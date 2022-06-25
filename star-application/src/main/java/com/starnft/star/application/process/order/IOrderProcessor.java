package com.starnft.star.application.process.order;

import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.number.res.MarketOrderRes;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;

public interface IOrderProcessor {

    //秒杀下单
    OrderGrabRes orderGrab(OrderGrabReq orderGrabReq);

    //查询秒杀订单
    OrderListRes obtainSecKillOrder(OrderGrabReq orderGrabReq);

    //查询用户下单订单状态
    OrderGrabStatus obtainSecKIllStatus(OrderGrabReq orderGrabReq);

    //订单支付
    OrderPayDetailRes orderPay(OrderPayReq orderPayReq);

    //秒杀订单取消
    OrderPlaceRes cancelSecOrder(OrderCancelReq orderGrabReq);

    //市场下单
    OrderListRes marketOrder(MarketOrderReq marketOrderReq);
}
