package com.star.nft.test;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.starnft.star.common.template.TemplateHelper;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest(classes = {StarApplication.class})
public class RankTest {
    @Resource
    TemplateHelper templateHelper;
    @Resource
    IRankService rankService;
    @Test
    public void  setSms() throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("account","123455");
        dataMap.put("password","121212");
        dataMap.put("types","121212");
        List<String> mobiles=new ArrayList<>();
        mobiles.add("17603264064");
        dataMap.put("mobiles",mobiles);
        dataMap.put("snCode","1233444");
        dataMap.put("times","时间");
        dataMap.put("content","内容");
        String process = templateHelper.process("sms/sms_shenzhou_req.ftl", dataMap);
        System.out.println(process);
        HttpResponse result = HttpUtil.createPost("http://userinterface.vcomcn.com/Opration.aspx").contentType("text/xml").body(process).execute();
        System.out.println(result);

    }
}
