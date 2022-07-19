package com.star.nft.test;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.activity.ActivityEventProducer;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.model.BuyActivityEventReq;
import com.starnft.star.application.process.event.model.EventReqAssembly;
import com.starnft.star.application.process.event.model.RegisterEventReq;
import com.starnft.star.application.process.rank.impl.RankProcessor;
import com.starnft.star.application.process.rank.req.RankReq;
import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.application.process.user.res.ShareCodeRes;
import com.starnft.star.application.process.user.res.UserInfoRes;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.domain.rank.core.rank.model.res.Rankings;
import com.starnft.star.interfaces.StarApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    UserCore userCore;

    @Resource
    RankProcessor rankProcessor;

    @Resource
    ActivityEventProducer activityEventProducer;
    static String shardCode = "9XHF8RR7";
    @Test
    //活动注册流程向MQ发送标记
    public void pullScopeTest(){
        RegisterEventReq scopeEventReq = new RegisterEventReq();
        scopeEventReq.setActivitySign("first_launch_acquisition");
        scopeEventReq.setEventSign(StarConstants.EventSign.Register.getSign());
        scopeEventReq.setUserId(546108553L);
        scopeEventReq.setNumber(1L);
        scopeEventReq.setReqTime(new Date());
        scopeEventReq.setParent(409412742L);
        String scopeTopic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
        messageSender.send(scopeTopic, Optional.of( EventReqAssembly.assembly(scopeEventReq)));
    }

    @Test
    //活动购买流程向MQ发送
    public void BuyPullMqTest(){
        BuyActivityEventReq buyActivityEventReq = new BuyActivityEventReq();
        buyActivityEventReq.setEventSign(StarConstants.EventSign.Buy.getSign());
        buyActivityEventReq.setUserId(546108553L);
        buyActivityEventReq.setReqTime(new Date());
        buyActivityEventReq.setSeriesId(4L);
        buyActivityEventReq.setThemeId(991131478355697664L);
        buyActivityEventReq.setMoney(new BigDecimal(22));
       String scopeTopic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
       messageSender.send(scopeTopic, Optional.of( EventReqAssembly.assembly(buyActivityEventReq)));
    }


    @Test
    public void getRank(){
        Map entries = redisTemplate.opsForHash().entries(RedisKey.RANK_LIST.getKey());
        System.out.println(entries);
        Object test_tank = redisTemplate.opsForHash().hasKey(RedisKey.RANK_LIST.getKey(), "test_tank");
        System.out.println(test_tank);

    }

    @Test
    public void genShardCode(){
        ShareCodeRes s = userCore.shareCodeInfo(409412742L);
        log.info("邀请码：{}",s);
    }

    @Test
    public void login(){
        ShareCodeRes code = userCore.shareCodeInfo(409412742L);
        log.info("code:{}",code);
        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setLoginScenes(2);
//        userLoginReq.setActivityType("launch_acquistion");
        userLoginReq.setCode("1563531");
//        userLoginReq.setPassword();
//        userLoginReq.setShareCode(code.getShareCode());
        userLoginReq.setPhone("15830887988");
        UserInfoRes userInfoRes = userCore.loginByPhoneAndRegister(userLoginReq);

    }

    @Test
    public void  id(){
        Long aLong = SnowflakeWorker.generateId();
        log.info(aLong.toString());
    }


    @Test
    public void buy(){
        BuyActivityEventReq buyActivityEventReq = new BuyActivityEventReq();
        buyActivityEventReq.setEventSign(StarConstants.EventSign.Buy.getSign());
        buyActivityEventReq.setUserId(320266671L);
        buyActivityEventReq.setReqTime(new Date());
        buyActivityEventReq.setSeriesId(5L);
        buyActivityEventReq.setThemeId(995367819184877568L);
        buyActivityEventReq.setMoney(new BigDecimal("1.9"));
        ActivityEventReq assembly = EventReqAssembly.assembly(buyActivityEventReq);
        activityEventProducer.sendScopeMessage(assembly);
    }

    @Test
    public void rank(){
        RequestConditionPage<RankReq> rankReqRequestConditionPage = new RequestConditionPage<>();
        rankReqRequestConditionPage.setPage(1);
        rankReqRequestConditionPage.setSize(10);
        RankReq rankReq = new RankReq();
//        rankReq.setRankName("test_rank");
        rankReqRequestConditionPage.setCondition(rankReq);
        Rankings rankings = rankProcessor.rankings(rankReqRequestConditionPage);

        log.info(rankings.toString());
    }

//    @Test
//    public void rankHistory(){
//        RequestConditionPage<RankReq> rankReqRequestConditionPage = new RequestConditionPage<>();
//        rankReqRequestConditionPage.setPage(1);
//        rankReqRequestConditionPage.setSize(10);
//        RankReq rankReq = new RankReq();
//        rankReq.setRankName("test_rank");
//        rankReq.setUserId(409412742L);
//        rankReqRequestConditionPage.setCondition(rankReq);
//        List<InvitationHistoryItem> invitationHistoryItems = rankProcessor.invitationHistory(rankReqRequestConditionPage);
//        log.info(invitationHistoryItems.toString());
//    }
//
//    @Test
//    public void rankNum(){
//        RankReq rankReq = new RankReq();
//        rankReq.setRankName("test_rank");
//        rankReq.setUserId(409412742L);
//        Long aLong = rankProcessor.personNum(rankReq);
//        log.info(aLong.toString());
//    }

    @Test

    public void name(){
        RankDefinition nowRank = rankProcessor.getNowRank();
        log.info(nowRank.toString());
    }

}
