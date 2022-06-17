package com.starnft.star.application.mq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Service;

/**
 * @Date 2022/6/17 5:49 PM
 * @Author ： shellya
 */
@Service
@RocketMQMessageListener(topic = "STAR-DELAY-QUEUE", consumerGroup = "star-consumer-delay-group", selectorExpression = "delay")
public class DelayConsumer {
}
