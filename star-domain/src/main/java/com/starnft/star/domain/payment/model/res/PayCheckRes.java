package com.starnft.star.domain.payment.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayCheckRes implements Serializable {

    private Integer status;

    private String message;

    private String uid;

    private String orderSn;

    private String transSn;

    private BigDecimal totalAmount;

    private String payChannel;

    //todo 回调相应参数

    private String sandSerialNo;
}
