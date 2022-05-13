package com.star.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.starnft.star.domain.support.key.ApiKeyInfo;
import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import com.starnft.star.infrastructure.entity.dict.StarNftDict;
import com.starnft.star.management.StarManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {StarManagementApplication.class})
public class POtoJsonTest {

    @Resource
    IDictionaryRepository repository;

    @Test
    public void dictJson() {
        StarNftDict starNftDict = new StarNftDict();
//        starNftDict.setDictDesc("1");
//        String s = JSON.toJSONString(starNftDict);
//        System.out.println(s);
        JSONObject result = JSON.parseObject(JSON.toJSONString(starNftDict,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero));
        System.out.println(result.toJSONString());

    }

    @Test
    public void repoTest() {
        ApiKeyInfo apikeys = repository.getApiKeyInfo("APIKEY", ApiKeyInfo.class);
        System.out.println(JSON.toJSONString(apikeys));
    }
}
