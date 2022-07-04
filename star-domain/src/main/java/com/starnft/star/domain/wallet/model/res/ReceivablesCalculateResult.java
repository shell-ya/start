package com.starnft.star.domain.wallet.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReceivablesCalculateResult implements Serializable {

    /**
     *  计算后金额
     */
    private String calculated;
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
    private String serviceMoney;
    /**
     * 版权费
     */
    private String copyrightMoney;

}
