package com.starnft.star.domain.payment.model.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayCheckRes implements Serializable {

    private Integer status;

    private String message;

    //todo 回调相应参数

}
