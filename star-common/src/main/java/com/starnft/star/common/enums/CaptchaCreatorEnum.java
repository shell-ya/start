package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author Harlan
 * @date 2022/05/30 23:09
 */
@Getter
@SuppressWarnings("all")
public enum CaptchaCreatorEnum {

    TIAN_AI(1, "tianaiCaptchaCreator", "天爱开源验证码");

    private Integer code;
    private String creator;
    private String desc;

    CaptchaCreatorEnum(Integer code, String creator, String desc) {
        this.code = code;
        this.creator = creator;
        this.desc = desc;
    }

    public static CaptchaCreatorEnum getCaptchaCreatorEnum(Integer code) {
        for (CaptchaCreatorEnum c : CaptchaCreatorEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return CaptchaCreatorEnum.TIAN_AI;
    }
}
