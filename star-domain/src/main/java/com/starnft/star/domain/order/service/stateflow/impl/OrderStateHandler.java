package com.starnft.star.domain.order.service.stateflow.impl;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.service.stateflow.IOrderStateHandler;
import com.starnft.star.domain.order.service.stateflow.OrderStateConfig;
import org.springframework.stereotype.Service;

@Service
public class OrderStateHandler extends OrderStateConfig implements IOrderStateHandler {


    @Override
    public Result waitPay(Long uid, String orderSn, StarConstants.ORDER_STATE orderState) {
        return orderStateMap.get(orderState).waitPay(uid, orderSn, orderState);
    }

    @Override
    public Result payComplete(Long uid, String orderSn, String payNumber, StarConstants.ORDER_STATE orderState) {
        return orderStateMap.get(orderState).payComplete(uid, orderSn, payNumber, orderState);
    }


    @Override
    public Result payCancel(Long uid, String orderSn, StarConstants.ORDER_STATE orderState) {
        return orderStateMap.get(orderState).payCancel(uid, orderSn, orderState);
    }
}
