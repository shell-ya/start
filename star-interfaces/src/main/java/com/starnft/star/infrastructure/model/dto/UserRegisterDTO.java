package com.starnft.star.infrastructure.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author WeiChunLAI
 */
@Data
public class UserRegisterDTO {

    @ApiModelProperty("注册场景")
    @NotNull(message = "registerScenes 不能为空")
    private Integer registerScenes;

    @ApiModelProperty("验证码")
    @NotBlank(message = "verificationCode 不能为空")
    private String verificationCode;
}
