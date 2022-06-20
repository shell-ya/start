package com.starnft.star.domain.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("修改支付密码请求")
public class UserPlyPasswordVO {

    private Long userId;

    @ApiModelProperty("支付密码校验凭证")
    @NotBlank(message = "支付密码校验凭证不能为空")
    private String token;

    @ApiModelProperty(value = "支付密码")
    @Pattern(regexp = "[1-9]\\d*", message = "密码格式错误")
    private String payPassword;
}
