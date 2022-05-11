package com.starnft.star.common.constant;

import com.starnft.star.common.utils.LocalDateUtil;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author WeiChunLai
 */
@Getter
public enum RedisKey {

    UNKNOWN(0, "UNKNOWN" , 0L ,TimeUnit.SECONDS),

    /**
     * 短信验证码校验 - 注册场景
     */
    SMS_CODE(100 ,StarConstants.SERVICE_NAME.concat(".verification.code.%s"), LocalDateUtil.betweenTomorrowMillis(), TimeUnit.MILLISECONDS),

    /**
     * 密码重试次数 - 账号密码登录
     */
    RETRY_PWD( 103 , StarConstants.SERVICE_NAME.concat(".retry.password.count.%s"), LocalDateUtil.betweenTomorrowMillis(), TimeUnit.MILLISECONDS),
    /**
     * 短信验证码 - 注册场景
     */
    REDIS_CODE_REGISIER(1 ,StarConstants.SERVICE_NAME.concat(".register.phone.code.%s"), 5L , TimeUnit.MINUTES),

    /**
     * 未登录修改密码验证码
     */
    REDIS_CODE_LOGIN_CHANGE_PWD(2 , StarConstants.SERVICE_NAME.concat(".login.changpwd.%s") ,5L , TimeUnit.MINUTES),

    /**
     * 登录后修改密码验证码
     */
    REDIS_CODE_NOT_LOGIN_CHANGE_PWD(3 , StarConstants.SERVICE_NAME.concat(".notlogin.changpwd.%s") , 5L , TimeUnit.MINUTES),

    /**
     * 修改支付密码
     */
    REDIS_CODE_LOGIN_CHANGE_PAYPWD(4, StarConstants.SERVICE_NAME.concat(".login.changpaypwd.%s") , 60L , TimeUnit.SECONDS),

    /**
     * 实名认证验证码
     */
    REDIS_REAL_NAME_AUTHENTICATION(5 , StarConstants.SERVICE_NAME.concat(".realnameauthentication.%s") , 5L ,TimeUnit.MINUTES),

    /**
     * 分布式锁 - 注册场景
     */
    REDIS_LOCK_USER_REGSIST(101 ,StarConstants.SERVICE_NAME.concat(".redislocak.register.%s"), 10L, TimeUnit.SECONDS),

    /**
     * 用户token
     */
    REDIS_USER_TOKEN(200 ,StarConstants.SERVICE_NAME.concat(".login.token.%s") , 12L , TimeUnit.HOURS),

    /**
     * 发送短信间隔限制时间
     */
    SMS_CODE_LIFE(300 ,StarConstants.SERVICE_NAME.concat(".stint.sendmessage.%s") , 30L ,TimeUnit.SECONDS),

    /**
     * 修改密码成功的限制时间
     */
    REDIS_CHANGE_PWD_SUCCESS_EXPIRED(102, StarConstants.SERVICE_NAME.concat(".stint.changgepwd.succcess.%s") ,12L , TimeUnit.HOURS),

    /**
     * 修改支付密码成功的限制时间
     */
    REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED(103, StarConstants.SERVICE_NAME.concat(".stint.changgepaypwd.succcess.%s") ,24L , TimeUnit.HOURS),
    ;

    private Integer code;

    private String key;

    private Long time;

    private TimeUnit timeUnit;

    RedisKey(Integer code , String key, Long time, TimeUnit timeUnit) {
        this.code = code;
        this.key = key;
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public static RedisKey getRedisKeyEnum(Integer code){
        for (RedisKey c : RedisKey.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return RedisKey.UNKNOWN;
    }
}
