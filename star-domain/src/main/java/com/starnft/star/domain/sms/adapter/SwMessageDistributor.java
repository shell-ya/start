package com.starnft.star.domain.sms.adapter;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.sms.SwMessageConfig;
import com.starnft.star.domain.sms.interfaces.MessageDistributor;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.support.process.ProcessInteractionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
@Slf4j
public class SwMessageDistributor implements MessageDistributor, ProcessInteractionHolder {

    @Resource
    Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;
    @Override
    public Boolean delivery(String mobile, String context) {
        String uuid = String.valueOf(idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId());
        long timestamp = new Date().getTime();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("timestamp", timestamp);
        requestMap.put("phone", mobile);
        requestMap.put("appkey", SwMessageConfig.getSwMessageAppKey());
        requestMap.put("msg", SwMessageConfig.getSwMessageHeader().concat(context));
        requestMap.put("extend", uuid);
        requestMap.put("appcode", SwMessageConfig.getSwMessageAppCode());
        requestMap.put("sign", getSign(timestamp));
        String post = HttpUtil.post(SwMessageConfig.getSwMessageApi(), JSONUtil.toJsonStr(requestMap));
        JSONObject result = JSONUtil.parseObj(post);
        if (result.getStr("code").equals("00000")) {
            JSONArray array = result.getJSONArray("result");
            JSONObject object = array.getJSONObject(0);
            if (object.getStr("status").equals("00000")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer deliveryBatch(Set<String> mobile, String context) {
        return null;
    }

    private String getSign(long timestamp) {
        return SecureUtil.md5(SwMessageConfig.getSwMessageAppKey().concat(SwMessageConfig.getSwMessageAppSecret()).concat(Long.toString(timestamp)));
    }

}
