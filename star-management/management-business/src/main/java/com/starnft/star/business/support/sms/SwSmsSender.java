package com.starnft.star.business.support.sms;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("swSmsSender")
@Slf4j
public class SwSmsSender {
  @Resource
  SwMessageConfig swMessageConfig;
    public Boolean send(String mobiles,String context,String orderSn){

        long timestamp = new Date().getTime();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("timestamp", timestamp);
        requestMap.put("phone", mobiles);
        requestMap.put("appkey", swMessageConfig.getSwMessageAppKey());
        requestMap.put("msg", swMessageConfig.getSwMessageHeader().concat(context));
        requestMap.put("extend", orderSn);
        requestMap.put("appcode", swMessageConfig.getSwMessageAppCode());
        requestMap.put("sign", getSign(timestamp));
        String post = HttpUtil.post(swMessageConfig.getSwMessageApi(), JSONUtil.toJsonStr(requestMap));
        JSONObject result = JSONUtil.parseObj(post);
        if (result.getStr("code").equals("00000")) {
            JSONArray array = result.getJSONArray("result");
            JSONObject object = array.getJSONObject(0);
            if (object.getStr("status").equals("00000")) {
                return true;
            }
        }
        log.info("发送短信失败 响应结果： {} ,状态码：{}", result, result.getStr("code"));
        return false;
    }
    private String getSign(long timestamp) {
        return SecureUtil.md5(swMessageConfig.getSwMessageAppKey().concat(swMessageConfig.getSwMessageAppSecret()).concat(Long.toString(timestamp)));
    }

}
