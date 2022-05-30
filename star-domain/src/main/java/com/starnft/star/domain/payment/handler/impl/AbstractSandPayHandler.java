package com.starnft.star.domain.payment.handler.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.PaymentHandlerBase;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import com.starnft.star.domain.payment.helper.TemplateHelper;
import com.starnft.star.domain.payment.model.req.PaymentOrder;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentOrderRes;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class AbstractSandPayHandler extends PaymentHandlerBase {

    protected PaymentRes getPaymentRes(PaymentRich paymentRich, Map<String, String> vendorConf, TempConf channelConf) throws Exception {

        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);

        //模板解析参数
        String signString = processTemplate(channelConf.getSignTempPath(), paymentRich, vendorConf);
        //参数根据第三方加密规则加密
        Map<String, String> req = getSignAndMap(sdKeysHelper, signString);

        if (log.isDebugEnabled()) {
            log.info("[{}] :加密后入参:{}", this.getClass().getSimpleName(), req.toString());
        }

        IInteract iInteract = obtainProcessInteraction(StarConstants.ProcessType.JSON);

        String context = iInteract.interact(ConnContext.builder()
                .formData(req).httpHeaders(new HttpHeaders())
                .restMethod(StarRequestMethod.POST_FORM)
                .url(channelConf.getHttpConf().getApiUrl()).build(), () -> null);
        //参数解密
        String result = URLDecoder.decode(Objects.requireNonNull(context), "utf-8");
        Map<String, String> data = TemplateHelper.getInstance().convertResultStringToMap(result);
        String sign = data.get("sign");
        String respData = data.get("data");
        //响应验签
        boolean valid = sdKeysHelper.verifyDigitalSign(respData.getBytes("utf-8"),
                Base64.decodeBase64(sign), sdKeysHelper.getPublicKey(), "SHA1WithRSA");
        if (!valid) throw new RuntimeException("签名校验出错");

        //模板解析相应参数
        JSONObject resObj = JSONUtil.parseObj(respData);
        String resModel = super.processTemplate(channelConf.getResTempPath(), paymentRich, resObj);
        RemoteRes remoteRes = JSON.parseObject(resModel, RemoteRes.class);

        //验证响应状态并返回
        return iInteract.verifyResAndGet(remoteRes, PaymentRes.class);
    }

    private  Map<String, String> getSignAndMap(SdKeysHelper sdKeysHelper, String signString) {
        String signResult = new String(Base64.encodeBase64(sdKeysHelper.digitalSign(signString.getBytes(StandardCharsets.UTF_8),
                sdKeysHelper.getPrivateKey(), "SHA1WithRSA")));
        Map<String, String> req = new HashMap<>();
        req.put("charset", "utf-8");
        req.put("data", signString);
        req.put("signType", "01");
        req.put("sign", signResult);
        return req;
    }


    @SneakyThrows
    protected PaymentOrderRes searchOrder(PaymentOrder order, Map<String, String> vendorConf){

        TempConf channelConf = getChannelConf(TradeType.SandPay_Order_Query);
          String signString = processTemplate(channelConf.getSignTempPath(), order, vendorConf);
        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
        Map<String, String> req = getSignAndMap(sdKeysHelper, signString);
        IInteract iInteract = obtainProcessInteraction(StarConstants.ProcessType.JSON);
        String context = iInteract.interact(ConnContext.builder()
                .formData(req).httpHeaders(new HttpHeaders())
                .restMethod(StarRequestMethod.POST_FORM)
                .url(channelConf.getHttpConf().getApiUrl()).build(), () -> null);
        String result = URLDecoder.decode(Objects.requireNonNull(context), "utf-8");
        Map<String, String> data = TemplateHelper.getInstance().convertResultStringToMap(result);
        String sign = data.get("sign");
        String respData = data.get("data");
        boolean valid = sdKeysHelper.verifyDigitalSign(respData.getBytes("utf-8"),
                Base64.decodeBase64(sign), sdKeysHelper.getPublicKey(), "SHA1WithRSA");
        if (!valid) throw new RuntimeException("签名校验出错");
        //模板解析相应参数
        JSONObject resObj = JSONUtil.parseObj(respData);
        String resModel = super.processTemplate(channelConf.getResTempPath(), null, resObj);
        RemoteRes remoteRes = JSON.parseObject(resModel, RemoteRes.class);
        return  null;
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
