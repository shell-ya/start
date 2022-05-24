package com.starnft.star.application.mq.consumer;

import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RocketMQMessageListener(topic = "STAR-WALLET", consumerGroup = "star-consumer-group", selectorExpression = "recharge")
public class RechargeConsumer implements RocketMQListener<PaymentRich> {

    @Resource
    private IPaymentService paymentService;

    @Override
    public void onMessage(PaymentRich paymentRich) {
        PaymentRes pay = paymentService.pay(paymentRich);
        //充值响应 状态机修改记录状态以及支付信息
    }
}
