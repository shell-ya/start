package com.starnft.star.domain.event.model.res;

import lombok.Data;

import java.util.Date;

@Data
public class EventActivityRes {

    private Long id;


    private String activitySign;


    private String activityName;


    private Date startTime;


    private Date endTime;


    private Boolean isTimes;


    private Integer activityStatus;


    private String extend;
}
