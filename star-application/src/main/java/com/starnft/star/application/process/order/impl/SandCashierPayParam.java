package com.starnft.star.application.process.order.impl;

import lombok.Data;

/**
 * 新收银台支付 参数
 */
@Data
public class SandCashierPayParam {

    String userId;

    String userName;

    String idCard;

    String order_amt;

    // 回调地址
    String notify_url;

    String return_url;

    String mer_order_no;
}
