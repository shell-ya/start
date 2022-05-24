package com.starnft.star.application.mq.producer;

import com.starnft.star.application.mq.IMessageSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public abstract class BaseProducer {
    @Resource
    protected IMessageSender messageSender;
}
