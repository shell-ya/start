package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.MarketCancelOrderVo;
import com.starnft.star.domain.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public void onMessage(MarketOrderStatus marketOrderStatus) {
        //订单若仍是待支付状态 取消订单
        log.info("订单详情：{}", marketOrderStatus.toString());

        //查看订单仍是支付中状态
        OrderListRes orderListRes = orderService.orderDetails(new OrderListReq(0, 0, marketOrderStatus.getUserId(), marketOrderStatus.getStatus(), marketOrderStatus.getOrderSn()));
        if (!StarConstants.ORDER_STATE.WAIT_PAY.getCode().equals(orderListRes.getStatus())){
            log.info("用户：[{}]，订单:[{}]已完成",marketOrderStatus.getUserId(),marketOrderStatus.getOrderSn());
            return;
        }
        //仍是支付中 改为 取消状态 否则不处理
        orderService.cancelOrder(
                MarketCancelOrderVo.builder()
                        .uid(marketOrderStatus.getUserId())
                        .orderSn(marketOrderStatus.getOrderSn())
                        .build());
    }
}
