package com.starnft.star.common.constant;

/**
 * @author WeiChunLAI
 */
public class StarConstants {

    public static final String USER_ID = "userId";

    /**
     * 服务名称
     */
    public static final String SERVICE_NAME = "star-service";


    /**
     * 密码错误重试机会
     */
    public static final Integer RETRY_COUNT = 10;

    /**
     * 验证码重试次数
     */
    public static final Integer VERIFY_CODE_ERROR_TIMES = 10;

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

    public enum PayChannel {
        /**
         * 支付宝
         */
        AliPay,
        /**
         * 微信
         */
        WeChatPay,
        /**
         * 银行卡
         */
        BankCard,
        /**
         * 余额
         */
        Balance,
        /**
         * 其他
         */
        Other;
    }

    public enum Pay_Status {
        /**
         * 待支付
         */
        WAIT_PAY,
        /**
         * 支付中
         */
        PAY_ING,
        /**
         * 支付成功
         */
        PAY_SUCCESS,
        /**
         * 支付失败
         */
        PAY_FAILED,
        /**
         * 支付关闭
         */
        PAY_CLOSE;

    }

    public enum Fetch_Status {
        /**
         * 待收款
         */
        WaitFetch,
        /**
         * 收款成功
         */
        Fetch_Success,
        /**
         * 收款失败
         */
        Fetch_Failed;
    }

    public enum Transaction_Type {
        /**
         * 充值
         */
        Recharge,
        /**
         * 提现
         */
        Withdraw,
        /**
         * 买入
         */
        Buy,
        /**
         * 卖出
         */
        Sell,
        /**
         * 退款
         */
        Refund;

    }

}
