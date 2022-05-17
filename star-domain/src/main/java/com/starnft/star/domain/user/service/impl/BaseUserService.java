package com.starnft.star.domain.user.service.impl;

import com.starnft.star.common.component.AuthTokenComponent;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.po.AccessToken;
import com.starnft.star.common.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
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

        String key = String.format(RedisKey.REDIS_USER_TOKEN.getKey(), userId);

        //保存Redis
        redisTemplate.opsForValue().set(key , userToken
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


    /**
     * 校验token并返回用户id
     * @param token
     * @return
     */
    public Long checkTokenAndReturnUserId(String token){
        String accountId = JwtUtil.getAccountId(token);
        if (StringUtils.isNotBlank(accountId)){
            String key = String.format(RedisKey.REDIS_USER_TOKEN.getKey(), accountId);
            String tokenRes = String.valueOf(redisTemplate.opsForValue().get(key));
            if (StringUtils.isNotBlank(tokenRes)){
                return Long.valueOf(accountId);
            }
        }
        throw new  StarException(StarError.TOKEN_EXPIRED_ERROR);
    }


}
