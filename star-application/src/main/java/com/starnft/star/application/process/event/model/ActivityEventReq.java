package com.starnft.star.application.process.event.model;

import lombok.Data;

import java.util.Map;

@Data
public class ActivityEventReq {
    private String eventSign;
    private String activitySign;
    private Map<String,Object> params;
}
