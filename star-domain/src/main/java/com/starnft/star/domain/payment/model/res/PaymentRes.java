package com.starnft.star.domain.payment.model.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRes implements Serializable {
    private String orderSn;
    private String payment;
    private String totalMoney;

}
