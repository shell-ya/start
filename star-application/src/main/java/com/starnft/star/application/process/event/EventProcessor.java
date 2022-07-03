package com.starnft.star.application.process.event;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("eventProcessor")
public class EventProcessor {
    public  void processor(List<EventActivityExtRes> extResList, ActivityEventReq activityEventReq){
        for (EventActivityExtRes activityExtRes : extResList) {
            Integer extType = activityExtRes.getExtType();

        }
    }
}
