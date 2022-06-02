package com.starnft.star.domain.wallet.model.req;

import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
public class WalletRecordReq implements Serializable {

    /**
     * from userId
     */
    private Long from_uid;
    /**
     * to userId
     */
    private Long to_uid;
    /**
     * 流水号
     */
    private String recordSn;
    /**
     * 交易类型
     */
    private String tsType;
    /**
     * 交易金额
     */
    private BigDecimal tsMoney;

    /**
     * 交易渠道
     */
    private String payChannel;
    /**
     * 交易状态
     */
    private String payStatus;
    /**
     * 交易时间
     */
    private Date payTime;

    public Long getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(Long from_uid) {
        this.from_uid = from_uid;
    }

    public Long getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(Long to_uid) {
        this.to_uid = to_uid;
    }

    public String getRecordSn() {
        return recordSn;
    }

    public void setRecordSn(String recordSn) {
        this.recordSn = recordSn;
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
}
