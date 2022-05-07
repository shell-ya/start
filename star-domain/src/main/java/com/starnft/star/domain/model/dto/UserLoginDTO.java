package com.starnft.star.domain.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
@ApiModel
public class UserLoginDTO {

    @ApiModelProperty("手机号")
    @NotBlank(message = "phone 不能为空")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("登录类型(1-密码登录 2-短信验证码登录)")
    private Integer loginScenes;

    @ApiModelProperty("手机验证码")
    private String code;
}
