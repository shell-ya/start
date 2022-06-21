package com.starnft.star.domain.payment.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundReq {
    private  String refundOrderSn;
    private  String orderSn;
    private  String reason;
    private  String composeCallback;
    private BigDecimal totalMoney;
    private String payChannel;

}
