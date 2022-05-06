package com.starnft.star.common.constant;

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
    SMS_CODE(StarConstants.SERVICE_NAME.concat(".verification.code.%s"), 5L, TimeUnit.MINUTES),
    RETRY_PWD(StarConstants.SERVICE_NAME.concat(".retry.password.count.%s"), null, null);

    private String key;

    private Long time;

    private TimeUnit timeUnit;

    RedisKey(String key, Long time, TimeUnit timeUnit) {
        this.key = key;
        this.time = time;
        this.timeUnit = timeUnit;
    }

}
