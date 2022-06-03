package com.starnft.star.domain.order.service.stateflow.event;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.stateflow.AbstractOrderState;
import org.springframework.stereotype.Component;

@Component
public class OrderWaitPayState extends AbstractOrderState {

    @Override
    public Result waitPay(Long uid, String orderSn, StarConstants.ORDER_STATE orderState) {
        OrderVO orderVO = orderRepository.queryOrderByCondition(uid, orderSn);
        if (null == orderVO) {
            return Result.buildErrorResult("订单不存在");
        }
        return Result.buildErrorResult("订单已经在等待支付状态");
    }

    @Override
    public Result payComplete(Long uid, String orderSn, String payNumber, StarConstants.ORDER_STATE orderState) {
        boolean isSuccess = orderRepository.updateOrder(uid, orderSn, orderState.getCode(), payNumber);
        return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("状态修改为已完成失败");
    }


    @Override
    public Result payCancel(Long uid, String orderSn, StarConstants.ORDER_STATE orderState) {
        boolean isSuccess = orderRepository.updateOrder(uid, orderSn, orderState.getCode(), null);
        return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("状态修改为已取消失败");
    }
}
