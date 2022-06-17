package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "STAR-SEC-ROLLBACK", consumerGroup = "star-consumer-secrollback-group", selectorExpression = "rollback")
public class OrderSecRollbackConsumer implements RocketMQListener<OrderGrabStatus> {


    @Override
    public void onMessage(OrderGrabStatus message) {

    }
}
