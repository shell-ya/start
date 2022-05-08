package com.starnft.star.domain.user.service.impl;

import com.starnft.star.common.component.AuthTokenComponent;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.po.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author WeiChunLAI
 */
@Component
public class BaseUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthTokenComponent authTokenComponent;

    /**
     * 根据用户 id 创建 Token 并保存 redis
     * @param userId
     * @return
     */
    protected String createUserTokenAndSaveRedis(Long userId){
        //创建token
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);

        String userToken = authTokenComponent.createAccessToken(accessToken);

        //保存Redis
        redisTemplate.opsForValue().set(RedisKey.REDIS_USER_TOKEN.getKey(), userToken
                , RedisKey.REDIS_USER_TOKEN.getTime()
                , RedisKey.REDIS_USER_TOKEN.getTimeUnit());

        return userToken;
    }

    /**
     * 清除用户token
     * @param userId
     * @return
     */
    protected Boolean cleanUserToken(Long userId){
        return redisTemplate.delete(RedisKey.REDIS_USER_TOKEN.getKey());
    }
}
