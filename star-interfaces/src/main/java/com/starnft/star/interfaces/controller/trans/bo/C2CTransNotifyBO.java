package com.starnft.star.interfaces.controller.trans.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * C2C转账BO
 */
@NoArgsConstructor
@Data
public class C2CTransNotifyBO implements Serializable {


    /**
     * amount : 0.12
     * orderNo : 1y7cun3t24a6kmzewe8i
     * mid : 6888806044846
     * orderStatus : 00
     * sandSerialNo : CEAS22101820300770300000181219
     * feeAmt : 0
     * userFeeAmt : 0
     * payerInfo : {"payerAccNo":"200229000050007","payerMemID":"666666","payerAccName":"沈凡"}
     * transType : C2C_TRANSFER
     * payeeInfo : {"payeeMemID":"536952750","payeeAccNo":"200229000020004","payeeAccName":"薛明阳"}
     * respTime : 20221018211252
     * respMsg : 成功
     * respCode : 00000
     */

    /**
     * 交易金额
     */
    private Double amount;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商户id
     */
    private String mid;

    /**
     *
     */
    private String orderStatus;

    /**
     *
     */
    private String sandSerialNo;

    /**
     *
     */
    private Integer feeAmt;

    /**
     *
     */
    private Integer userFeeAmt;

    /**
     * 付款人信息
     */
    private PayerInfoDTO payerInfo;

    /**
     *
     */
    private String transType;

    /**
     * 收款人信息
     */
    private PayeeInfoDTO payeeInfo;

    /**
     *
     */
    private String respTime;

    /**
     *
     */
    private String respMsg;

    /**
     * 响应码
     */
    private String respCode;
}
