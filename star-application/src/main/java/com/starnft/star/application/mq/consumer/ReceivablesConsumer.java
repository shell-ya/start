package com.starnft.star.application.mq.consumer;

import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.wallet.model.req.CalculateReq;
import com.starnft.star.domain.wallet.model.req.WalletPayRequest;
import com.starnft.star.domain.wallet.model.res.ReceivablesCalculateResult;
import com.starnft.star.domain.wallet.model.res.WalletPayResult;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * @Date 2022/7/3 6:19 PM
 * @Author ： shellya
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.receivables}", consumerGroup = "${consumer.group.receivables}", selectorExpression = "callback")
public class ReceivablesConsumer implements RocketMQListener<WalletPayRequest> {

    final WalletService walletService;
    final RedisUtil redisUtil;

    @Override
    public void onMessage(WalletPayRequest payRequest) {
        try {
            log.info("市场订单：{},交易成功添加卖方余额 消息实体:{}", payRequest.getOrderSn(), payRequest);
            Long processed = redisUtil.hincr(RedisKey.MARKET_RECEIVABLES.getKey(), payRequest.getOrderSn(), 1L);
            if (processed > 1) {
                log.info("该订单:{}已处理", payRequest.getOrderSn());
                return;
            }
            walletService.balanceVerify(0L, payRequest.getPayAmount());
//        walletService.balanceVerify(payRequest.getFromUid(), payRequest.getPayAmount());
            //计算手续费
            ReceivablesCalculateResult calculateResult = walletService.ReceivablesMoneyCalculate(new CalculateReq(payRequest.getPayAmount(), payRequest.getChannel()));
            log.info("市场订单：{}，买方支付金额：{},卖方收款金额:{},交易手续费：{},版权费:{}", payRequest.getOrderSn(), payRequest.getPayAmount(),
                    calculateResult.getCalculated(), calculateResult.getServiceMoney(), calculateResult.getCopyrightMoney());
            Supplier<Result> feeSupplier = () -> {
                boolean isSuccess = walletService.feeProcess(payRequest.getOrderSn(), calculateResult.getServiceMoney().add(calculateResult.getCopyrightMoney()));
                return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("修改交易记录手续费失败");
            };
            payRequest.setPayAmount(calculateResult.getCalculated());
            payRequest.setDoModifyRecord(feeSupplier);
            WalletPayResult walletPayResult = walletService.doWalletPay(payRequest);
            if (!(ResultCode.SUCCESS.getCode().equals(walletPayResult.getStatus()))) {
                log.error("修改余额失败：{}", payRequest);
                throw new RuntimeException("修改余额失败");
            }
            redisUtil.hincr(RedisKey.MARKET_RECEIVABLES.getKey(), payRequest.getOrderSn(), 1L);
        } finally {
            walletService.threadClear();
        }
    }
}
