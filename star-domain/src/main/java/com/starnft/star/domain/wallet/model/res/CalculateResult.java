package com.starnft.star.domain.wallet.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CalculateResult implements Serializable {

    /**
     *  计算后金额
     */
    private String calculated;

    /**
     * 费率
     */
    private String rate;

}
