package com.starnft.star.application.mq.consumer;

import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.wallet.model.vo.RechargeVO;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.domain.wallet.service.stateflow.IStateHandler;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.recharge}", consumerGroup = "${consumer.group.recharge}", selectorExpression = "callback")
public class RechargeConsumer implements RocketMQListener<PayCheckRes> {

    private Logger log = LoggerFactory.getLogger(RechargeConsumer.class);

    final WalletService walletService;

    final RedisUtil redisUtil;

    final IStateHandler stateHandler;

    @Override
    public void onMessage(PayCheckRes payCheckRes) {
        //消息幂等校验
        messageIdempotentVerify(payCheckRes.getOrderSn(), payCheckRes.getTransSn());
        if (payCheckRes.getStatus().equals(ResultCode.SUCCESS.getCode())) {
            RechargeVO rechargeVO = BeanColverUtil.colver(payCheckRes, RechargeVO.class);
            boolean isSuccess = walletService.rechargeProcess(rechargeVO);
            if (!isSuccess) {
                log.error("修改余额失败：{}", payCheckRes);
                throw new RuntimeException("修改余额失败");
            }
            return;
        }

        //修改余额失败 0失败 1 成功
        redisUtil.set(String.format(RedisKey.REDIS_TRANSACTION_SUCCESS.getKey(), payCheckRes.getOrderSn()), 0, RedisKey.REDIS_TRANSACTION_SUCCESS.getTime());
        WalletRecordVO walletRecordVO = walletService.queryWalletRecordByOrderNo(payCheckRes.getOrderSn());
        Result result = stateHandler.payFailure(payCheckRes.getOrderSn(), StarConstants.Pay_Status.valueOf(walletRecordVO.getPayStatus()));
        if (!result.getCode().equals(ResultCode.SUCCESS.getCode())) {
            log.error("修改失败状态有误：{}", payCheckRes);
            throw new RuntimeException(result.getInfo());
        }
    }

    private void messageIdempotentVerify(String orderSn, String transSn) {
        String idempotent = String.format(RedisKey.REDIS_IDEMPOTENT_MARK.getKey(), orderSn + transSn);
        if (redisUtil.hasKey(idempotent)) {
            log.error("单号:[{}] 流水号:[{}] mq重复消费，幂等校验", orderSn, transSn);
            throw new RuntimeException("消息重复消费，幂等验证拦截");
        }
        redisUtil.incr(idempotent, 1);
        redisUtil.expire(idempotent, RedisKey.REDIS_IDEMPOTENT_MARK.getTime(), RedisKey.REDIS_IDEMPOTENT_MARK.getTimeUnit());
    }
}
