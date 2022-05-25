package com.starnft.star.domain.order.service.stateflow.event;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.service.stateflow.AbstractOrderState;
import org.springframework.stereotype.Component;

@Component
public class OrderPayCancelState extends AbstractOrderState {

    @Override
    public Result waitPay(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return null;
    }

    @Override
    public Result paySuccess(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return null;
    }

    @Override
    public Result payFailed(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return null;
    }

    @Override
    public Result payCancel(String orderSn, Enum<StarConstants.ORDER_STATE> orderState) {
        return null;
    }
}
