package com.starnft.star.domain.sms.adapter;

import com.starnft.star.domain.sms.MessageTypeEnums;
import com.starnft.star.domain.sms.interfaces.MessageStrategyInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class MessageAdapter implements ApplicationContextAware {
    private ApplicationContext context;
    public MessageStrategyInterface getDistributor(MessageTypeEnums messageTypeEnums) {
        return (MessageStrategyInterface) context.getBean(messageTypeEnums.getStrategy());
    }
    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }
}
