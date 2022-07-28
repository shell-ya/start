package com.starnft.star.domain.captcha.helper;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.starnft.star.domain.captcha.config.NetEaseCaptchaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 二次验证
 */
@Component
@Slf4j
public class NECaptchaVerifier {

    private static final String VERSION = "v2";

    /**
     * 二次验证
     *
     * @param validate 验证码组件提交上来的NECaptchaValidate值
     * @return
     */
    public VerifyResult verify(String validate) {
        if (StringUtils.isEmpty(validate) || StringUtils.equals(validate, "null")) {
            return VerifyResult.fakeFalseResult("validate data is empty");
        }
        Map<String, String> params = new HashMap<>(8);
        params.put("captchaId", NetEaseCaptchaConfig.getCaptchaId());
        params.put("validate", validate);
        params.put("user", "");
        // 公共参数
        params.put("secretId", NetEaseCaptchaConfig.getSecretId());
        params.put("version", VERSION);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(ThreadLocalRandom.current().nextInt()));
        // 计算请求参数签名信息
        String signature = sign(NetEaseCaptchaConfig.getSecretKey(), params);
        params.put("signature", signature);
        String resp = HttpRequest.post(NetEaseCaptchaConfig.getVerifyApi())
                .contentType(ContentType.FORM_URLENCODED.toString())
                .formStr(params)
                .execute()
                .body();
        log.info("网易验证码二次校验参数：{} 返回结果：{}", validate, resp);
        return verifyRet(resp);
    }

    /**
     * 生成签名信息
     *
     * @param secretKey 验证码私钥
     * @param params    接口请求参数名和参数值map，不包括signature参数名
     * @return
     */
    public static String sign(String secretKey, Map<String, String> params) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(params.get(key));
        }
        sb.append(secretKey);
        return DigestUtils.md5Hex(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 验证返回结果<br>
     * 1. 当易盾服务端出现异常或者返回异常时，优先使用返回true的结果，反之阻塞用户的后序操作<br>
     * 2. 如果想修改为返回false结果。可以调用VerifyResult.fakeFalseResult(java.lang.String)函数
     *
     * @param resp
     * @return
     */
    private VerifyResult verifyRet(String resp) {
        if (StringUtils.isEmpty(resp)) {
            return VerifyResult.fakeTrueResult("return empty response");
        }
        try {
            return JSON.parseObject(resp, VerifyResult.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("网易验证码二次校验结果转换异常", ex);
            return VerifyResult.fakeTrueResult(resp);
        }
    }
}
