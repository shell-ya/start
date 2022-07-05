package com.starnft.star.application.process.event.model;

import lombok.Data;

@Data
public class RankEventReq  extends  BaseActivityEventReq{
    private Long parent;
    private Long number;
}
