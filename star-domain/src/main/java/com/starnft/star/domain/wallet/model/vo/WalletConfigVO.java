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
     * 手续费比例
     */
    @ApiModelProperty(name = "手续费比例", notes = "")
    private BigDecimal chargeRate;
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

    public BigDecimal getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(BigDecimal chargeRate) {
        this.chargeRate = chargeRate;
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
