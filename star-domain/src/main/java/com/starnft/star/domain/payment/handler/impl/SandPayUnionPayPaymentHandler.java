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
public class SandPayUnionPayPaymentHandler extends PaymentHandlerBase {

    @Resource
    SdKeysHelper sdKeysHelper;

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

    protected PaymentRes getPaymentRes(PaymentRich paymentRich, Map<String, String> vendorConf, TempConf channelConf) throws Exception {

        //模板解析参数
        String signString = processTemplate(channelConf.getSignTempPath(), paymentRich, vendorConf);
        //参数根据第三方加密规则加密
        String signResult = new String(Base64.encodeBase64(sdKeysHelper.digitalSign(signString.getBytes(StandardCharsets.UTF_8),
                sdKeysHelper.getPrivateKey(), "SHA1WithRSA")));

        Map<String, String> req = new HashMap<>();
        req.put("charset", "utf-8");
        req.put("data", signString);
        req.put("signType", "01");
        req.put("sign", signResult);

        if (log.isDebugEnabled()) {
            log.info("[{}] :加密后入参:{}", this.getClass().getSimpleName(), req.toString());
        }

        IInteract iInteract = obtainProcessInteraction(StarConstants.ProcessType.JSON);

        String context = iInteract.interact(ConnContext.builder()
                .formData(req).httpHeaders(new HttpHeaders())
                .restMethod(StarRequestMethod.POST_FORM).url(channelConf.getHttpConf().getApiUrl()).build(), () -> null);
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
        return verifyResAndGet(remoteRes, PaymentRes.class);
    }

    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        HashMap<@Nullable String, @Nullable Object> dataModel = Maps.newHashMap();
        dataModel.put("param1", data[0]);
        dataModel.put("param2", data[1]);
        dataModel.put("helper", TemplateHelper.getInstance());
        return dataModel;
    }

    @Override
    protected void verifyLegality(PaymentRich req) {

    }
}
