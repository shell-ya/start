package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
public class AuthMaterialReq {

    @ApiModelProperty("验证码")
    @NotBlank(message = "verificationCode 不能为空")
    private String verificationCode;

    @ApiModelProperty("发送验证码场景：1=手机号注册 2=不登录修改密码 3=登录下修改密码")
    private Integer verificationScenes;

    @ApiModelProperty("新密码")
    @NotBlank(message = "password 不能为空")
    private String password;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("手机号")
    @NotBlank(message = "phone 不能为空")
    private String phone;
}
