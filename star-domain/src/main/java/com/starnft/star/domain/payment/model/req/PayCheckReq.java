package com.starnft.star.domain.payment.model.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayCheckReq {

    /**
     * 支付渠道
     */
    private String payChannel;

    private String orderSn;
//    private String payChannel;

}
