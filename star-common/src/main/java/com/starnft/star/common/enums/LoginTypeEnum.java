package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author WeiChun
 */

@Getter
public enum LoginTypeEnum {

    UNKNOWN(0, "UNKNOWN", "未知"),

    PASSWORD_LOGIN(1, "loginByPasswordStrategy", "密码登录"),

    PHONE_CODE_LOGIN(2, "loginByVerificationCodeStrategy", "短信验证码登录"),
    ;

    private Integer code;
    private String strategy;
    private String desc;

    LoginTypeEnum(Integer code, String strategy, String desc) {
        this.code = code;
        this.strategy = strategy;
        this.desc = desc;
    }

    public static LoginTypeEnum getLoginTypeEnum(Integer code) {
        for (LoginTypeEnum c : LoginTypeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return LoginTypeEnum.UNKNOWN;
    }
}
