package com.starnft.star.application.process.event.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ActivityEventReq implements Serializable {
    //动作标记
    private String eventSign;
    //活动标记
    private String activitySign;
    //用户ID
    private Long userId;
    //所需参数
    private Map<String,Object> params;

}
