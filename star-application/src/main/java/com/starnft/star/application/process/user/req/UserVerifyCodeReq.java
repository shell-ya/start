package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
@ApiModel
public class UserVerifyCodeReq {

    @ApiModelProperty("手机号")
    @NotBlank(message = "phone 不能为空")
    private String phone;

    @ApiModelProperty("验证码场景： 1=手机验证码登录 2=不登录修改密码 3=登陆后修改密码 4=修改支付密码 5=发起实名认证")
    private Integer verificationScenes;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("图形验证码id")
    private String imageCaptchaId;
}
