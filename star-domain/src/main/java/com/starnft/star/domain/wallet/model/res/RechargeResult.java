package com.starnft.star.domain.wallet.model.res;

public class RechargeResult {

    /** 交易流水号*/
    private String orderNo;

    /** 交易状态*/
    private String status;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
