package com.starnft.star.domain.payment.handler.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.PaymentHandlerBase;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import com.starnft.star.domain.payment.helper.TemplateHelper;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.process.IInteract;
import com.starnft.star.domain.support.process.assign.StarRequestMethod;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import com.starnft.star.domain.support.process.context.ConnContext;
import com.starnft.star.domain.support.process.res.RemoteRes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
