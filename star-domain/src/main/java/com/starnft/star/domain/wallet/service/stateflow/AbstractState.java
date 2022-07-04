package com.starnft.star.domain.wallet.service.stateflow;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.repository.IWalletRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;

public abstract class AbstractState {

    @Resource
    protected IWalletRepository walletRepository;

    public abstract Result waitPay(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

    public abstract Result paying(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

    public abstract Result paySuccess(String orderNo, String outTradeNo, BigDecimal currMoney, Enum<StarConstants.Pay_Status> payStatus);

    public abstract Result payFailure(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

    public abstract Result payClose(String orderNo, Enum<StarConstants.Pay_Status> payStatus);

}
