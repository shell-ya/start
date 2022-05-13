package com.starnft.star.domain.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "验证码")
public class SmsVerifyCodePO {
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "短信类型")
    private SmsType smsType;
}
