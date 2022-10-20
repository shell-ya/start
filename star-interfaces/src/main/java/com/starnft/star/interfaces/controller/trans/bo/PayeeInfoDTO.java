package com.starnft.star.interfaces.controller.trans.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class PayeeInfoDTO implements Serializable {
    /**
     * payeeMemID : 536952750
     * payeeAccNo : 200229000020004
     * payeeAccName : 薛明阳
     */

    private String payeeMemID;
    private String payeeAccNo;
    private String payeeAccName;
}
