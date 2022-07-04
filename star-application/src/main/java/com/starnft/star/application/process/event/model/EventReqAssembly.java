package com.starnft.star.application.process.event.model;

import cn.hutool.core.bean.BeanUtil;

public class EventReqAssembly {
   public static ActivityEventReq assembly(RankEventReq req){
       ActivityEventReq activityEventReq = new ActivityEventReq();
       activityEventReq.setActivitySign(req.getActivitySign());
       activityEventReq.setEventSign(req.getEventSign());
       activityEventReq.setUserId(req.getUserId());
       activityEventReq.setParams(BeanUtil.beanToMap(req));
       return activityEventReq;
   }
    public static ActivityEventReq assembly(ScopeEventReq req){
        ActivityEventReq activityEventReq = new ActivityEventReq();
        activityEventReq.setActivitySign(req.getActivitySign());
        activityEventReq.setEventSign(req.getEventSign());
        activityEventReq.setUserId(req.getUserId());
        activityEventReq.setParams(BeanUtil.beanToMap(req));
        return activityEventReq;
    }
}
