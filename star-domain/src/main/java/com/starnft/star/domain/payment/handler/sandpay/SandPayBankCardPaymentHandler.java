package com.starnft.star.domain.payment.handler.sandpay;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SandPayBankCardPaymentHandler extends AbstractSandPayHandler {

    @Override
    public StarConstants.PayChannel getPayChannel() {
        return StarConstants.PayChannel.BankCard;
    }

    @Override
    public StarConstants.Pay_Vendor getVendor() {
        return StarConstants.Pay_Vendor.SandPay;
    }



    @Override
    protected void verifyLegality(PaymentRich req) {
        Assert.notNull(req.getFrontUrl(), () -> new StarException("回显地址不能为空"));
        Assert.notNull(req.getBankNo(), () -> new StarException("银行卡号不能为空"));
    }


    @Override
    @SneakyThrows
    protected PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf) {
        TempConf channelConf = getChannelConf(TradeType.Bank_SandPay);

        return super.getPaymentRes(paymentRich, vendorConf, channelConf);
    }

}
