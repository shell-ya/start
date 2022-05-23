package com.starnft.star.application.process.wallet.req;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RechargeCallbackReq implements Serializable {
    /** user id*/
    private String userId;
    /** 支付状态*/
    private Integer payStatus;
    /** 第三方交易流水号*/
    private String outTradeNo;
    /** 钱包交易订单号*/
    private String recordSn;

}
