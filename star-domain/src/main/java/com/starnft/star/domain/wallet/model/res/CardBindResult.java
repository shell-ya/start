package com.starnft.star.domain.wallet.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CardBindResult implements Serializable {
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 持卡人姓名
     */
    private String cardName;
    /**
     * 银行简称
     */
    private String bankShortName;

}
