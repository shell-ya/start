package com.starnft.star.application.process.event.model;

import lombok.Data;

import java.util.Date;

@Data
public class RankEventReq  extends  BaseActivityEventReq{
    private Long parent;
    private Long number;
    private Date reqTime;

}
