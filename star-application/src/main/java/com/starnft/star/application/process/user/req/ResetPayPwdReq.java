package com.starnft.star.application.process.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Harlan
 * @date 2022/06/20 17:06
 */
@Data
@Builder
@ApiModel
public class ResetPayPwdReq {
    private Long userId;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

    @ApiModelProperty("支付密码")
    @NotBlank(message = "支付密码不能为空")
    @Pattern(regexp = "[1-9]\\d*", message = "密码格式错误")
    private String payPassword;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
