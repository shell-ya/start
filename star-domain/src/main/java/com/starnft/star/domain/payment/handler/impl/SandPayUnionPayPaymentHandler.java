package com.starnft.star.domain.payment.handler.impl;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SandPayUnionPayPaymentHandler extends AbstractSandPayHandler {

    @Override
    public StarConstants.PayChannel getPayChannel() {
        return StarConstants.PayChannel.UNION_PAY;
    }

    @Override
    public StarConstants.Pay_Vendor getVendor() {
        return StarConstants.Pay_Vendor.SandPay;
    }


    /**
     * @param paymentRich
     * @param vendorConf
     * @return PaymentRes
     * @author Ryan Z / haoran
     * @description 杉德支付接口
     * @date 2022/5/24
     * 调用第三方接口 使用
     * @see com.starnft.star.domain.support.process.ProcessInteractionHolder obtainProcessInteraction 获取抽象类以继承该接口获取IInteract
     * @see com.starnft.star.domain.support.process.IInteract
     * 若第三方接口使用的自己独特加密后的工具，则封装该工具并继承 【InteractBase】并实现 interact 统一调用入口
     * @see com.starnft.star.domain.support.process.InteractBase
     */
    @SneakyThrows
    @Override
    protected PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf) {
        TempConf channelConf = getChannelConf(TradeType.Union_SandPay);
        return getPaymentRes(paymentRich, vendorConf, channelConf);
    }

    @Override
    protected void verifyLegality(PaymentRich req) {

    }
}
