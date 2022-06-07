package com.starnft.star.application.process.wallet.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("充值响应")
public class RechargeReqResult implements Serializable {

   //拉起支付参数
    @ApiModelProperty(value = "充值单号")
    private String orderSn;

    @ApiModelProperty(value = "所需参数")
    private String neededParam;

    // 拉起支付跳转的url  后端做拼装处理 不同厂商拉起域名不同
    @ApiModelProperty(value = "拉起支付跳转url")
    private String forward;


}
