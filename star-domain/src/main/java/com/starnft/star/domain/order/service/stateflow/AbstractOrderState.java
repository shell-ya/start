package com.starnft.star.domain.order.service.stateflow;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.repository.IOrderRepository;

import javax.annotation.Resource;

public abstract class AbstractOrderState {

    @Resource
    protected IOrderRepository orderRepository;

    public abstract Result waitPay(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);

    public abstract Result paySuccess(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);

    public abstract Result payFailed(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);

    public abstract Result payCancel(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);

}
