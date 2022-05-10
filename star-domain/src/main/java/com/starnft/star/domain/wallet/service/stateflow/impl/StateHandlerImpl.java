package com.starnft.star.domain.wallet.service.stateflow.impl;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.service.stateflow.IStateHandler;
import com.starnft.star.domain.wallet.service.stateflow.StateConfig;
import org.springframework.stereotype.Service;

@Service
public class StateHandlerImpl extends StateConfig implements IStateHandler {

    @Override
    public Result waitPay(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return stateMap.get(payStatus).waitPay(orderNo, payStatus);
    }

    @Override
    public Result paying(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return stateMap.get(payStatus).paying(orderNo, payStatus);
    }

    @Override
    public Result paySuccess(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return stateMap.get(payStatus).paySuccess(orderNo, payStatus);
    }

    @Override
    public Result payFailure(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return stateMap.get(payStatus).payFailure(orderNo, payStatus);
    }

    @Override
    public Result payClose(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return stateMap.get(payStatus).payClose(orderNo, payStatus);
    }
}
