package com.starnft.star.domain.order.service.model.wrapper;

import com.starnft.star.common.constant.StarConstants;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class ReqWrapper<T> {

    private StarConstants.OrderType orderType;

    private Method operation;

    private T request;

    public ReqWrapper(StarConstants.OrderType orderType, Method operation, T request) {
        this.orderType = orderType;
        this.operation = operation;
        this.request = request;
    }

}
