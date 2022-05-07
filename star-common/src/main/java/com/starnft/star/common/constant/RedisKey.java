package com.starnft.star.common.constant;

import com.starnft.star.common.utils.LocalDateUtil;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author WeiChunLai
 */
@Getter
public enum RedisKey {

    /**
     * 短信验证码
     */
    SMS_CODE(StarConstants.SERVICE_NAME.concat(".verification.code.%s"), LocalDateUtil.betweenTomorrowMillis(), TimeUnit.MILLISECONDS),

    /**
     * 密码重试次数
     */
    RETRY_PWD(StarConstants.SERVICE_NAME.concat(".retry.password.count.%s"), LocalDateUtil.betweenTomorrowMillis(), TimeUnit.MILLISECONDS),
    /**
     * 短信验证码 - 注册场景
     */
    REDIS_CODE_REGISIER(StarConstants.SERVICE_NAME.concat(".register.phone.code.%s"), 60L, TimeUnit.SECONDS),

    REDIS_LOCK_USER_REGSIST(StarConstants.SERVICE_NAME.concat(".redislocak.register.%s"), 10L, TimeUnit.SECONDS),
    ;

    private String key;

    private Long time;

    private TimeUnit timeUnit;

    RedisKey(String key, Long time, TimeUnit timeUnit) {
        this.key = key;
        this.time = time;
        this.timeUnit = timeUnit;
    }

}
