package com.starnft.star.application.process.number.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 市场订单请求类
 * @Date 2022/6/17 1:45 PM
 * @Author ： shellya
 */
@Data
public class MarketOrderReq implements Serializable {

    private Long numberId;
    private Long userId;
}
