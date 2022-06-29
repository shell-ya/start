package com.starnft.star.domain.payment.handler.sandpay;

import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.payment.helper.TemplateHelper;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.req.RefundReq;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.payment.model.res.RefundRes;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SandPayCloudPayPaymentHandler extends AbstractSandPayHandler {
    @Override
    public StarConstants.PayChannel getPayChannel() {
        return StarConstants.PayChannel.CloudAccount;
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
        Assert.notNull(payExtend, () -> new StarException("在PayExtend中加入userName为用户的昵称"));
//        payExtend.put("accountType","1");
        Assert.notNull(payExtend.get("userName"), () -> new StarException("在PayExtend中加入userName为用户的昵称"));
        Assert.notNull(req.getUserId(), () -> new StarException("用户ID不能为空"));
    }

    @Override
    protected PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf) {
        TempConf channelConf = getChannelConf(TradeType.Cloud_Account_SandPay);
        PaymentRes paymentRes = super.getRes(paymentRich, vendorConf, channelConf);
        return paymentRes;
    }



    @Override
    protected PayCheckRes doOrderCheck(PayCheckReq payCheckReq, Map<String, String> vendorConf) {
        return super.doOrderCheck(payCheckReq,vendorConf);
    }

    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        HashMap<@Nullable String, @Nullable Object> dataModel = Maps.newHashMap();
        dataModel.put("param1", data[0]);
        dataModel.put("param2", data[1]);
        dataModel.put("helper", TemplateHelper.getInstance());
        dataModel.put("createTime", data[2]);
        dataModel.put("endTime", data[3]);
        return dataModel;
    }
}
