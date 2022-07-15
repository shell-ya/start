package com.starnft.star.application.mq.consumer;

import com.starnft.star.domain.wallet.model.req.CalculateReq;
import com.starnft.star.domain.wallet.model.req.TransReq;
import com.starnft.star.domain.wallet.model.res.ReceivablesCalculateResult;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        log.info("市场订单：{},交易成功添加卖方余额 消息实体:{}", transReq.getOrderSn(), transReq);
        walletService.balanceVerify(transReq.getUid(), transReq.getPayAmount());
        //计算手续费
        ReceivablesCalculateResult calculateResult = walletService.ReceivablesMoneyCalculate(new CalculateReq(transReq.getPayAmount(), transReq.getPayChannel()));
        log.info("市场订单：{}，买方支付金额：{},卖方收款金额:{},交易手续费：{},版权费:{}", transReq.getOrderSn(), transReq.getPayAmount(),
                calculateResult.getCalculated(), calculateResult.getServiceMoney(), calculateResult.getCopyrightMoney());
        transReq.setPayAmount(new BigDecimal(calculateResult.getCalculated()));
        boolean isSuccess = walletService.doTransaction(transReq);
        if (!isSuccess) {
            log.error("修改余额失败：{}", transReq);
            throw new RuntimeException("修改余额失败");
        }
    }
}
