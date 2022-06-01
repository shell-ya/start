package com.starnft.star.domain.notify.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.starnft.star.common.enums.PlatformTypeEnum;
import com.starnft.star.domain.notify.model.NotifyDTO;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class SandPayNotifyHandler implements INotifyHandler {
    @Resource
    SdKeysHelper sdKeysHelper;

    @Override
    public String doNotify(HttpServletRequest request) {
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
                log.info("verify sign success");
                JSONObject dataJson = JSONObject.parseObject(data);
                if (dataJson != null) {
                    if (dataJson.getJSONObject("head").getString("respCode").equals("000000")) {
                        return JSONUtil.toJsonStr(

                                NotifyDTO
                                        .builder()
//                                .orderCode(dataJson.getJSONObject("body").getString("tradeNo"))
//                                .platformTradeNo(dataJson.getJSONObject("body").getString("payOrderCode"))
//                                .payStatus(DictConstants.PayStatus.SUCCESS.getCode())
                                        .build()
                        );
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
}
