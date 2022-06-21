package com.starnft.star.domain.payment.model.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundRes {
    private String message;
    private Long requestTime;
    private Integer status;
    private String tradeSn;
    private String orderSn;
    private BigDecimal refundAmount;
    private Long refundTime;
}
