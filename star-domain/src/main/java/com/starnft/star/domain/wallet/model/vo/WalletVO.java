package com.starnft.star.domain.wallet.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletVO {

    private Long uid;

    private String walletId;
    private String thWId;

    private BigDecimal balance;

    private BigDecimal wallet_income;

    private BigDecimal wallet_outcome;

    private boolean frozen;

    private BigDecimal frozen_fee;


}
