package com.starnft.star.domain.sms.adapter;

import com.starnft.star.domain.sms.MessageTypeEnums;
import com.starnft.star.domain.sms.configs.StrategyConfigs;
import com.starnft.star.domain.sms.interfaces.MessageStrategyInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageAdapter implements ApplicationContextAware {
    @Resource
    StrategyConfigs strategyConfigs;
    private ApplicationContext context;

    /**
     * 获取短信发送通道（使用默认配置）
     * @return
     */
    public MessageStrategyInterface getDistributor() {
        MessageTypeEnums defaultMessageType = MessageTypeEnums.getDefaultMessageType(strategyConfigs.getStrategy());
        return (MessageStrategyInterface) context.getBean(defaultMessageType.getStrategy());
    }
    /**
     * 获取短信发送通道（自定义配置）
     * @return
     */
    public MessageStrategyInterface getDistributor(MessageTypeEnums messageTypeEnums) {
        return (MessageStrategyInterface) context.getBean(messageTypeEnums.getStrategy());
    }
    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }
}
