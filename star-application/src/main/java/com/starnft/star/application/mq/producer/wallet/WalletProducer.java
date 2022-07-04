package com.starnft.star.application.mq.producer.wallet;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.domain.wallet.model.req.TransReq;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class WalletProducer extends BaseProducer {

    /**
     * MQ异步
     *
     * @param message
     */
    public void receivablesCallback(TransReq message) {

        String destination = String.format(TopicConstants.WALLER_RECEIVABLES_DESTINATION.getFormat(), TopicConstants.WALLER_RECEIVABLES_DESTINATION.getTag());

        messageSender.asyncSend(destination, Optional.of(message), success(), rollback());
    }

    private Consumer<SendResult> success() {
        return sendResult -> {

        };
    }

    private Runnable rollback() {
        return () -> {

        };
    }

}
