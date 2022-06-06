package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/06/05 20:44
 */
@Data
@ApiModel("支付密码前置校验响应结果")
@AllArgsConstructor
@NoArgsConstructor
public class PayPwdPreCheckRes implements Serializable {

    @ApiModelProperty("令牌")
    private String token;
}

