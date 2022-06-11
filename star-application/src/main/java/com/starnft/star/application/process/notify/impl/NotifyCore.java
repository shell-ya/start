package com.starnft.star.application.process.notify.impl;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.domain.notify.model.req.NotifyOrderReq;
import com.starnft.star.domain.notify.service.NotifyOrderService;
import com.starnft.star.domain.notify.service.PayNotifyService;
import com.starnft.star.domain.payment.model.res.NotifyRes;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class NotifyCore {
    @Resource
    PayNotifyService payNotifyService;
    @Resource
    IMessageSender messageSender;
    @Resource
    NotifyOrderService notifyOrderService;

    public NotifyRes handler(String platform, HttpServletRequest request, HttpServletResponse response) {
        NotifyRes transform = payNotifyService.transform(platform, request, response);
        PayCheckRes payCheckRes = transform.getPayCheckRes();
        NotifyOrderReq req = NotifyOrderReq.builder()
                .orderSn(payCheckRes.getOrderSn())
                .payChannel(payCheckRes.getPayChannel())
                .createTime(new Date())
                .message(payCheckRes.getMessage())
                .payTime(new Date())
                .status(payCheckRes.getStatus())
                .totalAmount(payCheckRes.getTotalAmount())
                .transSn(payCheckRes.getTransSn())
                .uid(Long.parseLong(payCheckRes.getUid()))
                .build();
        notifyOrderService.saveOrder(req);
        messageSender.asyncSend(transform.getTopic(), Optional.of(transform.getPayCheckRes()), null, null);
        return transform;
    }
}
