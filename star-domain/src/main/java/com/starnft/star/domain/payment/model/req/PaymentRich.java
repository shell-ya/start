package com.starnft.star.domain.payment.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private String clientIp;

    private String frontUrl;
    private String totalMoney;
    /**
     * 订单号
     */
    private String orderSn;
}
