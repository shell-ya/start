package com.starnft.star.domain.payment.core.imp;

import com.google.common.collect.Lists;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.handler.IPaymentHandler;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.payment.router.IPaymentRouter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    @Resource
    private IPaymentRouter paymentRouter;

    @Override
    public List<String> obtainSupported() {
        List<Enum<StarConstants.PayChannel>> enums = paymentRouter.obtainSupported();
        List<@Nullable String> channels = Lists.newArrayList();
        for (Enum<StarConstants.PayChannel> channel : enums) {
            channels.add(channel.name());
        }
        return channels;
    }

    @Override
    public PaymentRes pay(PaymentRich payReq) {
        IPaymentHandler iPaymentHandler = paymentRouter.payRoute(payReq.getPayChannel());
        return iPaymentHandler.pay(payReq);
    }

    @Override
    public PayCheckRes orderCheck(PayCheckReq payReq) {
        IPaymentHandler iPaymentHandler = paymentRouter.payRoute(payReq.getPayChannel());
        return iPaymentHandler.orderCheck(payReq);
    }
}
