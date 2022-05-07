package com.starnft.star.common.constant;

/**
 * @author WeiChunLAI
 */
public class StarConstants {

    public static final String USER_ID = "userId";

    public enum Ids {
        /**
         * 雪花算法
         */
        SnowFlake,
        /**
         * 随机算法
         */
        RandomNumeric;
    }

    /**
     * 服务名称
     */
    public static final String SERVICE_NAME = "star-service";


    /**
     * 密码错误重试机会
     */
    public static final Integer RETRY_COUNT = 5;

    /**
     * 账号冻结时长
     */
    public static final Long LOCK_DURATION = 600L;

    /**
     * 验证码重试次数
     */
    public static final Integer VERIFY_CODE_ERROR_TIMES = 10;
}
