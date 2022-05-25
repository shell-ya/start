package com.starnft.star.domain.order.service.stateflow.impl;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.service.stateflow.IOrderStateHandler;
import com.starnft.star.domain.order.service.stateflow.OrderStateConfig;
import org.springframework.stereotype.Service;

@Service
public class OrderStateHandler extends OrderStateConfig implements IOrderStateHandler {


    @Override
    public Result waitPay(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return orderStateMap.get(orderState).waitPay(orderSn, orderState);
    }

    @Override
    public Result paySuccess(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return orderStateMap.get(orderState).paySuccess(orderSn, orderState);
    }

    @Override
    public Result payFailed(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return orderStateMap.get(orderState).payFailed(orderSn, orderState);
    }

    @Override
    public Result payCancel(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return orderStateMap.get(orderState).payCancel(orderSn, orderState);
    }
}
