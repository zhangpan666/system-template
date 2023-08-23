package com.gallery.manage.common.config.redis;

import com.light.redis.util.RedisUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class MybatisRedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id; // cache instance id
    private static final long EXPIRE_TIME_IN_MINUTES = 5; // redis过期时间

    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Put query result to redis
     *
     * @param key
     * @param value
     */
    @Override
    @SuppressWarnings("unchecked")
    public void putObject(Object key, Object value) {
        if (value == null) {
            return;
        }
        RedisTemplate redisTemplate = getRedisTemplate();
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(id, key.toString(), value);
        logger.debug("Put query result to redis");
    }

    /**
     * Get cached query result from redis
     *
     * @param key
     * @return
     */
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        HashOperations hashOperations = redisTemplate.opsForHash();
        logger.debug("Get cached query result from redis");
        return hashOperations.get(id, key.toString());
    }

    /**
     * Remove cached query result from redis
     *
     * @param key
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(id, key.toString());
        logger.debug("Remove cached query result from redis");
        return null;
    }

    /**
     * Clears this cache instance
     */
    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(id);
        logger.debug("Clear all the cached query result from redis");
    }

    @Override
    public int getSize() {
        RedisTemplate redisTemplate = getRedisTemplate();
        HashOperations hashOperations = redisTemplate.opsForHash();
        return Math.toIntExact(hashOperations.size(id));
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public RedisTemplate getRedisTemplate() {
        return RedisUtil.getRedisTemplate(12);
    }
}

