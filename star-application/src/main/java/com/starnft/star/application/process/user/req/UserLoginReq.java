package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
public class UserLoginReq {

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("登录类型(1-密码登录 2-短信验证码登录)")
    @NotBlank(message = "登录类型不能为空")
    private Integer loginScenes;

    @ApiModelProperty("手机验证码")
    private String code;
}
