package com.star.nft.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.starnft.star.application.process.user.req.PayRecordReq;
import com.starnft.star.domain.support.aware.ConfigurationHolder;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {StarApplication.class})
public class SpringTest {

    @Test
    public void repoTest() {
        System.out.println(ConfigurationHolder.getPayConfig().getChannel());
    }

    @Test
    public void test() {
        PayRecordReq payRecordReq = new PayRecordReq();
//        starNftDict.setDictDesc("1");
//        String s = JSON.toJSONString(starNftDict);
//        System.out.println(s);
        JSONObject result = JSON.parseObject(JSON.toJSONString(payRecordReq,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero));
        System.out.println(result.toJSONString());
    }
}
