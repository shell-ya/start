package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.mq.producer.activity.ActivityEventProducer;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.model.BuyActivityEventReq;
import com.starnft.star.application.process.event.model.EventReqAssembly;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.stateflow.impl.OrderStateHandler;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.innerpay}", consumerGroup = "${consumer.group.innerpay}", selectorExpression = "router")
public class CloudPayCallBackConsumer implements RocketMQListener<PayCheckRes> {

    private final TransactionTemplate template;
    private final INumberService numberService;
    private final OrderStateHandler orderStateHandler;
    private final RedisUtil redisUtil;
    private final ActivityEventProducer activityProducer;
    private final IOrderService orderService;
    private final IUserService userService;

    @Override
    public void onMessage(PayCheckRes payCheckRes) {

        OrderVO orderVO = null;
        if (payCheckRes.getStatus().equals(ResultCode.SUCCESS.getCode())) {
            orderVO = orderService.queryOrderById(Long.parseLong(payCheckRes.getOrderSn()));
            if (orderVO == null) {
                log.error("uid :[{}] orderId : [{}] 不存在！", payCheckRes.getUid(), payCheckRes.getOrderSn());
                throw new RuntimeException("订单不存在！");
            }
        }

        try {
            OrderVO finalOrderVO = orderVO;
            Boolean isSuccess = template.execute(status -> {
                //商品发放
                boolean handover = numberService.handover(buildHandOverReq(finalOrderVO));
                //订单状态更新
                Result result = orderStateHandler.payComplete(finalOrderVO.getUserId(), finalOrderVO.getOrderSn(), finalOrderVO.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);
                return ResultCode.SUCCESS.getCode().equals(result.getCode()) && handover;
            });
            if (isSuccess) {
                redisUtil.hincr(String.format(RedisKey.SECKILL_BUY_GOODS_NUMBER.getKey(), finalOrderVO.getSeriesThemeInfoId()), String.valueOf(finalOrderVO.getUserId()), 1L);

                //只有首发订单增加积分
                if (!finalOrderVO.getOrderSn().startsWith(StarConstants.OrderPrefix.TransactionSn.getPrefix())) {
                    OrderListRes orderListRes = orderService.obtainSecKillOrder(finalOrderVO.getUserId(), finalOrderVO.getSeriesThemeInfoId());
                    if (orderListRes.getPriorityBuy() == 1) subPTimes(finalOrderVO.getUserId(), orderListRes);
                    activityProducer.sendScopeMessage(createEventReq(finalOrderVO));
                }
            }
        } catch (TransactionException | StarException e) {
            throw new RuntimeException("订单处理异常！", e);
        }
    }

    private void subPTimes(Long userId, OrderListRes orderListRes) {
        Boolean isSuccess = userService.whiteTimeConsume(userId, orderListRes.getWhiteId());
        if (!isSuccess) userService.whiteTimeConsume(userId, 1L);
    }

    private HandoverReq buildHandOverReq(OrderVO orderPayReq) {
        HandoverReq handoverReq = new HandoverReq();
        handoverReq.setUid(orderPayReq.getUserId());
        NumberDetailVO numberDetail = numberService.getNumberDetail(orderPayReq.getNumberId());
        handoverReq.setFromUid(Long.parseLong(numberDetail.getOwnerBy()));
        handoverReq.setToUid(orderPayReq.getUserId());
        handoverReq.setPreMoney(orderPayReq.getPayAmount());
        handoverReq.setCurrMoney(orderPayReq.getPayAmount());
        handoverReq.setItemStatus(NumberStatusEnum.SOLD.getCode());
        handoverReq.setThemeId(orderPayReq.getSeriesThemeInfoId());
        handoverReq.setNumberId(orderPayReq.getNumberId());
        handoverReq.setCategoryType(orderPayReq.getThemeType());
        handoverReq.setSeriesId(orderPayReq.getSeriesId());
        handoverReq.setType(NumberCirculationTypeEnum.PURCHASE.getCode());
        handoverReq.setOrderType(orderPayReq.getOrderSn().startsWith(StarConstants.OrderPrefix.PublishGoods.getPrefix(), 0) ?
                StarConstants.OrderType.PUBLISH_GOODS : StarConstants.OrderType.MARKET_GOODS);
        return handoverReq;
    }

    private ActivityEventReq createEventReq(OrderVO orderPayReq) {
        BuyActivityEventReq buyActivityEventReq = new BuyActivityEventReq();
        buyActivityEventReq.setEventSign(StarConstants.EventSign.Buy.getSign());
        buyActivityEventReq.setUserId(orderPayReq.getUserId());
        buyActivityEventReq.setReqTime(new Date());
        buyActivityEventReq.setSeriesId(orderPayReq.getSeriesId());
        buyActivityEventReq.setThemeId(orderPayReq.getSeriesThemeInfoId());
        buyActivityEventReq.setMoney(orderPayReq.getPayAmount());
        return EventReqAssembly.assembly(buyActivityEventReq);
    }


}
