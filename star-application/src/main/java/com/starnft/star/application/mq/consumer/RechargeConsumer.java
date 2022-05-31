package com.starnft.star.application.mq.consumer;

import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RocketMQMessageListener(topic = "STAR-RECHARGE", consumerGroup = "star-consumer-recharge-group", selectorExpression = "callback")
public class RechargeConsumer implements RocketMQListener<PayCheckRes> {

    @Resource
    private IPaymentService paymentService;

    @Override
    public void onMessage(PayCheckRes payCheckRes) {

    }
}
