package com.starnft.star.application.process.wallet.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeFacadeReq implements Serializable {

    private Long userId;

    private BigDecimal money;

    private String channel;
}
