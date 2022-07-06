package com.starnft.star.application.process.event;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("activityEventAdapter")
@Slf4j
public class ActivityEventAdapter {
    @Resource
    IEventActivityService eventActivityService;
    @Resource
    ActivityEventProcessor processor;
    public void handler(ActivityEventReq activityEventReq) {
        if (Objects.nonNull(activityEventReq.getActivitySign())){
            log.info("活动标记不为空");
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
        }else {
            log.info("活动标记为空");
            String eventSign = activityEventReq.getEventSign();
            EventActivityExtReq eventActivityExtReq = new EventActivityExtReq();
            eventActivityExtReq.setEventSign(eventSign);
            List<EventActivityExtRes> extArrays = eventActivityService.queryEventActivityParams(eventActivityExtReq);
            log.info("找到关于动作标记:{}的活动内容{}条",activityEventReq.getEventSign(),extArrays.size());
            if (extArrays.isEmpty()){
                return;
            }
            Set<Long> activityIds = extArrays.stream().map(item -> item.getActivityId()).collect(Collectors.toSet());

            Map<Long, EventActivityRes> collect = eventActivityService.queryEventActivityByIds(activityIds).stream().collect(Collectors.toMap(EventActivityRes::getId, Function.identity()));
            List<EventActivityExtRes> isTrueExtArrays= Lists.newArrayList();
            for (EventActivityExtRes extArray : extArrays) {
                EventActivityRes eventActivityRes = collect.get(extArray.getActivityId());
                if(Objects.nonNull(eventActivityRes)){
                    if (eventActivityRes.getActivityStatus().equals(StarConstants.EventStatus.EVENT_STATUS_OPEN)){
                        Date reqTime = Optional.ofNullable(activityEventReq.getReqTime()).orElse(new Date());
                        if (!(reqTime.before(eventActivityRes.getStartTime())||reqTime.after(eventActivityRes.getEndTime()))){
                            isTrueExtArrays.add(extArray);
                        }
                    }
                }
            }

            log.info("找到可用的动作标记:{}的活动内容{}条",activityEventReq.getEventSign(),extArrays.size());
            processor.processor(isTrueExtArrays,activityEventReq);
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
