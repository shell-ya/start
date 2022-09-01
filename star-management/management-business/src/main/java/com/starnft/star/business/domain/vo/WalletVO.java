package com.starnft.star.business.domain.vo;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class WalletVO {

    private Long uid;

    private String walletId;

    private BigDecimal balance;

    private BigDecimal wallet_income;

    private BigDecimal wallet_outcome;

    private boolean frozen;

    private BigDecimal frozen_fee;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWallet_income() {
        return wallet_income;
    }

    public void setWallet_income(BigDecimal wallet_income) {
        this.wallet_income = wallet_income;
    }

    public BigDecimal getWallet_outcome() {
        return wallet_outcome;
    }

    public void setWallet_outcome(BigDecimal wallet_outcome) {
        this.wallet_outcome = wallet_outcome;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getFrozen_fee() {
        return frozen_fee;
    }

    public void setFrozen_fee(BigDecimal frozen_fee) {
        this.frozen_fee = frozen_fee;
    }
}
