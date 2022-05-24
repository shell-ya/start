package com.starnft.star.domain.payment.router.model;

import lombok.Data;

@Data
public class PaymentRouterContext {


    /**
     * 支付渠道
     * */
    private String payChannel;

    /**
     * 当前厂商
     */
    private String currVendor;


}
