package com.starnft.star.domain.identify.adapter;

import com.starnft.star.domain.identify.interfaces.IdentifyStrategyInterface;
import com.starnft.star.domain.sms.MessageTypeEnums;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IdentifyAdapter implements ApplicationContextAware {

    private ApplicationContext context;

    public IdentifyStrategyInterface getDistributor(MessageTypeEnums messageTypeEnums) {
        Map<String, IdentifyStrategyInterface> interfaceMap = context.getBeansOfType(IdentifyStrategyInterface.class);
        return interfaceMap.get(messageTypeEnums.getStrategy());
    }
    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }
}
