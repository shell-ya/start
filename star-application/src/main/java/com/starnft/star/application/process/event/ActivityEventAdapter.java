package com.starnft.star.application.process.event;

import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.req.EventActivityExtReq;
import com.starnft.star.domain.event.model.req.EventActivityReq;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.event.model.res.EventActivityRes;
import com.starnft.star.domain.event.model.service.IEventActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component("activityEventAdapter")
@Slf4j
public class ActivityEventAdapter {
    @Resource
    IEventActivityService eventActivityService;
    @Resource
    ActivityEventProcessor processor;
    public void handler(ActivityEventReq activityEventReq) {
        EventActivityReq eventActivityReq = new EventActivityReq();
        eventActivityReq.setActivitySign(activityEventReq.getActivitySign());
        EventActivityRes eventActivityRes = eventActivityService.queryEventActivity(eventActivityReq); //获取什么活动
        log.info("获取到的活动参数为{}", JSONUtil.toJsonStr(eventActivityRes));
        if (judgeActivity(eventActivityRes,activityEventReq)){ //判断活动是否有效
            //符合活动条件进入
            log.info("符合活动条件进入");
            EventActivityExtReq eventActivityExtReq = new EventActivityExtReq();
            eventActivityExtReq.setActivitySign(activityEventReq.getActivitySign());
            eventActivityExtReq.setEventSign(activityEventReq.getEventSign());
            List<EventActivityExtRes> extResList = eventActivityService.queryEventActivityParams(eventActivityExtReq);  //获取活动ext
            log.info("找到关于活动标记:{},动作标记:{}的活动内容{}条",activityEventReq.getActivitySign(),activityEventReq.getEventSign(),extResList.size());
            processor.processor(extResList,activityEventReq);
        }
    }
    private Boolean judgeActivity(EventActivityRes eventActivityRes,ActivityEventReq activityEventReq){
        if (Objects.isNull(eventActivityRes)){
            return false;
        }
        if (eventActivityRes.getActivityStatus().equals(StarConstants.EventStatus.EVENT_STATUS_CLOSE)){
            return false;
        }
        Date reqTime = Optional.ofNullable(activityEventReq.getReqTime()).orElse(new Date());
        if (reqTime.before(eventActivityRes.getStartTime())||reqTime.after(eventActivityRes.getEndTime())){
            return false;
        }
      return true;
    }
}
