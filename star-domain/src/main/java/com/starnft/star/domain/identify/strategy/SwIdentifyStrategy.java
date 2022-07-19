package com.starnft.star.domain.identify.strategy;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.starnft.star.domain.identify.interfaces.IdentifyStrategyInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("swIdentifyStrategy")
@Slf4j
public class SwIdentifyStrategy implements IdentifyStrategyInterface {
    private final String appId = "fIatb52I";
    private final String appKey = "N2UWBXTc";
    private final String url = "http://139.224.206.237:3333/user/api/id-card-auth";

    @Override
    public boolean checkNameAndIdCard(String name, String idCard) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("name", name);
            map.put("idNum", idCard);
            map.put("appId", appId);
            map.put("appKey", appKey);
            String post = HttpUtil.post(url, map);
            JSONObject json = JSONObject.parseObject(post);
            log.info("身份验证回调「{}」",post);
            Boolean identify = json.getBoolean("message");
            JSONObject data = json.getJSONObject("data");
            return BooleanUtils.isTrue(identify) && StringUtils.equals("01", data.getString("result"));
        } catch (Throwable e) {
            return false;
        }

    }
}
