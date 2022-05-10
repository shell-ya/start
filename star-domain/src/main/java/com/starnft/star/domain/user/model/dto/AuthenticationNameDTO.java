package com.starnft.star.domain.user.model.dto;

import lombok.Data;

@Data
public class AuthenticationNameDTO {

    /**
     * 真实名字
     */
    private String name;

    /**
     * 身份证号
     */
    private String cardId;

    /**
     * 验证码
     */
    private String verificationCode;

    /**
     * 手机号
     */
    private String phone;
}
