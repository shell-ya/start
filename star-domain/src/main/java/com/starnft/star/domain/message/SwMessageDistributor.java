package com.starnft.star.domain.message;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.lsnft.utils.message.MessageConstants.SwMessageConstants.*;

@Component
@Slf4j
public class SwMessageDistributor implements MessageDistributor {

    @Override
    public Boolean delivery(String mobile, String context) {
        String uuid = IdUtil.getSnowflake().nextIdStr();
        long timestamp = new Date().getTime();
        Map<String,Object> requestMap= new HashMap<>();
           requestMap.put("timestamp", timestamp);
           requestMap.put("phone",mobile);
           requestMap.put("appkey",sw_message_app_key);
           requestMap.put("msg",sw_message_header.concat(context));
           requestMap.put("extend",uuid);
           requestMap.put("appcode",sw_message_app_code);
           requestMap.put("sign",getSign(timestamp));
           String post = HttpUtil.post(sw_message_api, JSONUtil.toJsonStr(requestMap));
           JSONObject result = JSONUtil.parseObj(post);
        if (result.getStr("code").equals("00000")){
            JSONArray array = result.getJSONArray("result");
            JSONObject object = array.getJSONObject(0);
            if(object.getStr("status").equals("00000")){
                return true;
            }
        }
        return false;
    }

    private String getSign(long timestamp){
       return  SecureUtil.md5( sw_message_app_key.concat(sw_message_app_secret).concat(Long.toString(timestamp)));
    }

    @Override
    public Integer deliveryBatch(String mobile, String context) {
        return null;
    }
}
