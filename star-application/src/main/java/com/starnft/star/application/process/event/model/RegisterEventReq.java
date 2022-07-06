package com.starnft.star.application.process.event.model;

import lombok.Data;

@Data
public class RegisterEventReq  extends  BaseActivityEventReq{
    private Long parent;
    private Long number;
}
