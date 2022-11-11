package com.starnft.star.application.process.order;

import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.domain.payment.model.res.PayCheckRes;

public interface IOrderProcessor {

    //秒杀下单
    OrderGrabRes orderGrab(OrderGrabReq orderGrabReq);

    //秒杀下单
    OrderGrabRes createOrder(OrderGrabReq orderGrabReq);

    //查询秒杀订单
    OrderListRes obtainSecKillOrder(OrderGrabReq orderGrabReq);

    //查询用户下单订单状态
    OrderGrabStatus obtainSecKIllStatus(OrderGrabReq orderGrabReq);

    //订单支付
    OrderPayDetailRes orderPay(OrderPayReq orderPayReq);

    //秒杀订单取消
    OrderPlaceRes cancelSecOrder(OrderCancelReq orderGrabReq);

    //市场下单

    /**
     * 市场交易锁改成藏品id+下单用户id.并且锁定时间大于订单超时自动取消时间  之前只能确实藏品被锁住 无法判断被谁锁住
     * 藏品拥有者字段统一使用 theme_number表中字段  user_theme可能会有重复记录
     * 支付时再次确认用户是否持有当前藏品交易锁，支付时藏品拥有者与下单时拥有者是否一致
     *
     * @param marketOrderReq
     * @return
     */
    OrderListRes marketOrder(MarketOrderReq marketOrderReq);

    OrderListRes orderDetails(OrderListReq req);


    Boolean dataCheck(Long themeId, String keySecret);

    //查询用户当前是否有未支付订单
    Boolean havingOrder(Long userId);

    Integer priorityTimes(Long userId);

    OrderListRes payDetails(String orderSn);

    Boolean marketC2COrder(PayCheckRes payCheckRes);
}
