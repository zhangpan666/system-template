package com.gallery.manage.common.util;

import com.gallery.manage.common.config.token.LoginInfo;
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

    private static final ThreadLocal<LoginInfo> LOGIN_INFO_THREAD_LOCAL = new ThreadLocal();


    public static boolean setAdminLoginInfo() {
        try {
            RedisInfo redisInfo = ShiroConstant.RedisKey.WEB_USER_LOGIN_INFO;
            Subject subject = SecurityUtils.getSubject();
            String username = subject.getPrincipal().toString();
            String key = redisInfo.getKey(username);
            LoginInfo loginInfo = RedisUtil.get(key, redisInfo.getDbIndex());
            if (loginInfo != null) {
                Serializable sessionId = loginInfo.getSessionId();
                RedisInfo userSessionCache = ShiroConstant.RedisKey.USER_SESSION_CACHE;
                RedisUtil.delete(userSessionCache.getKey(sessionId), userSessionCache.getDbIndex());
            }
            SysUserService userService = ApplicationContextUtil.getBean(SysUserService.class);
            SysUser sysUser = userService.getAdminUserByUsername(username);
            loginInfo = new LoginInfo();
            loginInfo.setUserId(sysUser.getId());
            loginInfo.setLoginTime(new Date());
            loginInfo.setNickname(sysUser.getNickname());
            loginInfo.setUsername(username);
            Serializable sessionId = subject.getSession().getId();
            loginInfo.setSessionId(sessionId);
            RedisUtil.set(key, loginInfo, redisInfo.getDbIndex(), redisInfo.getExpire());
            LOGIN_INFO_THREAD_LOCAL.set(loginInfo);
            return true;
        } catch (Exception e) {
            log.info("缓存后台用户登录信息异常");
            e.printStackTrace();
        }
        return false;
    }


    public static LoginInfo getAdminLoginInfo() {
        LoginInfo loginInfo = LOGIN_INFO_THREAD_LOCAL.get();
        if (loginInfo != null) {
            return loginInfo;
        }
        RedisInfo redisInfo = ShiroConstant.RedisKey.WEB_USER_LOGIN_INFO;
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        String redisInfoKey = redisInfo.getKey(username);
        loginInfo = RedisUtil.get(redisInfoKey, redisInfo.getDbIndex());
        if (loginInfo != null) {
            LOGIN_INFO_THREAD_LOCAL.set(loginInfo);
            return loginInfo;
        }
        SysUserService userService = ApplicationContextUtil.getBean(SysUserService.class);
        SysUser sysUser = userService.getAdminUserByUsername(username);
        loginInfo = new LoginInfo();
        loginInfo.setUserId(sysUser.getId());
        loginInfo.setNickname(sysUser.getNickname());
        loginInfo.setUsername(username);
        Serializable sessionId = subject.getSession().getId();
        loginInfo.setSessionId(sessionId);
        RedisUtil.set(redisInfoKey, loginInfo, redisInfo.getDbIndex(), redisInfo.getExpire());
        LOGIN_INFO_THREAD_LOCAL.set(loginInfo);
        return loginInfo;
    }

    public static Long getAdminLoginUserId() {
        LoginInfo loginInfo = getAdminLoginInfo();
        if (loginInfo == null) {
            return null;
        }
        return loginInfo.getUserId();
    }


    public static Long getLoginUserId() {
        LoginInfo loginInfo = getLoginInfo();
        if (loginInfo != null) {
            return loginInfo.getUserId();
        }
        return null;
    }



    public static LoginInfo getLoginInfo() {
        return LOGIN_INFO_THREAD_LOCAL.get();
    }

    public static void setLoginInfo(LoginInfo loginInfo) {
        LOGIN_INFO_THREAD_LOCAL.set(loginInfo);
    }


    public static void remove() {
        LOGIN_INFO_THREAD_LOCAL.remove();
    }
}
