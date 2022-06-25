package com.starnft.star.domain.payment.handler.sandpay;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.req.RefundReq;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.payment.model.res.RefundRes;

import java.util.Map;

public class SandPayCheckBankCardPaymentHandler extends AbstractSandPayHandler {
    @Override
    public StarConstants.PayChannel getPayChannel() {
        return StarConstants.PayChannel.CheckPay;
    }

    @Override
    public StarConstants.Pay_Vendor getVendor() {
        return StarConstants.Pay_Vendor.SandPay;
    }

    @Override
    protected RefundRes doRefund(RefundReq refundReq, Map<String, String> vendorConf) {
        return super.doRefund(refundReq,vendorConf);
    }

    @Override
    protected void verifyLegality(PaymentRich req) {
        Map<String, Object> payExtend = req.getPayExtend();
        Assert.notNull(payExtend, () -> new StarException("在PayExtend中加入userName为用户的姓名，加入idCard为用户的身份证号"));
        Assert.notNull(payExtend.get("userName"), () -> new StarException("在PayExtend中加入userName为用户的姓名"));
        Assert.notNull(payExtend.get("idCard"), () -> new StarException("在PayExtend中加入idCard为用户的身份证号"));
        Assert.notNull(req.getUserId(), () -> new StarException("用户ID不能为空"));
    }

    @Override
    protected PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf) {
        return null;
    }

    @Override
    protected PayCheckRes doOrderCheck(PayCheckReq payCheckReq, Map<String, String> vendorConf) {
        return super.doOrderCheck(payCheckReq,vendorConf);
    }

    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        return null;
    }
}
