package com.starnft.star.domain.notify.service.impl;

import com.starnft.star.domain.notify.router.INotifyRouter;
import com.starnft.star.domain.notify.service.PayNotifyService;
import com.starnft.star.domain.payment.model.res.NotifyRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class PayNotifyServiceImpl implements PayNotifyService {

    @Resource
    private INotifyRouter iNotifyRouter;

    @Override
    public NotifyRes transform(String platform, HttpServletRequest request, HttpServletResponse response) {
        NotifyRes s = iNotifyRouter.doDistribute(platform, request, response);
        return s;
    }
}
