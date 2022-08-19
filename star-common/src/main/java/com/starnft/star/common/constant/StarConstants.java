package com.starnft.star.common.constant;

/**
 * @author WeiChunLAI
 */
public class StarConstants {

    public static final String USER_ID = "userId";
    public static final String PHONE = "phone";

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
    /**
     * 轮播图缓存名字
     */
    public static final String BANNER_CACHE_NAME = ".banner.cache";
    public static final String DING_CACHE_NAME = ".ding.cache";
    /**
     * 排行榜缓存名字
     */
    public static final String RANK_CACHE_NAME = ".rank.cache";
    /**
     * 公告缓存名字
     */
    public static final String BULLETIN_LIST_CACHE_NAME = ".bulletin.list.cache";
    /**
     * 公告详情缓存名字
     */
    public static final String BULLETIN_CACHE_NAME = ".bulletin.cache";

    public enum RankTypes {
        Consumption(2L, "积分"),
        Acquisition(1L, "拉新");
        private final Long value;

        private final String desc;

        RankTypes(Long value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public Long getValue() {
            return value;
        }

        public String getDesc() {
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
        //道具订单
        PROPSHOP("PS"),
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

    public static class EventStatus {

        public static final Integer EVENT_STATUS_OPEN = 1;
        public static final Integer EVENT_STATUS_CLOSE = 0;
    }

    public enum EventSign {

        Register("register"),
        Buy("buy");
        private final String sign;

        EventSign(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }

        public static EventSign getEventSign(String sign) {
            for (EventSign value : values()) {
                if (value.getSign().equals(sign)) {
                    return value;
                }
            }
            return null;
        }
    }

    public enum ActivityType {
        Scope(2, "积分活动"),
        Rank(1, "排行版活动");
        private final Integer value;
        private final String desc;

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

    public static class ScopeMold {

        public static final Integer UP = 1;
        public static final Integer DOWN = 0;
    }

    public enum userThemeStatus {

        PURCHASED(0, "已购买"),
        PENDING(1, "挂售中"),
        SOLD(2, "已售出");
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

    public enum themeResaleEnum {
        NOT_RESALE(0, "不可转售"),
        RESALE(1, "可转售"),
        ;
        private Integer code;
        private String desc;

        themeResaleEnum(int code, String desc) {
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


    public enum PropsType {

        CONSUMPTION(1, "消耗品"),
        PERMANENT(2, "长期存在的"),
        ;
        private Integer code;
        private String desc;

        PropsType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public enum PropsLevel {

        NORMAL(1, "N"),
        BOUTIQUE(2, "R"),
        BEST(3, "SR"),
        LEGEND(4, "SSR"),
        ;
        private Integer code;
        private String desc;

        PropsLevel(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public enum PlatformEnums {
        Android,
        Ios,
        Web;

    }


    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖
     */
    public enum DrawState {
        /**
         * 未中奖
         */
        FAIL(0, "未中奖"),

        /**
         * 已中奖
         */
        SUCCESS(1, "已中奖"),

        /**
         * 兜底奖
         */
        Cover(2, "兜底奖");

        private Integer code;
        private String info;

        DrawState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 发奖状态：0等待发奖、1发奖成功、2发奖失败
     */
    public enum AwardState {

        /**
         * 等待发奖
         */
        WAIT(0, "等待发奖"),

        /**
         * 发奖成功
         */
        SUCCESS(1, "发奖成功"),

        /**
         * 发奖失败
         */
        FAILURE(2, "发奖失败");

        private Integer code;
        private String info;

        AwardState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 奖品类型（1:藏品、2:兑换码、3:优惠券、4:实物奖品 5:道具 6:原石）
     */
    public enum AwardType {
        /**
         * 文字描述
         */
        NFT(1, "藏品"),
        /**
         * 兑换码
         */
        RedeemCodeGoods(2, "兑换码"),
        /**
         * 优惠券
         */
        CouponGoods(3, "优惠券"),
        /**
         * 实物奖品
         */
        PhysicalGoods(4, "实物奖品"),
        /**
         * 道具
         */
        PropsAward(5, "道具"),
        /**
         * 元石
         */
        Yuan(6,"元石")

        ;

        private Integer code;
        private String info;

        AwardType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }


    /**
     * 抽奖策略模式：总体概率、单项概率
     * 场景：两种抽奖算法描述，场景A20%、B30%、C50%
     * 单项概率：如果A奖品抽空后，B和C保持目前中奖概率，用户抽奖扔有20%中为A，因A库存抽空则结果展示为未中奖。为了运营成本，通常这种情况的使用的比较多
     * 总体概率：如果A奖品抽空后，B和C奖品的概率按照 3:5 均分，相当于B奖品中奖概率由 0.3 升为 0.375
     */
    public enum StrategyMode {

        /**
         * 单项概率：如果A奖品抽空后，B和C保持目前中奖概率，用户抽奖扔有20%中为A，因A库存抽空则结果展示为未中奖。为了运营成本，通常这种情况的使用的比较多
         */
        SINGLE(1, "单项概率"),

        /**
         * 总体概率：如果A奖品抽空后，B和C奖品的概率按照 3:5 均分，相当于B奖品中奖概率由 0.3 升为 0.375
         */
        ENTIRETY(2, "总体概率"),

        BLANKOFFSET(3, "奖品为空偏移概率");

        private Integer code;
        private String info;

        StrategyMode(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }



    /**
     * 消息发送状态（0未发送、1发送成功、2发送失败）
     */
    public enum MQState {
        INIT(0, "初始"),
        COMPLETE(1, "完成"),
        FAIL(2, "失败");

        private Integer code;
        private String info;

        MQState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }


    /**
     * 发奖状态 0初始、1完成、2失败
     */
    public enum GrantState {

        INIT(0, "初始"),
        COMPLETE(1, "完成"),
        FAIL(2, "失败");

        private Integer code;
        private String info;

        GrantState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }



}
