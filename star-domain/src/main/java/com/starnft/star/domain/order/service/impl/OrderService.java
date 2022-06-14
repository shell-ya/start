package com.starnft.star.domain.order.service.impl;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.repository.IOrderRepository;
import com.starnft.star.domain.order.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService implements IOrderService {

    @Resource
    IOrderRepository orderRepository;

    @Override
    public ResponsePageResult<OrderListRes> orderList(OrderListReq orderListReq) {
        ResponsePageResult<OrderVO> queryOrders = this.orderRepository.queryOrders(
                orderListReq.getUserId(), orderListReq.getStatus(), orderListReq.getPage(), orderListReq.getSize());
        return ResponsePageResult.listReplace(queryOrders, BeanColverUtil.colverList(queryOrders.getList(), OrderListRes.class));
    }
}
