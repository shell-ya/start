package com.starnft.star.application.mq.producer.wallet;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletProducer extends BaseProducer {

    public void sendRecharge(PaymentRich paymentRich){
        String topic = String.format(TopicConstants.WALLET_RECHARGE_DESTINATION.getFormat()
                ,TopicConstants.WALLET_RECHARGE_DESTINATION.getTag());

        messageSender.send(topic, Optional.of(paymentRich));
    }
}
