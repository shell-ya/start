package com.starnft.star.domain.payment.model.res;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CloudAccountBalanceRes {
    private String ResponseStatus;
    private BigDecimal balance;
}
