package com.gallery.manage.common.service.impl;

import com.gallery.manage.common.service.CacheService;
import com.light.redis.model.RedisInfo;
import com.light.redis.util.RedisUtil;
import com.light.shiro.constants.ShiroConstant;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {



    @Override
    public void clearSysUserAuthorization() {
        this.clearByPattern(ShiroConstant.RedisKey.USER_AUTHORIZATION_CACHE);
    }

    @Override
    public void clearUserAuthorization(String username) {
        this.clearByKey(ShiroConstant.RedisKey.USER_AUTHORIZATION_CACHE,username);
    }

    @Override
    public boolean clearByKey(RedisInfo redisInfo, Object... values) {
        String key = redisInfo.getKey(values);
        return RedisUtil.delete(key, redisInfo.getDbIndex());
    }

    @Override
    public boolean clearByPattern(RedisInfo redisInfo) {
        return RedisUtil.deleteByPattern(redisInfo, redisInfo.getDbIndex());
    }


    @Override
    public void clearWebUserLoginInfo(String username) {
        RedisInfo redisInfo = ShiroConstant.RedisKey.WEB_USER_LOGIN_INFO;
        this.clearByKey(redisInfo, username);
    }

    @Override
    public void clearUserSessionCache(String username) {
        RedisInfo redisInfo = ShiroConstant.RedisKey.USER_SESSION_CACHE;
        this.clearByKey(redisInfo, username);
    }

}
