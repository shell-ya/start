package com.starnft.star.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包交易记录对象 star_nft_wallet_record
 *
 * @author shellya
 * @date 2022-06-09
 */
public class StarNftWalletRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** isDeleted */
    @Excel(name = "isDeleted")
    private Integer isDeleted;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 修改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date modifiedAt;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifiedBy;

    /** 交易流水号 */
    @Excel(name = "交易流水号")
    private String recordSn;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderSn;

    /** 第三方交易流水号 */
    @Excel(name = "第三方交易流水号")
    private String outTradeNo;

    /** 支付方uid 0系统充值 -1 系统退款 */
    @Excel(name = "支付方uid 0系统充值 -1 系统退款")
    private Long fromUid;

    /** 接收方uid 0系统提现 */
    @Excel(name = "接收方uid 0系统提现")
    private Long toUid;

    /** 交易类型 1充值 2提现 3买入 4卖出 5退款 */
    @Excel(name = "交易类型 1充值 2提现 3买入 4卖出 5退款")
    private Long tsType;

    /** 交易金额 */
    @Excel(name = "交易金额")
    private BigDecimal tsMoney;

    private BigDecimal tsCost;

    private BigDecimal tsFee;

    /** 支付渠道 0未知 1支付宝 2微信 3银行卡 4余额 */
    @Excel(name = "支付渠道 0未知 1支付宝 2微信 3银行卡 4余额")
    private String payChannel;

    /** 支付状态 0待支付 -1失败 1成功 */
    @Excel(name = "支付状态 0待支付 -1失败 1成功")
    private String payStatus;

    /** 交易时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "交易时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date payTime;

    private BigDecimal currMoney;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setModifiedAt(Date modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public Date getModifiedAt()
    {
        return modifiedAt;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setRecordSn(String recordSn)
    {
        this.recordSn = recordSn;
    }

    public String getRecordSn()
    {
        return recordSn;
    }
    public void setOrderSn(String orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getOrderSn()
    {
        return orderSn;
    }
    public void setOutTradeNo(String outTradeNo)
    {
        this.outTradeNo = outTradeNo;
    }

    public String getOutTradeNo()
    {
        return outTradeNo;
    }
    public void setFromUid(Long fromUid)
    {
        this.fromUid = fromUid;
    }

    public Long getFromUid()
    {
        return fromUid;
    }
    public void setToUid(Long toUid)
    {
        this.toUid = toUid;
    }

    public Long getToUid()
    {
        return toUid;
    }
    public void setTsType(Long tsType)
    {
        this.tsType = tsType;
    }

    public Long getTsType()
    {
        return tsType;
    }
    public void setTsMoney(BigDecimal tsMoney)
    {
        this.tsMoney = tsMoney;
    }

    public BigDecimal getTsMoney()
    {
        return tsMoney;
    }
    public void setTsCost(BigDecimal tsCost)
    {
        this.tsCost = tsCost;
    }
    public BigDecimal getTsCost() {
        return this.tsCost;
    }
    public void setTsFee(BigDecimal tsFee)
    {
        this.tsFee = tsFee;
    }
    public BigDecimal getTsFee() {  return this.tsFee;}

    public void setPayChannel(String payChannel)
    {
        this.payChannel = payChannel;
    }

    public String getPayChannel()
    {
        return payChannel;
    }
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }

    public String getPayStatus()
    {
        return payStatus;
    }
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
    }


    public BigDecimal getCurrMoney() {
        return currMoney;
    }

    public void setCurrMoney(BigDecimal currMoney) {
        this.currMoney = currMoney;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("isDeleted", getIsDeleted())
            .append("createdAt", getCreatedAt())
            .append("createdBy", getCreatedBy())
            .append("modifiedAt", getModifiedAt())
            .append("modifiedBy", getModifiedBy())
            .append("recordSn", getRecordSn())
            .append("orderSn", getOrderSn())
            .append("outTradeNo", getOutTradeNo())
            .append("fromUid", getFromUid())
            .append("toUid", getToUid())
            .append("tsType", getTsType())
            .append("tsMoney", getTsMoney())
            .append("tsCost", getTsCost())
            .append("tsFee", getTsFee())
            .append("payChannel", getPayChannel())
            .append("remark", getRemark())
            .append("payStatus", getPayStatus())
            .append("payTime", getPayTime())
            .toString();
    }


}
