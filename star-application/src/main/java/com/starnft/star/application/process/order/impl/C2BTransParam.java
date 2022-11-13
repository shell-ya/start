package com.starnft.star.application.process.order.impl;

import lombok.Data;

/**
 * C2C 转账参数
 */
@Data
public class C2BTransParam {

    String recvUserId;

    String payUserId;

    String order_amt;


    // 回调地址
    String notify_url;

    String return_url;

    String mer_order_no;
}
