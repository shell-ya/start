package com.starnft.star.application.process.order.impl;

import lombok.Data;

/**
 * C2C 转账参数
 */
@Data
public class C2CTransParam {

    String recvUserId;

    String payUserId;

    String order_amt;

    // 用户服务费，商户向用户收取的服务费
    String userFeeAmt;


    // 回调地址
    String notify_url;

    String return_url;

    String mer_order_no;
}
