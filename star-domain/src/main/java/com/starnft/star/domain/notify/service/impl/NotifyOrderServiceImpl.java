package com.starnft.star.domain.notify.service.impl;

import com.starnft.star.domain.notify.model.req.NotifyOrderReq;
import com.starnft.star.domain.notify.repository.INotifyOrderRepository;
import com.starnft.star.domain.notify.service.NotifyOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class NotifyOrderServiceImpl implements NotifyOrderService {
    @Resource
    INotifyOrderRepository notifyOrderRepository;

    @Override
    @Transactional
    public void saveOrder(NotifyOrderReq notifyOrderReq) {
        notifyOrderRepository.insertNotifyOrder(notifyOrderReq);
    }

    @Override
    public Integer sendStatus(String orderSn, Long statusCode) {
        return notifyOrderRepository.sendStatus(orderSn, statusCode);
    }
}
