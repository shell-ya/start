package com.starnft.star.domain.order.service.stateflow;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.repository.IOrderRepository;

import javax.annotation.Resource;

public abstract class AbstractOrderState {

    @Resource
    protected IOrderRepository orderRepository;

    public abstract Result waitPay(Long uid, String orderSn, StarConstants.ORDER_STATE orderState);

    public abstract Result payComplete(Long uid, String orderSn, String payNumber, StarConstants.ORDER_STATE orderState);

    public abstract Result payCancel(Long uid, String orderSn, StarConstants.ORDER_STATE orderState);

}
