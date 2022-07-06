package com.star.nft.test;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.event.model.EventReqAssembly;
import com.starnft.star.application.process.event.model.RegisterEventReq;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.interfaces.StarApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@SpringBootTest(classes = {StarApplication.class})
@Slf4j
public class TestActivity {
    @Resource
    IMessageSender messageSender;
    @Resource
    RedisTemplate<String,Object> redisTemplate;
    @Test
    //活动注册流程向MQ发送标记
    public void pullScopeTest(){
        RegisterEventReq scopeEventReq = new RegisterEventReq();
        scopeEventReq.setActivitySign("first_launch_acquisition");
        scopeEventReq.setEventSign(StarConstants.EventSign.Register.getDesc());
        scopeEventReq.setUserId(310196862L);
        scopeEventReq.setNumber(1L);
        scopeEventReq.setReqTime(new Date());
        scopeEventReq.setParent(409412742L);
        String scopeTopic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
        messageSender.send(scopeTopic, Optional.of( EventReqAssembly.assembly(scopeEventReq)));
    }

    @Test
    //活动购买流程向MQ发送
    public void BuyPullMqTest(){
//        RankEventReq scopeEventReq = new RankEventReq();
////        scopeEventReq.setActivitySign("first_launch_acquisition");
//        scopeEventReq.setEventSign("buy");
//        scopeEventReq.setUserId(310196862L);
//        scopeEventReq.setNumber(1L);
//        scopeEventReq.setReqTime(new Date());
//        scopeEventReq.setParent(409412742L);
//        String scopeTopic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
//        messageSender.send(scopeTopic, Optional.of( EventReqAssembly.assembly(scopeEventReq)));
    }


    @Test
    public void getRank(){
        Map entries = redisTemplate.opsForHash().entries(RedisKey.RANK_LIST.getKey());
        System.out.println(entries);
        Object test_tank = redisTemplate.opsForHash().hasKey(RedisKey.RANK_LIST.getKey(), "test_tank");
        System.out.println(test_tank);

    }

}
