package com.star.nft.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.notification.NotificationProducer;
import com.starnft.star.application.process.notification.vo.NotificationVO;
import com.starnft.star.application.process.wallet.req.PayRecordReq;
import com.starnft.star.common.template.FreeMakerTemplateHelper;
import com.starnft.star.common.template.TemplateHelper;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.ChannelConf;
import com.starnft.star.domain.support.process.config.TempConf;
import com.starnft.star.interfaces.StarApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = {StarApplication.class})
@Slf4j
public class SpringTest {

    @Resource
    private NotificationProducer notificationProducer;

    @Resource
    private ChannelConf channelConf;

    @Resource
    private FreeMakerTemplateHelper templateHelper;

    @Test
    @SneakyThrows
    public void tempconf() {
        log.info(JSON.toJSONString(channelConf.getTempConfs()));

        List<TempConf> tempConfs = channelConf.getTempConfs();

        TempConf temp = null;
        for (TempConf tempConf : tempConfs) {
            if (tempConf.getTrade().equals(TradeType.SandPay.name())) {
                temp = tempConf;
            }
        }

        String reqTempPath = temp.getReqTempPath();

        HashMap<@Nullable String, @Nullable String> map =
                Maps.newHashMap();
        map.put("a", "123");
        map.put("b", "234");
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("request", map);

        String process = templateHelper.process(reqTempPath, dataModel);

        System.out.println(process);


    }

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
    public void mqTest() {
        notificationProducer.sendNotification(NotificationVO.builder()
                .toUid("10001").title("标题")
                .intro("简介").sendTime(new Date()).content("内容").build());
    }
}
