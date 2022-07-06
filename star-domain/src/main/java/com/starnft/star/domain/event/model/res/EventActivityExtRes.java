package com.starnft.star.domain.event.model.res;

import lombok.Data;

@Data
public class EventActivityExtRes {
    private Integer extType;
    private Long  activityId;
    private  String eventSign;
    private String params;
}
