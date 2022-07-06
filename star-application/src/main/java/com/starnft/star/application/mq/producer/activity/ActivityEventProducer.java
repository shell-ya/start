package com.starnft.star.application.mq.producer.activity;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ActivityEventProducer extends BaseProducer {
    public void sendScopeMessage(ActivityEventReq activityEventReq){
        String topic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
        messageSender.send(topic, Optional.of(activityEventReq));
    }

}
