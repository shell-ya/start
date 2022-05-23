package com.starnft.star.domain.payment.router;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.IPaymentHandler;
import com.starnft.star.domain.payment.router.model.PaymentRouterContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentRouter implements IPaymentRouter{


    @Override
    public List<Enum<StarConstants.PayChannel>> obtainSupported() {

        return null;
    }

    @Override
    public IPaymentHandler<?> payRoute(PaymentRouterContext context) {

        return null;
    }

    @Override
    public Class<?> obtainChannelRequest(StarConstants.PayChannel payChannel) {

        return null;
    }
}
