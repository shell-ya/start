package com.starnft.star.domain.payment.model.res;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayCheckRes implements Serializable {

    private Integer status;

    private String message;

    private String uid;

    private String orderSn;

    private String transSn;

    private BigDecimal totalAmount;

    private String payChannel;

    //todo 回调相应参数

}
