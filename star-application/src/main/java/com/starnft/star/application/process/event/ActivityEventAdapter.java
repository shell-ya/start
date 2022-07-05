package com.starnft.star.application.process.event;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.req.EventActivityReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.event.model.res.EventActivityRes;
import com.starnft.star.domain.event.model.service.IEventActivityService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("activityEventAdapter")
public class ActivityEventAdapter {
    @Resource
    IEventActivityService eventActivityService;
    @Resource
    ActivityEventProcessor processor;
    public void handler(ActivityEventReq activityEventReq) {
        EventActivityReq eventActivityReq = new EventActivityReq();
        eventActivityReq.setActivitySign(activityEventReq.getActivitySign());
        EventActivityRes eventActivityRes = eventActivityService.queryEventActivity(eventActivityReq);





        EventActivityExtReq eventActivityExtReq = new EventActivityExtReq();

        eventActivityExtReq.setActivitySign(activityEventReq.getActivitySign());
        eventActivityExtReq.setEventSign(activityEventReq.getEventSign());

        List<EventActivityExtRes> extResList = eventActivityService.queryEventActivityParams(eventActivityExtReq);
        processor.processor(extResList,activityEventReq);
    }
//    private Boolean judgeActivity(EventActivityRes eventActivityRes){
//        if (Objects.isNull(eventActivityRes)){
//            return false;
//        }
//        if (eventActivityRes.getActivityStatus().equals(StarConstants.EventStatus.EVENT_STATUS_CLOSE)){
//            return false;
//        }
//        Date now = new Date();
//        if (eventActivityRes.getStartTime().before(now)||eventActivityRes.getEndTime().after(now)){
//
//        }
//
//    }
}
