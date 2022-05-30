package com.starnft.star.domain.payment.handler;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;

public interface IPaymentHandler {

    PaymentRes pay(PaymentRich payReq);
    PaymentRes queryOrderCode(String  orderCode);

    StarConstants.PayChannel getPayChannel();

    StarConstants.Pay_Vendor getVendor();

}
