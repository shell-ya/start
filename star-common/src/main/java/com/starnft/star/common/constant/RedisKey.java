package com.starnft.star.common.constant;

import com.starnft.star.common.utils.LocalDateUtil;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author WeiChunLai
 */
@Getter
public enum RedisKey {

    UNKNOWN(0, "UNKNOWN", 0L, TimeUnit.SECONDS),

    /**
     * 短信验证码校验 - 注册场景
     */
    SMS_CODE(100, StarConstants.SERVICE_NAME.concat(".verification.code.%s"), LocalDateUtil.betweenTomorrowMillis(), TimeUnit.MILLISECONDS),

    /**
     * 密码重试次数 - 账号密码登录
     */
    RETRY_PWD(103, StarConstants.SERVICE_NAME.concat(".retry.password.count.%s"), LocalDateUtil.betweenTomorrowMillis(), TimeUnit.MILLISECONDS),
    /**
     * 短信验证码 - 注册场景
     */
    REDIS_CODE_REGISIER(1, StarConstants.SERVICE_NAME.concat(".register.phone.code.%s"), 5L, TimeUnit.MINUTES),

    /**
     * 未登录修改密码验证码
     */
    REDIS_CODE_LOGIN_CHANGE_PWD(2, StarConstants.SERVICE_NAME.concat(".login.changpwd.%s"), 5L, TimeUnit.MINUTES),

    /**
     * 登录后修改密码验证码
     */
    REDIS_CODE_NOT_LOGIN_CHANGE_PWD(3, StarConstants.SERVICE_NAME.concat(".notlogin.changpwd.%s"), 5L, TimeUnit.MINUTES),

    /**
     * 修改支付密码
     */
    REDIS_CODE_LOGIN_CHANGE_PAYPWD(4, StarConstants.SERVICE_NAME.concat(".login.changpaypwd.%s"), 5L, TimeUnit.MINUTES),

    /**
     * 实名认证验证码
     */
    REDIS_REAL_NAME_AUTHENTICATION(5, StarConstants.SERVICE_NAME.concat(".realnameauthentication.%s"), 5L, TimeUnit.MINUTES),

    /**
     * 分布式锁 - 注册场景
     */
    REDIS_LOCK_USER_REGSIST(101, StarConstants.SERVICE_NAME.concat(".redislocak.register.%s"), 10L, TimeUnit.SECONDS),

    /**
     * 用户token
     */
    REDIS_USER_TOKEN(200, StarConstants.SERVICE_NAME.concat(".login.token.%s"), 12L, TimeUnit.HOURS),
    /**
     * 用户是否设置登录密码
     */
    REDIS_USER_IS_SETTING_PWD(201, StarConstants.SERVICE_NAME.concat(".user:info:isPwd"), -1L, TimeUnit.HOURS),
    REDIS_USER_IS_CERTIFICATION(202, StarConstants.SERVICE_NAME.concat(".user:info:certification"), -1L, TimeUnit.HOURS),

    /**
     * 发送短信间隔限制时间
     */
    SMS_CODE_LIFE(300, StarConstants.SERVICE_NAME.concat(".stint.sendmessage.%s"), 30L, TimeUnit.SECONDS),

    /**
     * 修改密码成功的限制时间
     */
    REDIS_CHANGE_PWD_SUCCESS_EXPIRED(102, StarConstants.SERVICE_NAME.concat(".stint.changgepwd.succcess.%s"), 12L, TimeUnit.HOURS),

    /**
     * 修改支付密码成功的限制时间
     */
    REDIS_CHANGE_PAY_PWD_SUCCESS_EXPIRED(103, StarConstants.SERVICE_NAME.concat(".stint.changgepaypwd.succcess.%s"), 24L, TimeUnit.HOURS),

    /**
     * 前置支付密码校验令牌
     */
    REDIS_PRE_PAY_PWD_CHECK_TOKEN(104, StarConstants.SERVICE_NAME.concat(".paypwd.check.pre.%s"), 30L, TimeUnit.SECONDS),

    /**
     * 图像验证码校验成功凭证
     */
    REDIS_IMAGE_CAPTCHA_CHECK_SUCCESS_TOKEN(105, StarConstants.SERVICE_NAME.concat(".image.captcha.check.%s"), 30L, TimeUnit.SECONDS),

