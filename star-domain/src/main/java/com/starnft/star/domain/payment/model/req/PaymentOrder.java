package com.starnft.star.domain.payment.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {
    /**
     * 支付渠道
     */
    private String payChannel;
    /**
    /**
     * 订单号
     */
    private String orderSn;
}
