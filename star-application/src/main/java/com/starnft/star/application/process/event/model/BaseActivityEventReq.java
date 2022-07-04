package com.starnft.star.application.process.event.model;

import lombok.Data;

@Data
public abstract class BaseActivityEventReq {
    //动作标记
    private String eventSign;
    //活动标记
    private String activitySign;
    //用户id
    private Long userId;
}
