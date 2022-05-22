package com.starnft.star.domain.identify.adapter;

import com.starnft.star.domain.identify.interfaces.IdentifyStrategyInterface;
import com.starnft.star.domain.sms.MessageTypeEnums;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class IdentifyAdapter implements ApplicationContextAware {
    private ApplicationContext context;
    public IdentifyStrategyInterface getDistributor(MessageTypeEnums messageTypeEnums) {
        return (IdentifyStrategyInterface) context.getBean(messageTypeEnums.getStrategy());
    }
    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }
}
