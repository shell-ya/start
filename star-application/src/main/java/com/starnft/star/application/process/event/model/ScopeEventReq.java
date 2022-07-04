package com.starnft.star.application.process.event.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ScopeEventReq  extends BaseActivityEventReq {
    private BigDecimal  number;
}
