package com.starnft.star.domain.order.service.stateflow.event;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.service.stateflow.AbstractOrderState;
import org.springframework.stereotype.Component;

@Component
public class OrderPayCompleteState extends AbstractOrderState {
    @Override
    public Result waitPay(Long uid, String orderSn, StarConstants.ORDER_STATE orderState) {
        return Result.buildErrorResult("该状态不可修改为待支付");
    }

    @Override
    public Result payComplete(Long uid, String orderSn,String payNumber, StarConstants.ORDER_STATE orderState) {
        return Result.buildErrorResult("该状态已经是已完成");
    }

    @Override
    public Result payCancel(Long uid, String orderSn, StarConstants.ORDER_STATE orderState) {
        return Result.buildErrorResult("该状态不可修改为已取消");
    }
}
