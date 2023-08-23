package com.gallery.manage.common.service;

import com.light.redis.model.RedisInfo;

import java.util.List;

public interface CacheService {

    void clearSysUserAuthorization();

    void clearUserAuthorization(String username);

    boolean clearByKey(RedisInfo redisInfo, Object... values);

    boolean clearByPattern(RedisInfo redisInfo);

    void clearWebUserLoginInfo(String username);

    void clearUserSessionCache(String username);
}
