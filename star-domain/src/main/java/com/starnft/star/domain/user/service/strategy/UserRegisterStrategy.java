package com.starnft.star.domain.user.service.strategy;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Component("userRegisterStrategy")
public abstract class UserRegisterStrategy {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLockUtils redisLockUtils;

    public abstract Long startSaveRegisterInfo(UserLoginDTO registerInfo);

    public Long register(UserLoginDTO registerInfo) {
        Long userId = null;

        String lockKey = String.format(RedisKey.REDIS_LOCK_USER_REGSIST.getKey(), registerInfo.getPhone());

        //lock
        if (redisLockUtils.lock(lockKey, RedisKey.REDIS_LOCK_USER_REGSIST.getTime())) {
            try {
                //注册
                userId = registerInnit(registerInfo);
            } finally {
                redisLockUtils.unlock(lockKey);
            }
        } else {
            throw new StarException(StarError.SYSTEM_ERROR, "当前账号操作频繁 ，请稍后重试");
        }

        return userId;
    }

    private Long registerInnit(UserLoginDTO registerInfo) {

        Long userId = startSaveRegisterInfo(registerInfo);

        this.redisTemplate.opsForValue().setBit(RedisKey.REDIS_USER_IS_REGISTERED.getKey(), Long.parseLong(registerInfo.getPhone()), Boolean.TRUE);
        //注册加入拉新锁
        redisTemplate.opsForValue().setIfAbsent(String.format(RedisKey.REDIS_USER_REG_NEW.getKey(), userId), null, RedisKey.REDIS_USER_REG_NEW.getTime(), RedisKey.REDIS_USER_REG_NEW.getTimeUnit());
        //todo 触发注册逻辑
        return userId;
    }

}
