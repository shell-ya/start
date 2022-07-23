package com.starnft.star.domain.payment.handler.sandpay;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.PaymentHandlerBase;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import com.starnft.star.domain.payment.helper.TemplateHelper;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.req.RefundReq;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.payment.model.res.RefundRes;
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
import java.util.Date;
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
        log.info("返回的数据为：{}",JSONUtil.toJsonStr(resObj));
        String resModel = super.processTemplate(channelConf.getResTempPath(), paymentRich, resObj);
        RemoteRes remoteRes = JSON.parseObject(resModel, RemoteRes.class);

        //验证响应状态并返回
        return iInteract.verifyResAndGet(remoteRes, PaymentRes.class);
    }

    protected Map<String, String> getSignAndMap(SdKeysHelper sdKeysHelper, String signString) {
        String signResult = new String(Base64.encodeBase64(sdKeysHelper.digitalSign(signString.getBytes(StandardCharsets.UTF_8),
                sdKeysHelper.getPrivateKey(), "SHA1WithRSA")));
        Map<String, String> req = new HashMap<>();
        req.put("charset", "utf-8");
        req.put("data", signString);
        req.put("signType", "01");
        req.put("sign", signResult);
        return req;
    }
    protected PaymentRes getRes(PaymentRich paymentRich, Map<String, String> vendorConf, TempConf channelConf) {
        String signTempPath = channelConf.getSignTempPath();
        TemplateHelper instance = TemplateHelper.getInstance();
        String startTime = instance.getStartTime();
        String endTime = instance.getEndTime();
        String sign = super.processTemplate(signTempPath, paymentRich, vendorConf,startTime,endTime).toUpperCase();
        vendorConf.put("sign",sign);
            if (Objects.isNull(paymentRich.getPlatform())){
                paymentRich.setPlatform(StarConstants.PlatformEnums.Web);
            }
        if (!paymentRich.getPlatform().equals(StarConstants.PlatformEnums.Web)){
            String resTempPath = channelConf.getResTempPath();
            String result = super.processTemplate(resTempPath, paymentRich, vendorConf, startTime, endTime);
            PaymentRes paymentRes = new PaymentRes();
            paymentRes.setThirdPage(result);
            paymentRes.setOrderSn(paymentRich.getOrderSn());
            paymentRes.setStatus(0);
            paymentRes.setTotalMoney(paymentRich.getTotalMoney().toString());
            paymentRes.setMessage("成功");
            return paymentRes;
        }
        else
        {
            String reqTempPath = channelConf.getReqTempPath();
            String resultUri = super.processTemplate(reqTempPath, paymentRich, vendorConf, startTime, endTime);
            PaymentRes paymentRes = new PaymentRes();
            paymentRes.setGatewayApi(resultUri);
            paymentRes.setOrderSn(paymentRich.getOrderSn());
            paymentRes.setStatus(0);
            paymentRes.setTotalMoney(paymentRich.getTotalMoney().toString());
            paymentRes.setMessage("成功");
            return paymentRes;
        }
    }

    @SneakyThrows
    protected PayCheckRes doOrderCheck(PayCheckReq payCheckReq, Map<String, String> vendorConf) {

        TempConf channelConf = getChannelConf(TradeType.SandPay_Order_Query);
        String signString = processTemplate(channelConf.getSignTempPath(), payCheckReq.getOrderSn(), vendorConf,new Date(),new Date());
        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
        Map<String, String> req = getSignAndMap(sdKeysHelper, signString);
        log.info("查单请求报文：{}",JSONUtil.toJsonStr(req));
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
        log.info("查单返回解密报文：{}",respData);
        JSONObject resObj = JSONUtil.parseObj(respData);

        String resModel = super.processTemplate(channelConf.getResTempPath(), resObj, vendorConf,new Date(),new Date());
        RemoteRes remoteRes = JSON.parseObject(resModel, RemoteRes.class);

        return iInteract.verifyResAndGet(remoteRes, PayCheckRes.class);
    }

    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        HashMap<@Nullable String, @Nullable Object> dataModel = Maps.newHashMap();
        dataModel.put("param1", data[0]);
        dataModel.put("param2", data[1]);
        dataModel.put("helper", TemplateHelper.getInstance());
        return dataModel;
    }
  @SuppressWarnings("DuplicatedCode")
  @SneakyThrows
  protected RefundRes doRefund(RefundReq refundReq, Map<String, String> vendorConf){
      TempConf channelConf = getChannelConf(TradeType.Sand_Refund);
      String signString = processTemplate(channelConf.getSignTempPath(), refundReq, vendorConf);
      SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
      Map<String, String> req = getSignAndMap(sdKeysHelper, signString);
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
      JSONObject resObj = JSONUtil.parseObj(respData);
      log.info("回来的参数为：{}",JSONUtil.toJsonStr(resObj));
      String resModel = super.processTemplate(channelConf.getResTempPath(), resObj, vendorConf);
      RemoteRes remoteRes = JSON.parseObject(resModel, RemoteRes.class);
      RefundRes refundRes = iInteract.verifyResAndGet(remoteRes, RefundRes.class);
//      if (refundRes)
      return refundRes;
  }
}
