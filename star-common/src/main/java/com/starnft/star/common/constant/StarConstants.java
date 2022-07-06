package com.starnft.star.common.constant;

/**
 * @author WeiChunLAI
 */
public class StarConstants {

    public static final String USER_ID = "userId";
    public static final Integer INIT_VERSION = 0;

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
     * 支付密码错误重试机会
     */
    public static final Integer PAYPWD_RETRY_COUNT = 5;

    /**
     * token关键字
     */
    public static final String TOKEN = "token";

    /**
     * 市场品类系列缓存名字
     */
    public static final String SERIES_CACHE_NAME = ".series.cache";
    /**
     * 市场品类主题缓存名字
     */
    public static final String THEME_CACHE_NAME = ".theme.cache";
    /**
     * 主题详细信息缓存名字
     */
    public static final String THEME_DETAIL_CACHE_NAME = ".theme.detail.cache";

    public enum RankTypes {
        Consumption(2L, "积分"),
        Acquisition(1L, "拉新");
        private final  Long value;

        private final  String desc;

        RankTypes(Long value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public Long getValue() {
            return value;
        }
        public String  getDesc() {
            return desc;
        }
    }

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
         * 云闪付
         */
        UNION_PAY,
        /**
         * 微信
         */
        WeChatPay,
        /**
         * 银行卡
         */
        BankCard,
        /**
         * 快捷绑卡支付
         */
        QuickCard,
        /**
         * 市场
         */
        Market,
        /**
         * 余额
         */
        Balance,
        /**
         * 检测个人信息支付
         */
        CheckPay,
        CloudAccount,
        /**
         * 其他
         */
        Other;
    }

    /**
     * 对接支付厂商
     */
    public enum Pay_Vendor {
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
        PAY_CLOSE,
        /**
         * 支付异常
         */
        PAY_EXCEPTION;

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
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getFont() {
            return this.font;
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
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public enum MESSAGE_TERMINAL {
        Consumer,
        Producer
    }

    public enum NORMAL_STATUS {
        SUCCESS,
        FAILURE
    }

    public enum ORDER_STATE {
        WAIT_PAY(0, "待支付"),
        COMPLETED(1, "已完成"),
        PAY_CANCEL(2, "已取消");

        private Integer code;
        private String desc;

        ORDER_STATE(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }


    }


    public enum OrderType {

        RECHARGE("RECHARGE", "充值"),
        PUBLISH_GOODS("PUBLISH_GOODS", "发行"),
        MARKET_GOODS("MARKET_GOODS", "市场订单"),
        ;

        private String name;

        private String desc;

        OrderType(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum OrderPrefix {

        //充值订单前缀
        RechargeSn("RC"),
        //提现订单前缀
        WithdrawSn("WD"),
        //发行商品订单前缀
        PublishGoods("PG"),
        //市场交易订单前缀
        TransactionSn("TS");

        private final String prefix;

        OrderPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }
    public static class  EventStatus {

      public static   final Integer EVENT_STATUS_OPEN =1;
      public static  final  Integer EVENT_STATUS_CLOSE =0;
    }

    public  enum   EventSign {

        Register("register"),
        Buy("buy");
        private final String sign;

        EventSign(String sign) {
            this.sign=sign;
        }

        public String getSign() {
            return sign;
        }

        public static EventSign getEventSign(String sign) {
            for (EventSign value : values()) {
                if (value.getSign().equals(sign)){
                    return value;
                }
            }
            return  null;
        }
    }
    public enum   ActivityType {
        Scope(2, "积分活动"),
        Rank(1, "排行版活动");
        private final Integer  value;
        private final String   desc;

        ActivityType(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }
    public static class  ScopeMold {

        public static   final Integer UP =1;
        public static  final  Integer DOWN =0;
    }

    public enum userThemeStatus{

        PURCHASED(0,"已购买"),
        PENDING(1,"挂售中"),
        SOLD(2,"已售出")
        ;
        private Integer code;
        private String desc;

        userThemeStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
