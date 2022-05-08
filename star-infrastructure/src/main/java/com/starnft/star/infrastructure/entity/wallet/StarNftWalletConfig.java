package com.starnft.star.infrastructure.entity.wallet;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@TableName("star_nft_wallet_config")
public class StarNftWalletConfig extends BaseEntity {

    /** id */
    @ApiModelProperty(name = "id",notes = "")
    @TableId
    private Long id ;
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
    private String channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
