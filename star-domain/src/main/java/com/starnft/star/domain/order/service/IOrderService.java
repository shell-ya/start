package com.starnft.star.domain.order.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;

public interface IOrderService {

    //下单

    //查询用户订单列表
    ResponsePageResult<OrderListRes> orderList(OrderListReq orderListReq);

    //查询用户订单详情

    //订单支付状态回写

    //取消订单



}
