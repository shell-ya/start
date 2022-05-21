package com.starnft.star.infrastructure.entity.wallet;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提现申请表;
 *
 * @author : Ryan Z
 * @date : 2022-5-21
 */
@ApiModel(value = "提现申请表", description = "")
@TableName("star_nft_withdraw_apply")
public class StarNftWithdrawApply extends BaseEntity implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * 申请提现uid
     */
    @ApiModelProperty(name = "申请提现uid", notes = "")
    private Long withdrawUid;
    /**
     * 提现金额
     */
    @ApiModelProperty(name = "提现金额", notes = "")
    private BigDecimal withdrawMoney;
    /**
     * 提现流水号
     */
    @ApiModelProperty(name = "提现流水号", notes = "")
    private String withdrawTradeNo;
    /**
     * 提现银行卡号
     */
    @ApiModelProperty(name = "提现银行卡号", notes = "")
    private Long withdrawBankNo;
    /**
     * 持卡人姓名
     */
    @ApiModelProperty(name = "持卡人姓名", notes = "")
    private String bankMatchName;
    /**
     * 提现渠道
     */
    @ApiModelProperty(name = "提现渠道", notes = "")
    private String withdrawChannel;
    /**
     * 提现申请时间
     */
    @ApiModelProperty(name = "提现申请时间", notes = "")
    private String applyTime;
    /**
     * 提现申请通过时间
     */
    @ApiModelProperty(name = "提现申请通过时间", notes = "")
    private String applyPassTime;
    /**
     * 提现状态
     */
    @ApiModelProperty(name = "提现状态", notes = "")
    private Integer applyStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWithdrawUid() {
        return withdrawUid;
    }

    public void setWithdrawUid(Long withdrawUid) {
        this.withdrawUid = withdrawUid;
    }

    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getWithdrawTradeNo() {
        return withdrawTradeNo;
    }

    public void setWithdrawTradeNo(String withdrawTradeNo) {
        this.withdrawTradeNo = withdrawTradeNo;
    }

    public Long getWithdrawBankNo() {
        return withdrawBankNo;
    }

    public void setWithdrawBankNo(Long withdrawBankNo) {
        this.withdrawBankNo = withdrawBankNo;
    }

    public String getBankMatchName() {
        return bankMatchName;
    }

    public void setBankMatchName(String bankMatchName) {
        this.bankMatchName = bankMatchName;
    }

    public String getWithdrawChannel() {
        return withdrawChannel;
    }

    public void setWithdrawChannel(String withdrawChannel) {
        this.withdrawChannel = withdrawChannel;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyPassTime() {
        return applyPassTime;
    }

    public void setApplyPassTime(String applyPassTime) {
        this.applyPassTime = applyPassTime;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }
}
