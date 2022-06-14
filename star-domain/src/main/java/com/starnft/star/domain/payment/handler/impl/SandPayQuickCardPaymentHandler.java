package com.starnft.star.domain.payment.handler.impl;

import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import com.starnft.star.domain.payment.helper.TemplateHelper;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class SandPayQuickCardPaymentHandler  extends AbstractSandPayHandler {
    @Override
    public StarConstants.PayChannel getPayChannel() {
        return StarConstants.PayChannel.QuickCard;
    }

    @Override
    public StarConstants.Pay_Vendor getVendor() {
        return StarConstants.Pay_Vendor.SandPay;
    }

    @Override
    protected void verifyLegality(PaymentRich req) {
//       Assert.isNull(req.getFrontUrl(),()->new StarException("回显地址不能为空"));
//       Assert.isNull(req.getUserId(),()->new StarException("用户id不能为空"));
//       Assert.isNull(req.getTotalMoney(),()->new StarException("用户id不能为空"));
    }
    @SneakyThrows
    @Override
    protected PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf) {
        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
        TempConf channelConf = getChannelConf(TradeType.Quick_Card_SandPay);
        //模板解析参数
        String signString = processTemplate(channelConf.getSignTempPath(), paymentRich, vendorConf);
//        //参数根据第三方加密规则加密
//        String signResult = new String(Base64.encodeBase64(sdKeysHelper.digitalSign(signString.getBytes(StandardCharsets.UTF_8),
//                sdKeysHelper.getPrivateKey(), "SHA1WithRSA")));
        Map<String, String> req = getSignAndMap(sdKeysHelper, signString);
        PaymentRes paymentRes = new PaymentRes();
        paymentRes.setTotalMoney(paymentRich.getTotalMoney().toString());
        paymentRes.setOrderSn(paymentRich.getOrderSn());
        paymentRes.setStatus(0);
        paymentRes.setMessage("成功");
        paymentRes.setThirdParam(req);
        paymentRes.setGatewayApi(channelConf.getHttpConf().getApiUrl());
        return  paymentRes;
    }

    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        HashMap<@Nullable String, @Nullable Object> dataModel = Maps.newHashMap();
        dataModel.put("param1", data[0]);
        dataModel.put("param2", data[1]);
        dataModel.put("helper", TemplateHelper.getInstance());
        return dataModel;
    }
}
