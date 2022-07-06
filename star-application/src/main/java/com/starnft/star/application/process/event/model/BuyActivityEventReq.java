package com.starnft.star.application.process.event.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuyActivityEventReq extends  BaseActivityEventReq{
    private Long seriesId;
    //购买的主题id
    private Long themeId;
    //购买的金额
    private BigDecimal money;


}
