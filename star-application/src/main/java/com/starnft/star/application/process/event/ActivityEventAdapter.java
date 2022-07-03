package com.starnft.star.application.process.event;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.event.model.service.IEventActivityService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("activityEventAdapter")
public class ActivityEventAdapter {
    @Resource
    IEventActivityService eventActivityService;
    public void handler(ActivityEventReq activityEventReq) {
        EventActivityExtReq eventActivityExtReq = new EventActivityExtReq();
        eventActivityExtReq.setActivitySign(activityEventReq.getActivitySign());
        eventActivityExtReq.setEventSign(activityEventReq.getEventSign());
        List<EventActivityExtRes> extResList = eventActivityService.queryEventActivityParams(eventActivityExtReq);


    }
}
