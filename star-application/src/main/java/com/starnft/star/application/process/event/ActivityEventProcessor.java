package com.starnft.star.application.process.event;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.strategy.ActivityEventStrategy;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component("activityEventProcessor")
@Slf4j
public class ActivityEventProcessor {
    @Resource
    ApplicationContext applicationContext;
    @Transactional
    public void processor(List<EventActivityExtRes> extResList, ActivityEventReq activityEventReq) {
        for (EventActivityExtRes activityExtRes : extResList) {
            Map<String, ActivityEventStrategy> beansOfType = applicationContext.getBeansOfType(ActivityEventStrategy.class);
            for (ActivityEventStrategy eventStrategy : beansOfType.values()) {
                if (eventStrategy.getEventType().getValue().equals(activityExtRes.getExtType())) {
                    log.info("实现活动为「{}」",eventStrategy.getEventType().getDesc());
                    eventStrategy.handler(activityExtRes,activityEventReq);
                }
            }
        }
    }
}
