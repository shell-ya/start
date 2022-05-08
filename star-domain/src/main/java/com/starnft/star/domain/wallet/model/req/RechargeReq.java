package com.starnft.star.domain.wallet.model.req;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeReq {

    /** userId*/
    private Long userId;
    /** 钱包id*/
    private String walletId;
    /** 充值金额*/
    private BigDecimal money;
    /** 充值后当前金额*/
    private BigDecimal currentMoney;
    /** 充值渠道*/
    private String payChannel;
    /** 充值流水号*/
    private String payNo;
    /** 充值时间*/
    private Date payTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(BigDecimal currentMoney) {
        this.currentMoney = currentMoney;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
