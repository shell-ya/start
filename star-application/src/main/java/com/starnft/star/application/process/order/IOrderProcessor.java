package com.starnft.star.application.process.order;

import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;

public interface IOrderProcessor {

    OrderGrabRes orderGrab(OrderGrabReq orderGrabReq);
}
