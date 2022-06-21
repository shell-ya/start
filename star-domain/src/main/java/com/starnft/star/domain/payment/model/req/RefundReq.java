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
    /**
     * 退款流水号
     */
    private  String refundOrderSn;
    /**
     * 退款单号
     */
    private  String orderSn;
    /**
     * 退款原因
     */
    private  String reason;
    /**
     * 回调参数
     */
    private  String composeCallback;
    /**
     * 退款金额
     */
    private BigDecimal totalMoney;
    /**
     * 渠道
     */
    private String payChannel;

}
