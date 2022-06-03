package com.starnft.star.domain.order.repository;

import com.starnft.star.domain.order.model.vo.OrderVO;

public interface IOrderRepository {

    //下单
    boolean createOrder(OrderVO orderVO);

    //更新订单状态 若支付单号不为空则一并修改
    boolean updateOrder(Long uid, String orderSn, Integer modifiedStatus, String payNumber);

    //查询订单
    OrderVO queryOrderByCondition(Long uid, String orderSn);

}
