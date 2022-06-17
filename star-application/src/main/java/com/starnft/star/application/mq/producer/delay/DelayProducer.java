package com.starnft.star.application.mq.producer.delay;

import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.delay.dto.DelayMessage;

import java.util.Optional;

/**
 * @Date 2022/6/17 5:44 PM
 * @Author ： shellya
 */
public class DelayProducer extends BaseProducer {

    /**
     * 延时任务
     */
    public void delay(DelayMessage message,long timeout,int delayLevel){

        messageSender.syncSendDelay("STAR-DELAY-QUEUE:delay", Optional.of(message),timeout,delayLevel);
    }
}
