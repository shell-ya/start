package com.starnft.star.application.mq.producer;

import com.alibaba.fastjson.JSONObject;
import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.support.mq.IMessageLogRepository;
import com.starnft.star.domain.support.mq.model.MessageLogVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Component
public class RocketMQProducer implements IMessageSender {

    @Resource
    private RocketMQTemplate template;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    private IMessageLogRepository messageLogRepository;


    @Value("${namespace: rocketmq-8geezgooejbg|STAR%}")
    private String namespace;

    /**
     * @param topic 格式 topic:tag
     * @author Ryan Z / haoran
     * @description 同步发送消息
     * @date 2022/5/18
     */
    @Override
    public <T> void send(final String topic, final Optional<T> message) {
        verifyFormat(topic);
        message.ifPresent(msg -> {
            SendResult sendResult = template.syncSend(getCompleteTopic(topic), JSONObject.toJSONString(msg));
            String msgId = sendResult.getMsgId();
            SendStatus sendStatus = sendResult.getSendStatus();
            persistMessage(topic, msg, sendResult, sendStatus, msgId);
        });

    }

    private String getCompleteTopic(String topic) {
        return namespace + topic;
    }

    /**
     * @param topic 格式 topic:tag
     * @author Ryan Z / haoran
     * @description 异步发送消息
     * @date 2022/5/18
     */
    @Override
    public <T> void asyncSend(final String topic, final Optional<T> message,
                              Consumer<SendResult> operationIfSuccess, Runnable failOperation) {
//        verifyFormat(topic);
        message.ifPresent(msg -> {
            template.asyncSend(getCompleteTopic(topic), JSONObject.toJSONString(msg), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    String msgId = sendResult.getMsgId();
                    SendStatus sendStatus = sendResult.getSendStatus();
                    //如果成功执行的操作
                    operationIfSuccess.accept(sendResult);
                    if (log.isDebugEnabled()) {
                        log.debug("[{}] 消息同步发送成功，消息id: {} 消息内容:{}", topic, msgId, JSONObject.toJSONString(msg));
                    }
                }

                @Override
                public void onException(Throwable throwable) {
                    IIdGenerator iIdGenerator = idGeneratorMap.get(StarConstants.Ids.RandomNumeric);
                    long msgId = iIdGenerator.nextId();
                    log.error("[{}] 消息同步发送失败，消息id: {} 消息内容:{} 异常：{}", topic, msgId, JSONObject.toJSONString(msg), throwable.getMessage());
                    //消息记录落盘
                    boolean isSuccess = writeLog(topic, JSONObject.toJSONString(msg), String.valueOf(msgId), StarConstants.NORMAL_STATUS.FAILURE.name());
                    if (!isSuccess) {
                        throw new RuntimeException(StarError.PERSISTENT_FAIL.getErrorMessage());
                    }
                    failOperation.run();
                }
            });
        });
    }

    /**
     * @param topic      格式 topic:tag
     * @param message    消息体
     * @param timeout    超时
     * @param delayLevel 延时等级：现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，
     *                   从1s到2h分别对应着等级 1 到 18，消息消费失败会进入延时消息队列
     *                   "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
     * @author Ryan Z / haoran
     * @description 同步发送延时消息
     * @date 2022/5/18
     */
    @Override
    public <T> void syncSendDelay(final String topic, final Optional<T> message, long timeout, int delayLevel) {
        verifyFormat(topic);
        message.ifPresent(msg -> {
            SendResult sendResult = template.syncSend(getCompleteTopic(topic),
                    MessageBuilder.withPayload(JSONObject.toJSONString(msg)).build(), timeout, delayLevel);
            String msgId = sendResult.getMsgId();
            SendStatus sendStatus = sendResult.getSendStatus();
            persistMessage(topic, msg, sendResult, sendStatus, msgId);
        });
    }

    /**
     * 同步发送顺序消息
     *
     * @param topic 格式 topic:tag
     */
    @Override
    public <T> void syncSendOrderly(final String topic, List<T> messages, String hashKey) {
        verifyFormat(topic);
        for (T message : messages) {
            SendResult sendResult = this.template.syncSendOrderly(getCompleteTopic(topic),
                    MessageBuilder.withPayload(JSONObject.toJSONString(message)).build(), hashKey);
            String msgId = sendResult.getMsgId();
            SendStatus sendStatus = sendResult.getSendStatus();
            persistMessage(topic, message, sendResult, sendStatus, msgId);
        }
    }


    /**
     * 发送事务消息
     *
     * @param topic            topic
     * @param unique           唯一标识
     * @param transactionGroup 事务组
     * @param message          消息
     * @param <T>
     */
    @Override
    public <T> void sendTransaction(final String topic, final String unique, final String transactionGroup, Optional<T> message) {
        verifyFormat(topic);
        message.ifPresent(msg -> {
            TransactionSendResult result = template.sendMessageInTransaction(transactionGroup,
                    getCompleteTopic(topic),
                    MessageBuilder.withPayload(msg)
                            .setHeader(RocketMQHeaders.TRANSACTION_ID,
                                    String.valueOf(idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId()))
                            .setHeader("unique", unique)
                            .build()
                    , msg);
            SendStatus sendStatus = result.getSendStatus();
            String msgId = result.getMsgId();
            persistMessage(topic, msg, result, sendStatus, msgId);
        });
    }


    /**
     * 保存失败消息
     *
     * @param topic      topic
     * @param message    message
     * @param result     result
     * @param sendStatus sendStatus
     * @param msgId      msgId
     * @param <T>
     */
    private <T> void persistMessage(String topic, T message, SendResult result, SendStatus sendStatus, String msgId) {
        if (sendStatus.equals(SendStatus.SEND_OK)) {
            if (log.isDebugEnabled()) {
                log.debug("[{}] 消息发送成功，消息id: {} 消息内容:{}", getCompleteTopic(topic), msgId, JSONObject.toJSONString(message));
            }
        } else {
            log.error("[{}] 消息发送失败，消息id: {} 消息内容:{}", getCompleteTopic(topic), msgId, JSONObject.toJSONString(message));
            //消息记录落盘
            boolean isSuccess = writeLog(topic, JSONObject.toJSONString(message), result.getMsgId(), sendStatus.name());
            if (!isSuccess) {
                throw new RuntimeException(StarError.PERSISTENT_FAIL.getErrorMessage());
            }
        }
    }

    /**
     * @param topic
     * @author Ryan Z / haoran
     * @description 规范检查
     * @date 2022/5/18
     */
    private void verifyFormat(final String topic) {
        for (TopicConstants value : TopicConstants.values()) {
            if (String.format(value.getFormat(), value.getTag()).equals(topic)) {
                return;
            }
        }
        throw new RuntimeException(StarError.MESSAGE_TOPIC_NOT_FOUND.getErrorMessage());
    }

    private boolean writeLog(String topic, String message, String msgId, String sendStatus) {
        return messageLogRepository.writeLog(MessageLogVO.builder().messageId(msgId)
                .messageBody(JSONObject.toJSONString(message))
                .status(sendStatus)
                .terminate(StarConstants.MESSAGE_TERMINAL.Producer.name())
                .topic(getCompleteTopic(topic)).build());
    }


}
