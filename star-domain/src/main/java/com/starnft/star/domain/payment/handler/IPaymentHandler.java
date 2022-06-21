package com.starnft.star.domain.payment.handler;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.req.RefundReq;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.payment.model.res.RefundRes;

public interface IPaymentHandler {

    //支付
    PaymentRes pay(PaymentRich payReq);
    //查单
    PayCheckRes orderCheck(PayCheckReq payCheckReq);
    //支付渠道
    StarConstants.PayChannel getPayChannel();
    //厂商信息
    StarConstants.Pay_Vendor getVendor();



     RefundRes refund(RefundReq refundReq);

}
