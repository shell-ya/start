package com.starnft.star.domain.order.service.model.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPlaceRes implements Serializable {

    //订单状态
    private Integer orderStatus;

    //订单流水号
    private String orderSn;


}
