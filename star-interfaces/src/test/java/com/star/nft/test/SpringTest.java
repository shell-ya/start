package com.star.nft.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.notification.NotificationProducer;
import com.starnft.star.application.process.notification.vo.NotificationVO;
import com.starnft.star.application.process.user.req.PayRecordReq;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;


@SpringBootTest(classes = {StarApplication.class})
public class SpringTest {

    @Resource
    private NotificationProducer notificationProducer;

    @Test
    public void repoTest() {
//        System.out.println(ConfigurationHolder.getPayConfig().getChannel());
        System.out.println(String.format(TopicConstants.NOTICE_UNREAD_DESTINATION.getFormat(), TopicConstants.NOTICE_UNREAD_DESTINATION.getTag()));
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

    @Test
    public void mqTest(){
        notificationProducer.sendNotification(NotificationVO.builder()
                .toUid("10001").title("标题")
                .intro("简介").sendTime(new Date()).content("内容").build());
    }
}
