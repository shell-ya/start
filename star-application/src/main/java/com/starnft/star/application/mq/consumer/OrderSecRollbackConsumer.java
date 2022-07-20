package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
@RocketMQMessageListener(topic = "${consumer.topic.secrollback}", consumerGroup = "${consumer.group.secrollback}", selectorExpression = "rollback")
public class OrderSecRollbackConsumer implements RocketMQListener<OrderGrabStatus> {


    @Resource
    private IOrderService orderService;

    @Resource
    private RedisLockUtils redisLockUtils;

    @Resource
    private RedisUtil redisUtil;

    // 3分钟取消订单
    @Override
    public void onMessage(OrderGrabStatus message) {
        //防止用户在时间快结束时支付
        String lockKey = String.format(RedisKey.SECKILL_ORDER_TRANSACTION.getKey(), message.getOrderSn());
        try {
            if (redisLockUtils.lock(lockKey, RedisKey.SECKILL_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.SECKILL_ORDER_TRANSACTION.getTime()))) {
                OrderPlaceRes orderPlaceRes = orderService.orderCancel(message.getUid(), message.getOrderSn(), StarConstants.OrderType.PUBLISH_GOODS);
                if (StarConstants.ORDER_STATE.PAY_CANCEL.getCode().equals(orderPlaceRes.getOrderStatus())) {
                    Object times = redisUtil.hincr(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), String.valueOf(message.getUid()), 1L);
                    if (times != null) {
                        redisUtil.hdel(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), String.valueOf(message.getUid()));
                    }
                }
                if (orderPlaceRes == null || !Objects.equals(orderPlaceRes.getOrderStatus(), StarConstants.ORDER_STATE.PAY_CANCEL.getCode())) {
                    log.error("[{}] 数据修改异常 message = [{}]", this.getClass().getSimpleName(), message);
                }
            }
        } finally {
            redisLockUtils.unlock(lockKey);
        }

    }

}
