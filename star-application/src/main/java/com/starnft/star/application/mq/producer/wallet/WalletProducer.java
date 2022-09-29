package com.starnft.star.application.mq.producer.wallet;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.domain.wallet.model.req.WalletPayRequest;
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
    public void receivablesCallback(WalletPayRequest message) {

        String destination = String.format(TopicConstants.WALLER_RECEIVABLES_DESTINATION.getFormat(), TopicConstants.WALLER_RECEIVABLES_DESTINATION.getTag());

        messageSender.asyncSend(destination, Optional.of(message), success(), rollback());
    }

    /**
     * 订单支付路由
     *
     * @param walletPayRequest
     */
    public void syncPay(WalletPayRequest walletPayRequest) {
        String destination = String.format(TopicConstants.WALLER_PAY_DESTINATION.getFormat(), TopicConstants.WALLER_PAY_DESTINATION.getTag());
        messageSender.send(destination, Optional.of(walletPayRequest));
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
