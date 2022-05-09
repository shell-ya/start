package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author WeiChun
 */

@Getter
public enum RegisterTypeEnum {

    UNKNOWN(0, "UNKNOWN", "未知"),

    SMS_CODE_REGISTER(1, "registerByVerificationCodeStrategy", "手机验证码注册账号");

    private Integer code;
    private String strategy;
    private String desc;

    RegisterTypeEnum(Integer code, String strategy, String desc) {
        this.code = code;
        this.strategy = strategy;
        this.desc = desc;
    }

    public static RegisterTypeEnum getLoginTypeEnum(Integer code) {
        for (RegisterTypeEnum c : RegisterTypeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return RegisterTypeEnum.UNKNOWN;
    }
}
