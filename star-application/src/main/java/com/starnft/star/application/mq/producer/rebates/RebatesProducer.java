package com.starnft.star.application.mq.producer.rebates;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.rebates.model.RebatesMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @Date 2022/7/29 8:49 PM
 * @Author ： shellya
 * 返利（元石）
 */
//@Slf4j
//@Service
//public class RebatesProducer extends BaseProducer {
//
//
//    public void sendRebatesMessage(RebatesMessage message){
//        String destination = String.format(TopicConstants.PAY_REBATES_DESTINATION.getFormat(), TopicConstants.PAY_REBATES_DESTINATION.getTag());
//
//        messageSender.asyncSend(destination, Optional.of(message), success(), rollback());
//    }
//
//
//    private Consumer<SendResult> success() {
//        return sendResult -> {
//
//        };
//    }
//
//    private Runnable rollback() {
//        return () -> {
//
//        };
//    }
//}
