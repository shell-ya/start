package com.starnft.star.domain.user.model.dto;

import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class UserVerifyCodeDTO {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码场景： 1=手机验证码登录 2=修改密码 3=修改支付密码
     */
    private Integer verificationScenes;

    /**
     * 验证码
     */
    private String code;

    /**
     * 图形验证码id
     */
    private String imageCaptchaId;
}
