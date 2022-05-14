package com.starnft.star.application.process.user.res;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserGatheringInfoRes implements Serializable {

    /** ========= User Info =========== */
    private Long uid;

    private String phone;

    private String nickName;

    private String avatar;

    /** ========= User Info END========= */

    /** ========= User WalletInfo ============ */
    private String walletId;

    private BigDecimal balance;

    private BigDecimal wallet_income;

    private BigDecimal wallet_outcome;

    private boolean frozen;

    private BigDecimal frozen_fee;

    /** ========= User WalletInfo END========= */

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
