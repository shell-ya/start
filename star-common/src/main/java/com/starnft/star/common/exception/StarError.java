package com.starnft.star.common.exception;


import lombok.extern.slf4j.Slf4j;

/**
 * @author WeiChunLAI
 */
@Slf4j
public enum StarError {

    SUCCESS_000000("000000", "SUCCESS"),
    ACCOUNT_TOKEN_DECRYPT_ERROR("100001", "decryptToken error"),
    USER_NOT_EXISTS("100002","用户不存在"),
    USER_EXISTS("100003","用户已存在"),
    VERIFICATION_CODE_ERROR("100004","验证码错误"),
    SYSTEM_ERROR("100005","服务器异常"),
    USER_HAS_BEEN_FROZEN_BY_VERIFICATION_CODE_ERROR("100006","您输入的验证码已连续输入%s次，为您的账号安全，账号已冻结，请明日重试"),
    CODE_NOT_FUND("100007","验证码错误或已过期"),
    USER_REPEATED("100008","该手机号已经注册"),
    VERIFYCODE_FREQUENCY_IS_TOO_HIGH("100009","您的操作过于频繁，请稍后再试"),
    PARAETER_UNSUPPORTED("100010","参数不支持"),
    OLD_PWD_FAIL("100011","原密码校验错误"),
    CHANGE_PWD_FREQUENCY_IS_TOO_HIGH("100012","距离最新一次修改密码未超过24小时，请缓缓再试呢"),
    ;

    private String NS = "";

    private String code;

    private String desc;

    StarError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getNamespace() {
        return NS;
    }

    public String getErrorCode() {
        return code;
    }

    public String getErrorMessage() {
        return desc;
    }
}