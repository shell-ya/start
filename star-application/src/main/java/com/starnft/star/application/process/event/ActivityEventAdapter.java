package com.starnft.star.application.process.event;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("activityEventAdapter")
public class ActivityEventAdapter {
    @Resource
  public void handler(ActivityEventReq activityEventReq){

  }
}
