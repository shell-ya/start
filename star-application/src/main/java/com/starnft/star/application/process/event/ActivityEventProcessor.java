package com.starnft.star.application.process.event;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.strategy.EventStrategy;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component("activityEventProcessor")
public class ActivityEventProcessor {
    @Resource
    ApplicationContext applicationContext;
    @Transactional
    public void processor(List<EventActivityExtRes> extResList, ActivityEventReq activityEventReq) {
        for (EventActivityExtRes activityExtRes : extResList) {
            Map<String, EventStrategy> beansOfType = applicationContext.getBeansOfType(EventStrategy.class);
            for (EventStrategy value : beansOfType.values()) {
                if (value.getEventType().getValue().equals(activityExtRes.getExtType())) {
                    value.handler(activityExtRes,activityEventReq);
                }
            }
        }
    }
}
