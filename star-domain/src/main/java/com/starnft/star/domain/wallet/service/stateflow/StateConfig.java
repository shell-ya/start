package com.starnft.star.domain.wallet.service.stateflow;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.service.stateflow.event.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class StateConfig {

    @Resource
    private WaitPayState waitPayState;
    @Resource
    private PayingState payingState;
    @Resource
    private PaySuccessState paySuccessState;
    @Resource
    private PayFailureState payFailureState;
    @Resource
    private PayCloseState payCloseState;

    protected static Map<Enum<StarConstants.Pay_Status>, AbstractState> stateMap = new HashMap<>();

    @PostConstruct
    public void init(){
        stateMap.put(StarConstants.Pay_Status.WAIT_PAY, waitPayState);
        stateMap.put(StarConstants.Pay_Status.PAY_ING, payingState);
        stateMap.put(StarConstants.Pay_Status.PAY_SUCCESS, paySuccessState);
        stateMap.put(StarConstants.Pay_Status.PAY_FAILED, payFailureState);
        stateMap.put(StarConstants.Pay_Status.PAY_CLOSE, payCloseState);
    }
}
