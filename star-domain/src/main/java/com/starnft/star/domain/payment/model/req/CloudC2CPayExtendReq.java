package com.starnft.star.domain.payment.model.req;

import lombok.Data;

import java.util.List;

@Data
public class CloudC2CPayExtendReq {
    private List<Payee> payeeList;
    private Payer payer;
    private String orderDesc;
    private String accountingMode = "1";
    private String payeeBizUserNo;

    @Data
    public static class Payer {
        String payUserId;
        String accountType = "01";
        String remark;
    }

    @Data
    public static class Payee {
        String recvUserId;
        String recvCustomerOrderNo;
        String recvAmt;
        String remark;
    }
}
