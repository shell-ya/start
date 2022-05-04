package com.starnft.star.interfaces.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public void multiSet(Map map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    public void expire(String key, Long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    public void delByKey(String key) {
        redisTemplate.delete(key);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
}