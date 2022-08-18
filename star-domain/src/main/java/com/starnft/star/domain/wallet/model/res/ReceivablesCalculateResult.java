package com.starnft.star.domain.wallet.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReceivablesCalculateResult implements Serializable {

    /**
     *  计算后金额
     */
    private BigDecimal calculated;
    /**
     * 交易费率
     */
    private String serviceRate;
    /**
     * 版权费率
     */
    private String copyrightRate;

    /**
     * 交易费
     */
    private BigDecimal serviceMoney;
    /**
     * 版权费
     */
    private BigDecimal copyrightMoney;

}
