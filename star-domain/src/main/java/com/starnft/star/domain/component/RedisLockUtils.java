package com.starnft.star.domain.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component
public class RedisLockUtils {

    public static final String REDIS_LOCK_PREFIX = "STAR_LOCK";

    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean lock(String key , long expire){
        Boolean val = true;
        try {
            List<String> keys = Arrays.asList(REDIS_LOCK_PREFIX + key);
            val = (Boolean) redisTemplate.execute(RedisLuaUtils.lock(), keys, expire);
            if (val) {
                log.info("redis lock success:{}",key);
            }
        }catch (Exception e){
            log.info("redis lock fail:{}", key , e);
        }
        return val;
    }

    public void unlock(String key){
        String redisKey = REDIS_LOCK_PREFIX + key;
        try {
            if (redisTemplate.hasKey(redisKey)){
                redisTemplate.delete(redisKey);
                log.info("redis unlock success:{}" , key);
            }
        }catch (Exception e){
            log.info("redis unlock fail:{}" , key, e);
        }
    }
}
