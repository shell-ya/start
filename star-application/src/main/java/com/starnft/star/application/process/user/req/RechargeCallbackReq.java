package com.starnft.star.application.process.user.req;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RechargeCallbackReq implements Serializable {

    private String userId;

    private Integer payStatus;

    private String outTradeNo;


}
