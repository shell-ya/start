package com.starnft.star.application.process.order.model.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCancelReq implements Serializable {

    private Long uid;

    private String orderSn;
}
