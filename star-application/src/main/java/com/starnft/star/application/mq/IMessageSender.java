package com.starnft.star.application.mq;

import java.util.List;
import java.util.Optional;

public interface IMessageSender {

    <T> void send(final String topic, final Optional<T> message);

    <T> void asyncSend(final String topic, final Optional<T> message);

    <T> void syncSendDelay(final String topic, final Optional<T> message, long timeout, int delayLevel);

    <T> void syncSendOrderly(final String topic, List<T> messages, String hashKey);

}
