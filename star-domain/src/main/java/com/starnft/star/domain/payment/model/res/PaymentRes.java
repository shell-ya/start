package com.starnft.star.domain.payment.model.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRes implements Serializable {
    /**
     * 支付单号
     */
    private String orderSn;
    /**
     * 三方支付页面 数据
     */
    private String thirdPage;

//    private Map<String,String> thirdParam;

    private String gatewayApi;
    /**
     * 支付金额
     */
    private String totalMoney;
    /**
     * 支付状态
     */
    private Integer status;
    /**
     * 接口信息
     */
    private String message;

    @ApiModelProperty(value = "跳转路径")
    private String jumpUrl;

}
