package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScopeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.credits}", consumerGroup = "${consumer.group.credits}", selectorExpression = "modify")
public class ScopeConsumer implements RocketMQListener<ScopeMessage> {
    @Resource
    IScopeCore iScopeCore;
    @Override
    public void onMessage(ScopeMessage userScopeMessageVO) {
//        iScopeCore.calculateUserScope(userScopeMessageVO);
    }
}
