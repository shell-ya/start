package com.starnft.star.domain.wallet.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
public class WalletRecordVO implements Serializable {

    @ApiModelProperty(name = "交易流水号", notes = "")
    private String recordSn;
    /**
     * 支付方uid 0系统充值 -1 系统退款
     */
    @ApiModelProperty(name = "支付方uid 0系统充值 -1 系统退款", notes = "")
    private Long fromUid;
    /**
     * 接收方uid 0系统提现
     */
    @ApiModelProperty(name = "接收方uid 0系统提现", notes = "")
    private Long toUid;
    /**
     * 交易类型 1充值 2提现 3交易 4退款
     */
    @ApiModelProperty(name = "交易类型 1充值 2提现 3交易 4退款", notes = "")
    private String tsType;
    /**
     * 交易金额
     */
    @ApiModelProperty(name = "交易金额", notes = "")
    private BigDecimal tsMoney;
    /**
     * 支付渠道 0未知 1支付宝 2微信 3银行卡 4余额
     */
    @ApiModelProperty(name = "支付渠道 0未知 1支付宝 2微信 3银行卡 4余额", notes = "")
    private String payChannel;
    /**
     * 备注
     */
    @ApiModelProperty(name = "备注", notes = "")
    private String remark;
    /**
     * 支付状态 待支付 失败 成功 支付中 支付关闭
     */
    @ApiModelProperty(name = "支付状态 待支付 失败 成功 支付中 支付关闭", notes = "")
    private String payStatus;
    /**
     * 交易时间
     */
    @ApiModelProperty(name = "交易时间", notes = "")
    private Date payTime;
    /**
     * 收款状态 0待收款 -1失败 1成功
     */
    @ApiModelProperty(name = "收款状态 0待收款 -1失败 1成功", notes = "")
    private String fetchStatus;
    /**
     * 收款时间
     */
    @ApiModelProperty(name = "收款时间", notes = "")
    private Date fetchTime;
    /**
     * 对账状态 0未对账 1已对账
     */
    @ApiModelProperty(name = "对账状态 0未对账 1已对账", notes = "")
    private Integer checkStatus;
    /**
     * 对账时间
     */
    @ApiModelProperty(name = "对账时间", notes = "")
    private Date checkTime;

    public String getRecordSn() {
        return recordSn;
    }

    public void setRecordSn(String recordSn) {
        this.recordSn = recordSn;
    }

    public Long getFromUid() {
        return fromUid;
    }

    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public String getTsType() {
        return tsType;
    }

    public void setTsType(String tsType) {
        this.tsType = tsType;
    }

    public BigDecimal getTsMoney() {
        return tsMoney;
    }

    public void setTsMoney(BigDecimal tsMoney) {
        this.tsMoney = tsMoney;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getFetchStatus() {
        return fetchStatus;
    }

    public void setFetchStatus(String fetchStatus) {
        this.fetchStatus = fetchStatus;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}
