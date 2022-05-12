package com.starnft.star.application.process.user.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeCallbackRes implements Serializable {

    private Long userId;
    /** 平台交易流水号*/
    private String transactionSn;
    /** 第三方交易流水号*/
    private String outTradeNo;
    /** 订单号*/
    private String orderSn;
    /** 交易金额*/
    private BigDecimal money;
    /** 支付渠道*/
    private String channel;
    /** 支付时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private String payTime;
    /** 支付状态*/
    private String status;

}
