package com.starnft.star.domain.payment.model.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRich implements Serializable {

    /**
     * 支付渠道
     */
    private String payChannel;
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 交易流水号
     */
    private String tradeSn;

    /**
     * 订单号
     */
    private String orderSn;
}