    /**
     * 支付密码错误次数
     */
    REDIS_PAYPWD_CHECK_ERROR_TIMES(106, StarConstants.SERVICE_NAME.concat(".paypwd.check.error.times.%s"), 6L, TimeUnit.HOURS),


    /**
     * 钱包交易状态锁
     */
    REDIS_TRANSACTION_ING(401, StarConstants.SERVICE_NAME.concat(".wallet.transaction:%s"), 3L, TimeUnit.MINUTES),

    /**
     * 提现次数
     */
    REDIS_WITHDRAW_TIMES(402, StarConstants.SERVICE_NAME.concat(".wallet.withdraw.times:%s"), 1L, TimeUnit.HOURS),

    /**
     * 交易成功标识
     */
    REDIS_TRANSACTION_SUCCESS(403, StarConstants.SERVICE_NAME.concat(".transaction.status.success:%s"), 1L, TimeUnit.MINUTES),

    /**
     * 幂等判断
     */
    REDIS_IDEMPOTENT_MARK(201, StarConstants.SERVICE_NAME.concat(".MQ.idempotent.mark:%s"), 30L, TimeUnit.MINUTES),

    //======================================= sec kill goods cache =======================================

    /**
     * 秒杀商品详情
     */
    SECKILL_GOODS_INFO(501, StarConstants.SERVICE_NAME.concat(".seckill:goods:%s"), -1L, TimeUnit.MINUTES),
    /**
     * 秒杀商品库存队列
     */
    SECKILL_GOODS_STOCK_QUEUE(502, StarConstants.SERVICE_NAME.concat(".seckill:stock.queue:%s"), -1L, TimeUnit.HOURS),
    /**
     * 秒杀商品库存数量
     */
    SECKILL_GOODS_STOCK_NUMBER(503, StarConstants.SERVICE_NAME.concat(".seckill:stock.num"), -1L, TimeUnit.HOURS),

    /**
     * 记录用户下单次数
     */
    SECKILL_ORDER_REPETITION_TIMES(504, StarConstants.SERVICE_NAME.concat(".seckill:ordered.times"), -1L, TimeUnit.HOURS),

    /**
     * 用户订单 hash
     */
    SECKILL_ORDER_USER_MAPPING(505, StarConstants.SERVICE_NAME.concat(".seckill:ordered.mapping:%s"), -1L, TimeUnit.HOURS),

    /**
     * 用户订单状态 hash
     */
    SECKILL_ORDER_USER_STATUS_MAPPING(506, StarConstants.SERVICE_NAME.concat(".seckill:ordered.status:%s"), -1L, TimeUnit.HOURS),


    SECKILL_ORDER_TRANSACTION(507, StarConstants.SERVICE_NAME.concat(".seckill:ordered:transaction:%s"), 3L, TimeUnit.MINUTES),

    /**
     * 自动取消订单次数
     */
    ORDER_BREAK_COUNT(508, StarConstants.SERVICE_NAME.concat(".seckill:ordered:break:%s"), 1800L, TimeUnit.SECONDS),
    /**
     * 自动取消订单记录
     */
    ORDER_BREAK_RECORD(509, StarConstants.SERVICE_NAME.concat(".seckill:ordered:record:%s"), 3600L, TimeUnit.SECONDS),

    //======================================= sec kill goods end   =======================================

    /**
     * 市场交易锁
     */
    MARKET_ORDER_TRANSACTION(508, StarConstants.SERVICE_NAME.concat(".market:transaction:%s"), 3L, TimeUnit.MINUTES),
    /**
     * 排行榜列表
     */
    RANK_LIST(601, StarConstants.SERVICE_NAME.concat(".rank:list"), 3L, TimeUnit.HOURS),
    /**
     * 排行榜
     */
    RANK_STORE(602, StarConstants.SERVICE_NAME.concat(".rank:store:%s"), 3L, TimeUnit.HOURS),
    RANK_EXTEND(603, StarConstants.SERVICE_NAME.concat(".rank:extend:%s"), 3L, TimeUnit.HOURS);
    private Integer code;

    private String key;

    private Long time;

    private TimeUnit timeUnit;

    RedisKey(Integer code, String key, Long time, TimeUnit timeUnit) {
        this.code = code;
        this.key = key;
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public static RedisKey getRedisKeyEnum(Integer code) {
        for (RedisKey c : RedisKey.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return RedisKey.UNKNOWN;
    }
}
