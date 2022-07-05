package com.starnft.star.application.mq.producer.socpe;

import cn.hutool.json.JSONUtil;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
@Service
public class ActivityEventProducer extends BaseProducer {
    public void sendScopeMessage(ActivityEventReq activityEventReq){
        String topic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
        messageSender.send(topic, Optional.of(activityEventReq));
    }





    public static void main(String[] args) {
        @Data
        class Params{
            private String template;
            private BigDecimal scale;
            private Integer scopeType;
        }
        Params params = new Params();
        params.setScale(new BigDecimal(1));
        params.setTemplate("参加拉新活动获得%s积分");
        params.setScopeType(0);
        System.out.println(JSONUtil.toJsonStr(params));
    }
}
