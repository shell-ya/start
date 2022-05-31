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
    AGREEMENT_NOT_FUND("100017", "协议不存在"),
    AGREEMENT_TYPE_UNKNOWN("100018", "未知协议类型"),
    AGREEMENT_ID_NULL("100019", "协议id为空"),
    TOKEN_NOT_EXISTS_ERROR("100020", "token不存在"),
    TOKEN_EXPIRED_ERROR("100020", "token已失效，请重新登录"),
    USER_NOT_LOGIN("100015", "用户未登录"),

    IMAGE_CAPTCHA_CHECK_ERROR("100030", "图像验证码校验不通过"),

    DB_RECORD_UNEXPECTED_ERROR("200001", "数据库记录异常"),

    VALUE_COULD_NOT_BE_NULL("100010", "必须值不能为空"),

    BALANCE_NOT_ENOUGH("300001", "余额不足"),


    COS_ERROR("500001", "cos异常"),
    MESSAGE_TOPIC_NOT_FOUND("400001", "TopicConstants 未找到相应topic,请检查配置"),
    PERSISTENT_FAIL("200002", "数据保存失败"),

    IS_TRANSACTION("300002", "有其他交易正在进行，请先完成正在进行的交易"),

    OVER_WITHDRAW_TIMES("300003", "已达到当日提现最大次数"),
    OVER_WITHDRAW_MONEY("300004", "超过限制提现金额"),

    CARD_BINDING_FAILED("300005", "银行卡绑定失败"),
    CARD_NOT_BINDING_FAILED("300006", "你还没有绑定银行卡"),
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