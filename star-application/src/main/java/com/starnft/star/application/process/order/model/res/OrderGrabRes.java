package com.starnft.star.application.process.order.model.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderGrabRes implements Serializable {

    //抢单状态
    private Integer status;

    private String message;

    public OrderGrabRes(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
