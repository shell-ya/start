package com.starnft.star.application.mq.producer.notification;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.notification.vo.NotificationVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationProducer extends BaseProducer {

    public void sendNotification(NotificationVO notificationVO){
        String topic = String.format(TopicConstants.NOTICE_UNREAD_DESTINATION.getFormat()
                ,TopicConstants.NOTICE_UNREAD_DESTINATION.getTag());

        messageSender.send(topic, Optional.of(notificationVO));
    }
}
