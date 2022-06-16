package com.starnft.star.domain.order.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.OrderVO;

public interface IOrderService {

    //下单
    boolean createOrder(OrderVO orderVO);

    //查询用户订单列表
    ResponsePageResult<OrderListRes> orderList(OrderListReq orderListReq);

    //查询用户订单详情
    OrderListRes orderDetails(OrderListReq orderListReq);

    //订单支付状态回写

    //取消订单


}
