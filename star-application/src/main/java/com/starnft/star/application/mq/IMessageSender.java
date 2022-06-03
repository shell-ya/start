package com.starnft.star.application.mq;

import org.apache.rocketmq.client.producer.SendResult;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface IMessageSender {

    <T> void send(final String topic, final Optional<T> message);

    <T> void asyncSend(final String topic, final Optional<T> message, Consumer<SendResult> operationIfSuccess, Runnable failOperation);

    <T> void syncSendDelay(final String topic, final Optional<T> message, long timeout, int delayLevel);

    <T> void syncSendOrderly(final String topic, List<T> messages, String hashKey);

    <T> void sendTransaction(final String topic, final String unique, final String transactionGroup, Optional<T> message);

}
