package com.starnft.star.domain.wallet.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CalculateReq implements Serializable {

    private BigDecimal money;

    private String channel;

}
