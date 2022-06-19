package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.domain.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Date 2022/6/17 5:49 PM
 * @Author ： shellya
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "STAR-MARKET-ROLLBACK", consumerGroup = "star-consumer-delay-group", selectorExpression = "rollback",messageModel = MessageModel.CLUSTERING)
public class MarketOrderRollBackConsumer implements RocketMQListener<MarketOrderStatus> {


    @Resource
    private IOrderService orderService;

    @Override
    public void onMessage(MarketOrderStatus marketOrderStatus) {
        //订单若仍是待支付状态 取消订单
        log.info("订单详情：{}",marketOrderStatus.toString());
    }
}
