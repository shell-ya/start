package com.starnft.star.domain.notify.repository;

import com.starnft.star.domain.notify.model.req.NotifyOrderReq;

public interface INotifyOrderRepository {
   Integer insertNotifyOrder(NotifyOrderReq notifyOrderReq);
}
