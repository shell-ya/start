package com.starnft.star.domain.order.repository;

import com.starnft.star.domain.order.model.vo.OrderVO;

public interface IOrderRepository {

    boolean createOrder(OrderVO orderVO);

}
