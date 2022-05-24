package com.starnft.star.domain.payment.handler;

import com.starnft.star.domain.payment.config.PaymentConfiguration;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.process.ProcessInteractionHolder;

import java.util.Map;

public abstract class PaymentHandlerBase
        extends PaymentConfiguration implements IPaymentHandler, ProcessInteractionHolder {


    /**
     * @param paymentRich 是一个聚合对象 里面包含用户信息 订单信息等支付所需要的信息
     *                    在对接第三方支付时需要将用户唯一标识 作为扩展字段传入 在接收回调信息时候会获取到 原用户标识 以便于以后以用户标识作为分库分表路由的唯一标识
     * @return PaymentRes
     * @author Ryan Z / haoran
     * @description 支付
     * @date 2022/5/24
     */
    @Override
    public PaymentRes pay(PaymentRich paymentRich) {
        //验证参数边界 规则
        verifyLegality(paymentRich);
        //获取对应的厂商 渠道 配置信息
        Map<String, String> vendorConf = super.getVendorConf(getVendor(), getPayChannel());
        //执行支付流程
        return doPay(paymentRich, vendorConf);
    }

    protected abstract PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf);

    protected abstract void verifyLegality(PaymentRich req);


}
