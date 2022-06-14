package com.starnft.star.domain.wallet.model.vo;

import com.starnft.star.common.constant.StarConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public class WalletConfigVO implements Serializable {

    /**
     * 充值限额
     */
    @ApiModelProperty(name = "充值限额", notes = "")
    private BigDecimal rechargeLimit;
    /**
     * 提现限额
     */
    @ApiModelProperty(name = "提现限额", notes = "")
    private BigDecimal withdrawLimit;
    /**
     * 提现次数
     */
    @ApiModelProperty(name = "提现次数")
    private Integer withdrawTimes;
    /**
     * 手续费比例
     */
    @ApiModelProperty(name = "提现手续费比例", notes = "")
    private BigDecimal chargeRate;

    @ApiModelProperty(name = "版权费比率", notes = "")
    private BigDecimal copyrightRate;

    @ApiModelProperty(name = "手续费比例", notes = "")
    private BigDecimal serviceRate;
    /**
     * 固定手续费
     */
    @ApiModelProperty(name = "固定手续费", notes = "")
    private BigDecimal stableRate;
    /**
     * 所属身份
     */
    @ApiModelProperty(name = "所属身份", notes = "")
    private String identityCode;
    /**
     * 渠道
     */
    @ApiModelProperty(name = "渠道", notes = "")
    private StarConstants.PayChannel channel;

    public BigDecimal getRechargeLimit() {
        return rechargeLimit;
    }

    public void setRechargeLimit(BigDecimal rechargeLimit) {
        this.rechargeLimit = rechargeLimit;
    }

    public BigDecimal getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(BigDecimal withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public Integer getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(Integer withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }

    public BigDecimal getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(BigDecimal chargeRate) {
        this.chargeRate = chargeRate;
    }

    public BigDecimal getCopyrightRate() {
        return copyrightRate;
    }

    public void setCopyrightRate(BigDecimal copyrightRate) {
        this.copyrightRate = copyrightRate;
    }

    public BigDecimal getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(BigDecimal serviceRate) {
        this.serviceRate = serviceRate;
    }

    public BigDecimal getStableRate() {
        return stableRate;
    }

    public void setStableRate(BigDecimal stableRate) {
        this.stableRate = stableRate;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public StarConstants.PayChannel getChannel() {
        return channel;
    }

    public void setChannel(StarConstants.PayChannel channel) {
        this.channel = channel;
    }
}
