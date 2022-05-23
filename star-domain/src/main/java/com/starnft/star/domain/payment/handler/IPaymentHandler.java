package com.starnft.star.domain.payment.handler;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.model.PaymentRes;

public interface IPaymentHandler<T> {

    <R> PaymentRes pay(T payReq, Class<R> actualReqClazz, StarConstants.PayChannel payChannel);

}
