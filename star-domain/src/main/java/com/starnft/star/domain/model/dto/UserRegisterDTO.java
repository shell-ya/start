package com.starnft.star.domain.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
public class UserRegisterDTO {

    @ApiModelProperty("手机号")
    @NotBlank(message = "phone 不能为空")
    private String phone;

    @ApiModelProperty("验证码")
    @NotBlank(message = "verificationCode 不能为空")
    private String verificationCode;

    @ApiModelProperty("密码")
    @NotBlank(message = "password 不能为空")
    private String password;

    @ApiModelProperty("昵称")
    @NotBlank(message = "nickName 不能为空")
    private String nickName;
}
