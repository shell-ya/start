package com.starnft.star.application.process.wallet.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class RechargeReqResult implements Serializable {

   //拉起支付参数
    private String orderSn;

    // 拉起支付跳转的url  后端做拼装处理 不同厂商拉起域名不同
    private String forward;


}
