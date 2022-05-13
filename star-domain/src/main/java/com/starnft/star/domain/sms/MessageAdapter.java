package com.starnft.star.domain.sms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class MessageAdapter implements ApplicationContextAware {
    private ApplicationContext context;
    public  MessageStrategyInterface getDistributor(MessageTypeEnums messageTypeEnums) {
        return (MessageStrategyInterface) context.getBean(messageTypeEnums.getStrategy());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
