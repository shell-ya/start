package com.starnft.star.domain.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author WeiChunLAI
 */
@Data
public class UserLoginDTO {

    @ApiModelProperty("手机号")
    @NotBlank(message = "phone 不能为空")
    private String phone;

    @ApiModelProperty("密码")
    @NotBlank(message = "password 不能为空")
    private String password;
}
