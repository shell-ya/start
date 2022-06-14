package com.starnft.star.domain.wallet.util.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeResult {

    /**
     * 版权费
     */
    private BigDecimal copyrightFee;
    /**
     * 服务费
     */
    private BigDecimal serviceFee;
    /**
     * 最终实付
     */
    private BigDecimal finalCost;


    public FeeResult(BigDecimal copyrightFee, BigDecimal serviceFee, BigDecimal finalCost) {
        this.copyrightFee = copyrightFee;
        this.serviceFee = serviceFee;
        this.finalCost = finalCost;
    }
}
