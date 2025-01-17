package com.starnft.star.application.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Date 2022/6/17 5:49 PM
 * @Author ： shellya
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "${consumer.topic.market}", consumerGroup = "${consumer.group.market}",
        selectorExpression = "rollback", messageModel = MessageModel.CLUSTERING)
public class MarketOrderRollBackConsumer implements RocketMQListener<MarketOrderStatus> {


    @Resource
    private IOrderService orderService;


    @Resource
    private RedisLockUtils redisLockUtils;

    @Override
    public void onMessage(MarketOrderStatus marketOrderStatus) {
        //订单若仍是待支付状态 取消订单
        log.info("订单详情：{}", marketOrderStatus.toString());
        String lockKey = String.format(RedisKey.SECKILL_ORDER_TRANSACTION.getKey(), marketOrderStatus.getOrderSn());
        Boolean lock = redisLockUtils.lock(lockKey, RedisKey.SECKILL_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.SECKILL_ORDER_TRANSACTION.getTime()));
        Assert.isTrue(lock, () -> new RuntimeException("用户 [" + marketOrderStatus.getUserId() + "] orderSn: [ " + marketOrderStatus.getOrderSn() + "] 正在交易！"));
        try {
            OrderPlaceRes orderPlaceRes = orderService.orderCancel(marketOrderStatus.getUserId(), marketOrderStatus.getOrderSn(), StarConstants.OrderType.MARKET_GOODS);
            if (orderPlaceRes == null || !Objects.equals(orderPlaceRes.getOrderStatus(), StarConstants.ORDER_STATE.PAY_CANCEL.getCode())) {
                log.error("[{}] 数据修改异常 message = [{}]", this.getClass().getSimpleName(), marketOrderStatus);
            }
        } finally {
            redisLockUtils.unlock(lockKey);
        }
        //查看订单仍是支付中状态
//        OrderListRes orderListRes = orderService.orderDetails(new OrderListReq(0, 0, marketOrderStatus.getUserId(), marketOrderStatus.getStatus(), marketOrderStatus.getOrderSn()));
//        if (!StarConstants.ORDER_STATE.WAIT_PAY.getCode().equals(orderListRes.getStatus())){
//            log.info("用户：[{}]，订单:[{}]已完成",marketOrderStatus.getUserId(),marketOrderStatus.getOrderSn());
//            return;
//        }
//        //仍是支付中 改为 取消状态 否则不处理
//        orderService.cancelOrder(
//                MarketCancelOrderVo.builder()
//                        .uid(marketOrderStatus.getUserId())
//                        .orderSn(marketOrderStatus.getOrderSn())
//                        .build());
    }
}
