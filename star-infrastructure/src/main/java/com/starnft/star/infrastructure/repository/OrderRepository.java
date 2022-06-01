package com.starnft.star.infrastructure.repository;

import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.repository.IOrderRepository;
import com.starnft.star.infrastructure.mapper.order.StarNftOrderMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private StarNftOrderMapper starNftOrderMapper;

    @Override
    public boolean createOrder(OrderVO orderVO) {

        return false;
    }
}
