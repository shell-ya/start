package com.starnft.star.application.mq.consumer;

import com.starnft.star.domain.wallet.model.req.TransReq;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2022/7/3 6:19 PM
 * @Author ： shellya
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.receivables}", consumerGroup = "${consumer.group.receivables}", selectorExpression = "callback")
public class ReceivablesConsumer implements RocketMQListener<TransReq> {

    final WalletService walletService;

    @Override
    public void onMessage(TransReq transReq) {
        boolean isSuccess = walletService.doTransaction(transReq);
        if (!isSuccess) {
            log.error("修改余额失败：{}", transReq);
            throw new RuntimeException("修改余额失败");
        }
    }
}
