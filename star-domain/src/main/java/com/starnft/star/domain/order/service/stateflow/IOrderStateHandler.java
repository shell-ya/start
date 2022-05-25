package com.starnft.star.domain.order.service.stateflow;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;

public interface IOrderStateHandler {

    Result waitPay(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);

    Result paySuccess(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);

    Result payFailed(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);

    Result payCancel(String orderSn, Enum<StarConstants.ORDER_STATE> orderState);


}
