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

    /**
     * token关键字
     */
    public static final String TOKEN = "token";

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

    /**
     * 对接支付厂商
     */
    public enum Pay_Vendor{
        SandPay
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
        Recharge(1, "Recharge"),
        /**
         * 提现
         */
        Withdraw(2, "Withdraw"),
        /**
         * 买入
         */
        Buy(3, "Buy"),
        /**
         * 卖出
         */
        Sell(4, "Sell"),
        /**
         * 退款
         */
        Refund(5, "Refund");

        private Integer code;

        private String font;

        Transaction_Type(Integer code, String font) {
            this.code = code;
            this.font = font;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }
    }

    public enum ProcessType {

        WEBSERVICE("webservice", "ws"),
        JSON("json", "json"),
        UNKNOWN("unknown", "unknown");

        private String name;
        private String code;

        ProcessType(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public enum MESSAGE_TERMINAL{
        Consumer,
        Producer
    }

    public enum NORMAL_STATUS{
        SUCCESS,
        FAILURE
    }

}
