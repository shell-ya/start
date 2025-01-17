package com.starnft.star.domain.wallet.service.stateflow;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;

import java.math.BigDecimal;

public interface IStateHandler {

    Result waitPay(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

    Result paying(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

    Result paySuccess(String orderNo, String outTradeNo, BigDecimal currMoney, Enum<StarConstants.Pay_Status> payStatus);

    Result payFailure(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

    Result payClose(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

}
