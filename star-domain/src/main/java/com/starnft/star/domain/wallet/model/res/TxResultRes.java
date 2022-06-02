package com.starnft.star.domain.wallet.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("充值响应结果")
@AllArgsConstructor
public class TxResultRes implements Serializable {

    @ApiModelProperty(value = "充值单号")
    private String orderSn;

    @ApiModelProperty(value = "状态 WAIT_PAY待支付 PAY_ING 支付中 PAY_SUCCESS 支付成功  PAY_FAILED 支付失败 PAY_CLOSE 支付关闭 PAY_EXCEPTION 支付异常")
    private String status;

}
