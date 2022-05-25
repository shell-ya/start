package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.notification.vo.NotificationVO;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "STAR-NOTICE", consumerGroup = "star-consumer-noti-group", selectorExpression = "unread")
public class NotificationConsumer implements RocketMQListener<NotificationVO> {

    @Override
    public void onMessage(NotificationVO notificationVO) {
        System.out.println(notificationVO);
    }
}
