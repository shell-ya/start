package com.starnft.star.application.mq.producer.socpe;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.scope.model.ScopeMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScopeProducer extends BaseProducer {
    public void sendScopeMessage(ScopeMessage userScopeMessageVO){
        String topic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
        messageSender.send(topic, Optional.of(userScopeMessageVO));
    }
}
