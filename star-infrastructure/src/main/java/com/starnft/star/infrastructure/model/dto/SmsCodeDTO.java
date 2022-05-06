package com.starnft.star.infrastructure.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author WeiChunLAI
 */
@Data
public class SmsCodeDTO {

    @ApiModelProperty("验证码类型：0->注册 1->修改手机号")
    @NotNull(message = "codeType 不能为空")
    private Integer codeType;

    @ApiModelProperty("手机号")
    @NotBlank(message = "phone 不能为空")
    private String phone;
}
