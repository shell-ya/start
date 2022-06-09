package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.exchange.dto.ExchangeDTO;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "STAR-EXCHANGE", consumerGroup = "star-consumer-exchange-rank-group", selectorExpression = "unread")
public class ExchangeRankConsumer implements RocketMQListener<ExchangeDTO> {
    @Override
    public void onMessage(ExchangeDTO dto) {

    }
}
