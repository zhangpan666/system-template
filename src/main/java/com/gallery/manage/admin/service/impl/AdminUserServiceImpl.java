package com.gallery.manage.admin.service.impl;

import com.gallery.manage.admin.service.AdminUserService;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.model.SysUserRole;
import com.gallery.manage.common.service.*;
import com.gallery.manage.common.util.OperationRecordUtil;
import com.gallery.manage.common.util.SystemUtil;
import com.github.pagehelper.PageInfo;
import com.light.core.authenticator.GoogleAuthenticator;
import com.light.core.authenticator.QRCodeUtil;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;
import com.light.core.utils.PageUtil;
import com.light.core.utils.RegularExpUtil;
import com.light.redis.model.RedisInfo;
import com.light.redis.util.RedisStringHashUtil;
import com.light.redis.util.RedisStringUtil;
import com.light.shiro.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    CacheService cacheService;

    @Autowired
    OperationRecordService operationRecordService;

    @Override
    public PageInfo<SysUser> listUser(Page page, SysUser sysUser) {
        PageInfo<SysUser> pageInfo = PageUtil.query(page, sysUser, sysUserService::query);
        List<SysUser> list = pageInfo.getList();
        list.forEach(user1 -> {
            Boolean hasKey = RedisStringHashUtil.hasKey(ProjectConstant.RedisKey.ONLINE_ADMIN.getKeyPrefix(), user1.getUsername());
            if (hasKey){
                user1.setOnline(ProjectConstant.COMMON_STATUS_AVAILABLE);
            } else {
                user1.setOnline(ProjectConstant.COMMON_STATUS_NOT_AVAILABLE);
            }
        });
        return pageInfo;
    }

    @Override
    public SysUser getUserDetail(Long id) {
        return sysUserService.getById(id);
    }

    @Override
    public CommonResult updateUser(SysUser sysUser) {
        SysUser userDetail = sysUserService.getById(sysUser.getId());
        String username = userDetail.getUsername();
        Subject subject = SecurityUtils.getSubject();
        String loginUserName = subject.getPrincipal().toString();
        if (SystemUtil.getAdminUsername().equals(username) && !SystemUtil.isAdmin(loginUserName)) {
            return CommonResult.businessWrong("您无权修改超级管理员的账号");
        }
        String password = sysUser.getPassword();
        if (!StringUtils.isEmpty(password)) {
            String encodePassword = ShiroUtil.getEncodePassword(username, password);
            sysUser.setPassword(encodePassword);
        } else {
            sysUser.setPassword(null);
        }
        boolean updateById = sysUserService.updateById(sysUser);
        if (updateById) {
            cacheService.clearWebUserLoginInfo(username);
            if (!StringUtils.isEmpty(password)) {
                cacheService.clearUserSessionCache(username);
            }
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @Override
    public CommonResult saveUser(SysUser sysUser, SysUserRole sysUserRole) {
        sysUser.setAccountType(ProjectConstant.UserType.ADMIN.getKey());
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();
        Long roleId = sysUserRole.getRoleId();
        if (StringUtils.isEmpty(username)) {
            return CommonResult.businessWrong("用户名不能为空");
        }
        if (!RegularExpUtil.isValidUsername(username)) {
            return CommonResult.businessWrong("用户名不合法，支持字母数字下划线中划线，3到16位");
        }
        SysUser adminUserByUsername = sysUserService.getAdminUserByUsername(username);
        if (adminUserByUsername != null) {
            return CommonResult.businessWrong("该用户名已被使用");
        }
        if (StringUtils.isEmpty(password)) {
            return CommonResult.businessWrong("密码不能为空");
        }
        if (!RegularExpUtil.isValidPassword(password)) {
            return CommonResult.businessWrong("密码不合法，支持字母数字下划线中划线，6到18位");
        }
        if (roleId==null){
            return CommonResult.businessWrong("角色不能为空");
        }
        String encodePassword = ShiroUtil.getEncodePassword(username, password);
        sysUser.setPassword(encodePassword);
        sysUserService.save(sysUser);
        sysUserRole.setId(sysUser.getId());
        boolean save = sysUserRoleService.save(sysUserRole);
        if (save) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @Override
    public CommonResult removeBgUser(Long id) {
        SysUser sysUser = sysUserService.getById(id);
        String username = sysUser.getUsername();
        Subject subject = SecurityUtils.getSubject();
        String loginUserName = subject.getPrincipal().toString();
        if (SystemUtil.getAdminUsername().equals(username) && !SystemUtil.isAdmin(loginUserName)) {
            return CommonResult.businessWrong("您无权修改超级管理员的账号");
        }
        boolean removeById = sysUserService.updateById(new SysUser().setId(id).setStatus(ProjectConstant.COMMON_STATUS_DELETE));
        if (removeById) {
            cacheService.clearUserSessionCache(username);
            cacheService.clearUserAuthorization(username);
            cacheService.clearWebUserLoginInfo(username);
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @Override
    public boolean authUser(SysUserRole sysUserRole) {
        boolean updateById = sysUserRoleService.updateById(sysUserRole);
        if (updateById) {
            SysUser sysUser = sysUserService.getById(sysUserRole.getId());
            if (sysUser != null) {
                String username = sysUser.getUsername();
                cacheService.clearUserAuthorization(username);
            }
        }
        return updateById;
    }

    @Override
    public CommonResult changePassword(String oldPassword, String newPassword) {
        if (StringUtils.isEmpty(oldPassword)) {
            return CommonResult.businessWrong("旧密码不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        SysUser adminUser = sysUserService.getAdminUserByUsername(username);
        String password = adminUser.getPassword();
        String encodePassword = ShiroUtil.getEncodePassword(username, oldPassword);
        if (!encodePassword.equals(password)) {
            return CommonResult.businessWrong("旧密码输入错误");
        }
        if (StringUtils.isEmpty(newPassword)) {
            return CommonResult.businessWrong("新密码不能为空");
        }
        if (!RegularExpUtil.isValidPassword(newPassword)) {
            return CommonResult.businessWrong("密码不合法，支持字母数字下划线中划线，6到18位");
        }
        boolean update = sysUserService.updateById(new SysUser().setId(adminUser.getId()).setPassword(ShiroUtil.getEncodePassword(username, newPassword)));
        if (update) {
            OperationRecordUtil.record(null, "后台用户", "修改密码");
            subject.logout();
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }


    @Override
    public void generateGoogleAuthQrCode(String username, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("请填写登录用户名!");
        }
        RedisInfo redisInfo = ProjectConstant.RedisKey.USER_LOGIN_VERIFY_CODE;
        String key = redisInfo.getKey(username);
        String issuer = "香港图库购彩:" + username + "的登录验证码";
        String account = "https://www.cpdlht.com";
        String secretKey = GoogleAuthenticator.createSecretKey();
        RedisStringUtil.set(key, secretKey, redisInfo.getDbIndex(), redisInfo.getExpire());
        //生成二维码信息
        String googleAuthQRCodeData = GoogleAuthenticator.createGoogleAuthQRCodeData(secretKey, account, issuer);
        //返回二维码图片流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            QRCodeUtil.writeToStream(googleAuthQRCodeData, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
