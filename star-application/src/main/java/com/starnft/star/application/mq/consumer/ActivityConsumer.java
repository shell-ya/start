package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.event.ActivityEventAdapter;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.credits}", consumerGroup = "${consumer.group.credits}", selectorExpression = "modify")
public class ActivityConsumer implements RocketMQListener<ActivityEventReq> {
    @Resource
    ActivityEventAdapter activityEventAdapter;
    @Override
    public void onMessage(ActivityEventReq activityEventReq) {
       activityEventAdapter.handler(activityEventReq);
    }
}
