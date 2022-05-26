package com.starnft.star.domain.payment.handler.impl;

import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.PaymentHandlerBase;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import com.starnft.star.domain.payment.helper.TemplateHelper;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.process.IInteract;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import com.starnft.star.domain.support.process.helper.RestTemplateHelper;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SandPayPaymentHandler extends PaymentHandlerBase {
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
        TempConf channelConf = getChannelConf(TradeType.SandPay);
        String signString = processTemplate(channelConf.getSignTempPath(), paymentRich, vendorConf).replaceAll("(\\r\\n|\\n|\\\\n|\\s)", "");;
        String signResult = new String(Base64.encodeBase64(sdKeysHelper.digitalSign(signString.getBytes(StandardCharsets.UTF_8), sdKeysHelper.getPrivateKey(), "SHA1WithRSA")));
//        String requestStr = processTemplate(channelConf.getReqTempPath(), signString, signResult);
        Map<String, String> req = new HashMap<>();
        req.put("charset", "utf-8");
        req.put("data", signString);
        req.put("signType", "01");
        req.put("sign", signResult);
        System.out.println(req);

        IInteract iInteract = obtainProcessInteraction(StarConstants.ProcessType.JSON);
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Type","application/x-www-form-urlencoded");
//        String h5url = HttpUtil.post(vendorConf.get("h5url"), req);
        ResponseEntity<String> h5url = RestTemplateHelper.executePostFromParam(httpHeaders, vendorConf.get("h5url"), req);
//        String res = iInteract.interact(ConnContext.builder().httpHeaders(httpHeaders)
//                .url(vendorConf.get("h5url")).restMethod(RequestMethod.POST).build(), () -> req);
        String result = URLDecoder.decode(h5url.getBody(), "utf-8");
        Map<String, String> stringStringMap = TemplateHelper.getInstance().convertResultStringToMap(result);
        PaymentRes paymentRes = new PaymentRes();

        return null;
    }

    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        HashMap<@Nullable String, @Nullable Object> dataModel = Maps.newHashMap();
        dataModel.put("payment", data[0]);
        dataModel.put("conf", data[1]);
        dataModel.put("currentTime", getCurrentTime());
        dataModel.put("outLineTime", outLineTime());
        dataModel.put("helper", TemplateHelper.getInstance());
        return dataModel;
    }

    @Override
    protected void verifyLegality(PaymentRich req) {

    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private String outLineTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        return new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
    }
}
