package com.starnft.star.domain.order.service.stateflow;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.service.stateflow.event.OrderPayCancelState;
import com.starnft.star.domain.order.service.stateflow.event.OrderPayFailedState;
import com.starnft.star.domain.order.service.stateflow.event.OrderPaySuccessState;
import com.starnft.star.domain.order.service.stateflow.event.OrderWaitPayState;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class OrderStateConfig {

    @Resource
    private OrderWaitPayState orderWaitPayState;

    @Resource
    private OrderPaySuccessState orderPaySuccessState;

    @Resource
    private OrderPayFailedState orderPayFailedState;

    @Resource
    private OrderPayCancelState orderPayCancelState;

    protected static Map<Enum<StarConstants.ORDER_STATE>, AbstractOrderState> orderStateMap= new ConcurrentHashMap<>();

    @PostConstruct
    private void initStates(){
        orderStateMap.put(StarConstants.ORDER_STATE.WAIT_PAY,orderWaitPayState);
        orderStateMap.put(StarConstants.ORDER_STATE.PAY_SUCCESS,orderPaySuccessState);
        orderStateMap.put(StarConstants.ORDER_STATE.PAY_FAILED,orderPayFailedState);
        orderStateMap.put(StarConstants.ORDER_STATE.PAY_CANCEL,orderPayCancelState);

    }
}
