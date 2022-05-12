package com.starnft.star.application.process.user.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeReq implements Serializable {

    private Long userId;

    private BigDecimal money;

    private String channel;
}
