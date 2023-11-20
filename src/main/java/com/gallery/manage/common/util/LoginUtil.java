package com.gallery.manage.common.util;

import com.gallery.manage.common.config.token.AdminLoginInfo;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.service.SysUserService;
import com.light.config.util.ApplicationContextUtil;
import com.light.redis.model.RedisInfo;
import com.light.redis.util.RedisUtil;
import com.light.shiro.constants.ShiroConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.DependsOn;

import java.io.Serializable;
import java.util.Date;

@Slf4j
@DependsOn("applicationContextUtil")
public class LoginUtil {

    private static final ThreadLocal<AdminLoginInfo> LOGIN_INFO_THREAD_LOCAL = new ThreadLocal();


    public static boolean setAdminLoginInfo() {
        try {
            RedisInfo redisInfo = ShiroConstant.RedisKey.WEB_USER_LOGIN_INFO;
            Subject subject = SecurityUtils.getSubject();
            String username = subject.getPrincipal().toString();
            String key = redisInfo.getKey(username);
            AdminLoginInfo adminLoginInfo = RedisUtil.get(key, redisInfo.getDbIndex());
            if (adminLoginInfo != null) {
                Serializable sessionId = adminLoginInfo.getSessionId();
                RedisInfo userSessionCache = ShiroConstant.RedisKey.USER_SESSION_CACHE;
                RedisUtil.delete(userSessionCache.getKey(sessionId), userSessionCache.getDbIndex());
            }
            SysUserService userService = ApplicationContextUtil.getBean(SysUserService.class);
            SysUser sysUser = userService.getAdminUserByUsername(username);
            adminLoginInfo = new AdminLoginInfo();
            adminLoginInfo.setUserId(sysUser.getId());
            adminLoginInfo.setLoginTime(new Date());
            adminLoginInfo.setNickname(sysUser.getNickname());
            adminLoginInfo.setUsername(username);
            Serializable sessionId = subject.getSession().getId();
            adminLoginInfo.setSessionId(sessionId);
            RedisUtil.set(key, adminLoginInfo, redisInfo.getDbIndex(), redisInfo.getExpire());
            LOGIN_INFO_THREAD_LOCAL.set(adminLoginInfo);
            return true;
        } catch (Exception e) {
            log.info("缓存后台用户登录信息异常");
            e.printStackTrace();
        }
        return false;
    }


    public static AdminLoginInfo getAdminLoginInfo() {
        AdminLoginInfo adminLoginInfo = LOGIN_INFO_THREAD_LOCAL.get();
        if (adminLoginInfo != null) {
            return adminLoginInfo;
        }
        RedisInfo redisInfo = ShiroConstant.RedisKey.WEB_USER_LOGIN_INFO;
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        String redisInfoKey = redisInfo.getKey(username);
        adminLoginInfo = RedisUtil.get(redisInfoKey, redisInfo.getDbIndex());
        if (adminLoginInfo != null) {
            LOGIN_INFO_THREAD_LOCAL.set(adminLoginInfo);
            return adminLoginInfo;
        }
        SysUserService userService = ApplicationContextUtil.getBean(SysUserService.class);
        SysUser sysUser = userService.getAdminUserByUsername(username);
        adminLoginInfo = new AdminLoginInfo();
        adminLoginInfo.setUserId(sysUser.getId());
        adminLoginInfo.setNickname(sysUser.getNickname());
        adminLoginInfo.setUsername(username);
        Serializable sessionId = subject.getSession().getId();
        adminLoginInfo.setSessionId(sessionId);
        RedisUtil.set(redisInfoKey, adminLoginInfo, redisInfo.getDbIndex(), redisInfo.getExpire());
        LOGIN_INFO_THREAD_LOCAL.set(adminLoginInfo);
        return adminLoginInfo;
    }

    public static Long getAdminLoginUserId() {
        AdminLoginInfo adminLoginInfo = getAdminLoginInfo();
        if (adminLoginInfo == null) {
            return null;
        }
        return adminLoginInfo.getUserId();
    }


    public static Long getLoginUserId() {
        AdminLoginInfo adminLoginInfo = getLoginInfo();
        if (adminLoginInfo != null) {
            return adminLoginInfo.getUserId();
        }
        return null;
    }



    public static AdminLoginInfo getLoginInfo() {
        return LOGIN_INFO_THREAD_LOCAL.get();
    }

    public static void setLoginInfo(AdminLoginInfo adminLoginInfo) {
        LOGIN_INFO_THREAD_LOCAL.set(adminLoginInfo);
    }


    public static void remove() {
        LOGIN_INFO_THREAD_LOCAL.remove();
    }
}
