package com.starnft.star.domain.user.model.dto;

import lombok.Data;

@Data
public class AuthenticationNameDTO {

    /**
     * 真实名字
     */
    private String fullName;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 验证码
     */
    private String verificationCode;

    /**
     * 手机号
     */
    private String phone;
}
