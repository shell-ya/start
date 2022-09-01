package com.starnft.star.common.exception;


import lombok.extern.slf4j.Slf4j;

/**
 * @author WeiChunLAI
 */
@Slf4j
public enum StarError {

    SUCCESS_000000("000000", "SUCCESS"),
    ACCOUNT_TOKEN_DECRYPT_ERROR("100001", "decryptToken error"),
    USER_NOT_EXISTS("100002", "用户不存在"),
    USER_EXISTS("100003", "用户已存在"),

    USER_CREATION_ERROR("1000025", "创建用户失败"),

    VERIFICATION_CODE_ERROR("100004", "验证码错误"),
    SYSTEM_ERROR("100005", "服务器异常"),
    USER_HAS_BEEN_FROZEN_BY_VERIFICATION_CODE_ERROR("100006", "您输入的验证码已连续输入%s次，为您的账号安全，账号已冻结，请明日重试"),
    CODE_NOT_FUND("100007", "验证码错误或已过期"),
    USER_REPEATED("100008", "该手机号已经注册"),
    VERIFYCODE_FREQUENCY_IS_TOO_HIGH("100009", "您的操作过于频繁，请稍后再试"),
    PARAETER_UNSUPPORTED("100010", "参数不支持"),
    OLD_PWD_FAIL("100011", "原密码校验错误"),
    CHANGE_PWD_FREQUENCY_IS_TOO_HIGH("100012", "距离最新一次修改密码未超过24小时，请缓缓再试呢"),
    PWD_NOT_CHANGE("100013", "抱歉，您的密码设置与近期设置重复，请重新设置"),
    PWD_NOT_SETTING("100014", "您未设置密码，请使用短信验证码登录设置密码后重试"),
    IS_REAL_NAME_AUTHENTICATION("100015", "您已进行实名认证，请勿重复操作"),
    NOT_AUTHENTICATION("100016", "您还未进行实名认证操作"),
    ERROR_AUTHENTICATION("100016", "实名认证信息错误"),
    AGREEMENT_NOT_FUND("100017", "协议不存在"),
    AGREEMENT_TYPE_UNKNOWN("100018", "未知协议类型"),
    AGREEMENT_ID_NULL("100019", "协议id为空"),
    TOKEN_NOT_EXISTS_ERROR("100020", "token不存在"),
    TOKEN_EXPIRED_ERROR("100021", "token已失效，请重新登录"),
    USER_NOT_LOGIN("100015", "用户未登录"),

    PAYPWD_PRE_CHECK_ERROR("100022", "支付密码前置校验不通过"),
    PAYPWD_CHECK_ERROR("100023", "支付密码错误，连续错误%s次后将被冻结6小时，已连续错误%s次"),
    PAYPWD_CHECK_FREEZE("100024", "您输入的支付密码已连续错误%s次，为了您的账号安全，账号已冻结"),
    USER_SETUP_PASSWORD_ERROR("100025", "该用户已设置过初始登录密码，请勿重复设置"),

    IMAGE_CAPTCHA_CHECK_ERROR("100030", "图像验证码校验不通过"),

    DB_RECORD_UNEXPECTED_ERROR("200001", "数据库记录异常"),

    VALUE_COULD_NOT_BE_NULL("100022", "必须值不能为空"),

    BALANCE_NOT_ENOUGH("300001", "余额不足"),
    BALANCE_PAY_ERROR("300012", "余额支付失败"),

    THIRD_PLAT_SELL("400001","该藏品在第三方平台售出，请下架"),
    COS_ERROR("500001", "cos异常"),
    MESSAGE_TOPIC_NOT_FOUND("400001", "TopicConstants 未找到相应topic,请检查配置"),
    PERSISTENT_FAIL("200002", "数据保存失败"),

    IS_TRANSACTION("300002", "有其他交易正在进行，请先完成正在进行的交易!"),
    IS_WITHDRAWING("300010", "您已有提现订单正在处理,请耐心等待!"),

    WITHDRAW_ILLEGAL_MONEY("300011", "小于最低提现金额!"),

    OVER_WITHDRAW_TIMES("300003", "已达到当日提现最大次数"),
    OVER_WITHDRAW_MONEY("300004", "超过限制提现金额"),

    CARD_BINDING_FAILED("300005", "银行卡绑定失败"),
    CARD_NOT_BINDING_FAILED("300006", "你还没有绑定银行卡"),
    CARD_HAS_BIND("300008", "该银行卡已绑定"),

    CARD_LENGTH_ERROR("300009", "卡号长度错误"),
    CARD_BIND_NUMS_ERROR("300010", "银行卡超过绑定上限"),
    PAY_PROCESS_ERROR("300007", "支付异常"),
    REQUEST_TIMEOUT_ERROR("100040", "请求超时,请稍后再试！"),

    REQUEST_OVERFLOW_ERROR("100041", "人太多啦，请稍后再试吧！"),

    GOODS_NOT_FOUND("100042", "商品不存在"),

    ORDER_REPETITION("100043", "请勿重复下单"),

    STOCK_EMPTY_ERROR("100044", "手慢啦，全被抢光了!"),
    ORDER_DO_NOT_EXIST("100045", "未找到该订单"),
    ORDER_STATUS_ERROR("100046", "订单状态异常"),
    ORDER_CANCEL_ERROR("100047", "取消订单失败"),
    ORDER_DONT_SELL_ERROR("100056", "该藏品为赠品， 请参与活动获取！"),
    GOODS_SELF_ERROR("1000148", "禁止购买自己挂售商品"),

    GOODS_DO_NOT_START_ERROR("1000049", "未到商品出售时间！"),

    ORDER_CANCEL_TIMES_OVERFLOW("100050", "取消订单过于频繁,操作限制1小时,请稍后再试"),

    MARKET_DO_NOT_START_ERROR("1000051", "未到市场开放时间！"),
    GOOD_NOT_RESALE_ERROR("1000052", "该藏品不可转售"),
    NOT_ENABLED_ACTIVITY("1000053", "未到活动开启时间"),
    NOT_FOUND_ACTIVITY("1000054", "当前无开启活动"),
    GOODS_IS_TRANSACTION("1000055", "藏品正在交易中，请选择其他藏品"),

    ORDER_DONT_PAY_ERROR("100057", "当前已存在待支付订单，请先支付！"),
    //################# 道具  #################
    PROP_CAN_NOT_BUY("4000001", "道具不可购买"),
    PROP_CAN_BUY_OVERFLOW("4000002", "超出可购买数量"),
    //################# ===  #################
    COMPOSE_PRIZE_EXIST("10000068","超出合成次数或不具备合成名额"),
    BULLETIN_NOT_FOUND("50000001", "公告不存在"),

    BLIND_STOCK_IS_NULL("60000001", "盲盒奖品库存为空！"),

    BLIND_IS_ON_SELL("60000002", "请从市场或第三方平台先下架盲盒！"),

    INVALID_DRAW_REQUEST("60000003", "无效的抽奖请求！"),

    MARKET_NOT_OPEN("600000004","市场暂未开放，请等待公告通知"),
    CONSIGNMENT_NOT_OPEN("600000004","寄售暂未开放，请等待公告通知"),
    AIRDROP_NUMBER_ERROR("600000005","空投编号错误"),
    USER_NOT_HAS_DRAW("60000000006","该用户未中奖"),
    ;

    private String NS = "";

    private String code;

    private String desc;

    StarError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getNamespace() {
        return this.NS;
    }

    public String getErrorCode() {
        return this.code;
    }

    public String getErrorMessage() {
        return this.desc;
    }
}
