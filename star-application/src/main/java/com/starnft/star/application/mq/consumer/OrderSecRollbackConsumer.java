package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "${consumer.topic.secrollback}", consumerGroup = "${consumer.group.secrollback}"
        , selectorExpression = "rollback", messageModel = MessageModel.CLUSTERING)
public class OrderSecRollbackConsumer implements RocketMQListener<OrderGrabStatus> {


    @Override
    public void onMessage(OrderGrabStatus message) {

    }
}
