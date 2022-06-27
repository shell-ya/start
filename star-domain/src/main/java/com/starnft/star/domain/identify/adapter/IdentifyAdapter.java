package com.starnft.star.domain.identify.adapter;

import com.starnft.star.domain.identify.IdentifyTypeEnums;
import com.starnft.star.domain.identify.interfaces.IdentifyStrategyInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IdentifyAdapter implements ApplicationContextAware {

    private ApplicationContext context;

    public IdentifyStrategyInterface getDistributor(IdentifyTypeEnums identifyTypeEnums) {
        Map<String, IdentifyStrategyInterface> interfaceMap = context.getBeansOfType(IdentifyStrategyInterface.class);
        return interfaceMap.get(identifyTypeEnums.getStrategy());
    }
    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }
}
