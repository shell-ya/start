package com.starnft.star.interfaces.controller.trans.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class PayerInfoDTO implements Serializable {
    /**
     * payerAccNo : 200229000050007
     * payerMemID : 666666
     * payerAccName : 沈凡
     */

    private String payerAccNo;
    private String payerMemID;
    private String payerAccName;
}
