package com.starnft.star.application.mq.consumer;

import com.starnft.star.common.ResultCode;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.wallet.model.vo.RechargeVO;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
@RocketMQMessageListener(topic = "STAR-RECHARGE", consumerGroup = "star-consumer-recharge-group", selectorExpression = "callback")
public class RechargeConsumer implements RocketMQListener<PayCheckRes> {

    @Resource
    private WalletService walletService;

    @Override
    public void onMessage(PayCheckRes payCheckRes) {
        if (payCheckRes.getStatus().equals(ResultCode.SUCCESS.getCode())) {
            RechargeVO rechargeVO = BeanColverUtil.colver(payCheckRes, RechargeVO.class);
            boolean isSuccess = walletService.rechargeProcess(rechargeVO);
            if (!isSuccess) {
                log.error("修改余额失败：{}", payCheckRes);
                throw new RuntimeException("修改余额失败");
            }
        }
    }
}
