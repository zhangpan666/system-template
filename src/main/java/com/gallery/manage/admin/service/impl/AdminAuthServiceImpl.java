package com.gallery.manage.admin.service.impl;

import com.gallery.manage.admin.service.AdminAuthService;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.service.ConfigurationService;
import com.gallery.manage.common.service.SysUserService;
import com.gallery.manage.common.util.LoginUtil;
import com.gallery.manage.common.util.OperationRecordUtil;
import com.gallery.manage.common.util.SystemUtil;
import com.light.core.authenticator.GoogleAuthenticator;
import com.light.core.model.CommonResult;
import com.light.core.utils.HttpUtil;
import com.light.redis.model.RedisInfo;
import com.light.redis.util.RedisStringUtil;
import com.light.shiro.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    SysUserService sysUserService;


    @Override
    public CommonResult<Map<String, Object>> login(SysUser sysUser, HttpServletRequest request) {
        try {
            String ipAddr = HttpUtil.getIpAddr(request);
            String adminAllowedIp = configurationService.getAdminAllowedIp();
            if (!SystemUtil.isTest() && !StringUtils.isEmpty(adminAllowedIp) && !adminAllowedIp.contains(ipAddr)) {
                return CommonResult.businessWrong("非法IP访问");
            }
            String username = sysUser.getUsername();
            String password = sysUser.getPassword();
            String verifyCode = sysUser.getVerifyCode();
            if (StringUtils.isEmpty(username)) {
                return CommonResult.businessWrong("用户名不能为空");
            }
            if (StringUtils.isEmpty(password)) {
                return CommonResult.businessWrong("密码不能为空");
            }
            if (StringUtils.isEmpty(verifyCode)) {
                return CommonResult.businessWrong("验证码不能为空");
            }
            SysUser adminUserByUsername = sysUserService.getAdminUserByUsername(username);
            if (adminUserByUsername == null || adminUserByUsername.getStatus() != ProjectConstant.COMMON_STATUS_AVAILABLE) {
                return CommonResult.businessWrong("用户状态异常");
            }
            String encodePassword = ShiroUtil.getEncodePassword(username, password);
            String requiredPassword = adminUserByUsername.getPassword();
            if (!requiredPassword.equals(encodePassword)) {
                return CommonResult.businessWrong("用户名或密码错误");
            }
            if (!verifyCode.equals(SystemUtil.getVerifyCode())) {
                RedisInfo redisInfo = ProjectConstant.RedisKey.USER_LOGIN_VERIFY_CODE;
                String secretKey = RedisStringUtil.get(redisInfo.getKey(username), redisInfo.getDbIndex());
                if (StringUtils.isEmpty(secretKey)) {
                    return CommonResult.businessWrong("验证码已过期，请重新扫码！");
                }
                if (!GoogleAuthenticator.verify(secretKey, verifyCode)) {
                    return CommonResult.businessWrong("验证码错误");
                }
            }
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            usernamePasswordToken.setRememberMe(true);
            subject.login(usernamePasswordToken);
            if (subject.isAuthenticated()) {
                log.info("登录成功");
                LoginUtil.setAdminLoginInfo();
                sysUserService.updateById(new SysUser().setId(adminUserByUsername.getId()).setLastLoginTime(adminUserByUsername.getRecentLoginTime()).setRecentLoginTime(new Date()));
                OperationRecordUtil.record(null, "后台用户", "登录");
                Map<String, Object> map = new HashMap<>();
                map.put("url", "/gallery/manage/admin/index");
                return CommonResult.success(map);
            } else {
                log.info("登录失败");
                return CommonResult.businessWrong("用户名或密码错误");
            }
        } catch (Exception e) {
            log.info("======认证失败=====");
            return CommonResult.businessWrong("请求发生错误，请联系管理员");
        }
    }

}
