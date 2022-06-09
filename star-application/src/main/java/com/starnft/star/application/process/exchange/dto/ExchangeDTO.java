package com.starnft.star.application.process.exchange.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ExchangeDTO implements Serializable {
    private Long buyUid;
    private Long sellUid;
    private BigDecimal amount;
    private Long goodId;


}
