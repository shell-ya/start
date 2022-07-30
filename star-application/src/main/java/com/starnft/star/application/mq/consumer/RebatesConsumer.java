package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.rebates.RebatesProcess;
import com.starnft.star.application.process.rebates.model.RebatesMessage;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2022/7/29 8:52 PM
 * @Author ： shellya
 * 返利（元石）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.rebates}", consumerGroup = "${consumer.group.rebates}", selectorExpression = "pay")
public class RebatesConsumer implements RocketMQListener<RebatesMessage> {

    private final RebatesProcess rebatesProcess;

    /**
     * 购买人有邀请人id 返还元石 如果订单是秒杀订单 返还等比金额元石 是市场订单 返回20%元石
     */
    @Override
    public void onMessage(RebatesMessage rebatesMessage) {

    }


}
