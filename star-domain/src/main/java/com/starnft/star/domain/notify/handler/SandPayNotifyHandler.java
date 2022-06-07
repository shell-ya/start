package com.starnft.star.domain.notify.handler;

import com.alibaba.fastjson.JSONObject;
import com.starnft.star.common.enums.PlatformTypeEnum;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import com.starnft.star.domain.payment.model.res.NotifyRes;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Component
@Slf4j
public class SandPayNotifyHandler implements INotifyHandler {
    @Resource
    SdKeysHelper sdKeysHelper;

    @Override
    public NotifyRes doNotify(HttpServletRequest request) {
        String data = request.getParameter("data");
        String sign = request.getParameter("sign");
        // 验证签名
        boolean valid;
        try {
            valid = sdKeysHelper.verifyDigitalSign(data.getBytes("utf-8"), Base64.decodeBase64(sign),
                    sdKeysHelper.getPublicKey(), "SHA1WithRSA");
            if (!valid) {
                log.error("verify sign fail.");
                log.error("签名字符串(data)为：" + data);
                log.error("签名值(sign)为：" + sign);
            } else {
                JSONObject dataJson = JSONObject.parseObject(data);
                if (dataJson != null) {
                    if (dataJson.getJSONObject("head").getString("respCode").equals("000000")) {
                        JSONObject extend = dataJson.getJSONObject("body").getJSONObject("extend");
                        return   NotifyRes.builder().payCheckRes(PayCheckRes
                                .builder()
                                .orderSn(dataJson.getJSONObject("body").getString("tradeNo"))
                                .transSn(dataJson.getJSONObject("body").getString("payOrderCode"))
                                .uid(extend.getString("userId"))
                                .payChannel(extend.getString("payChannel"))
                                .status(0)
                                .message(dataJson.getJSONObject("head").getString("respMsg"))
                                .totalAmount(new BigDecimal(dataJson.getJSONObject("body").getString("settleAmount")).divide(new BigDecimal("100")))
                                .build())
                                .topic(extend.getString("multicastTopic"))
                                .build();
                    }
                    log.info("通知业务数据为：" + JSONObject.toJSONString(dataJson, true));
                } else {
                    log.error("通知数据异常！！！");
                }
            }
            throw new RuntimeException("支付失败");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        throw new RuntimeException("支付失败");
    }

    @Override
    public PlatformTypeEnum getNotifyChannel() {
        return PlatformTypeEnum.sand_pay;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
