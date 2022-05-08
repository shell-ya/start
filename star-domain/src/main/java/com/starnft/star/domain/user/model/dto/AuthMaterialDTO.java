package com.starnft.star.domain.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
public class AuthMaterialDTO {

    @ApiModelProperty("验证码")
    private String verificationCode;

    @ApiModelProperty("新密码")
    private String password;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("手机号")
    private String phone;

}
