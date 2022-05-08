package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
public class UserVerifyCodeReq {

    @ApiModelProperty("手机号")
    @NotBlank(message = "phone 不能为空")
    private String phone;

    @ApiModelProperty("验证码场景： 1=手机验证码登录 2=修改密码")
    @NotBlank(message = "verificationScenes 不能为空")
    private Integer verificationScenes;
}
