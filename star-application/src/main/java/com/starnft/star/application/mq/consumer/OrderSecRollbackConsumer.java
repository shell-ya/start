package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@RocketMQMessageListener(topic = "${consumer.topic.secrollback}", consumerGroup = "${consumer.group.secrollback}", selectorExpression = "rollback")
public class OrderSecRollbackConsumer implements RocketMQListener<OrderGrabStatus> {

    private static final Logger log = LoggerFactory.getLogger(OrderSecRollbackConsumer.class);
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
                if (orderPlaceRes == null || !Objects.equals(orderPlaceRes.getOrderStatus(), StarConstants.ORDER_STATE.PAY_CANCEL.getCode())) {
                    log.error("[{}] 自动取消失败，可能订单已被手动取消或完成 message = [{}]", this.getClass().getSimpleName(), message);
                }
            }
        } finally {
            redisLockUtils.unlock(lockKey);
        }

    }

}
