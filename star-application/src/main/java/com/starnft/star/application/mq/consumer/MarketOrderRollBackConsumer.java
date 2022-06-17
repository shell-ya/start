package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.domain.order.service.IOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Date 2022/6/17 5:49 PM
 * @Author ： shellya
 */
@Service
@RocketMQMessageListener(topic = "STAR-MARKET-ROLLBACK", consumerGroup = "star-consumer-delay-group", selectorExpression = "rollback")
public class MarketOrderRollBackConsumer implements RocketMQListener<MarketOrderStatus> {


    @Resource
    private IOrderService orderService;

    @Override
    public void onMessage(MarketOrderStatus marketOrderStatus) {
        //订单若仍是待支付状态 取消订单
    }
}
