package com.starnft.star.domain.order.service.stateflow;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;

public interface IOrderStateHandler {

    Result waitPay(Long uid, String orderSn, StarConstants.ORDER_STATE orderState);

    Result payComplete(Long uid, String orderSn, String payNumber, StarConstants.ORDER_STATE orderState);

    Result payCancel(Long uid, String orderSn, StarConstants.ORDER_STATE orderState);


}
