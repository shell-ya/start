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
 * 钱包变化记录对象 star_nft_wallet_log
 *
 * @author shellya
 * @date 2022-06-09
 */
public class StarNftWalletLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** isDeleted */
    @Excel(name = "isDeleted")
    private Long isDeleted;

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

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 钱包id */
    @Excel(name = "钱包id")
    private String wId;

    /** 变动金额 增+ 减- */
    @Excel(name = "变动金额 增+ 减-")
    private BigDecimal balanceOffset;

    /** 变动后的金额 */
    @Excel(name = "变动后的金额")
    private BigDecimal currentBalance;

    /** 是否显示 0不显示 1显示 */
    @Excel(name = "是否显示 0不显示 1显示")
    private Integer display;

    /** 渠道 */
    @Excel(name = "渠道")
    private String channel;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setIsDeleted(Long isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted()
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
    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Long getUid()
    {
        return uid;
    }
    public void setwId(String wId)
    {
        this.wId = wId;
    }

    public String getwId()
    {
        return wId;
    }
    public void setBalanceOffset(BigDecimal balanceOffset)
    {
        this.balanceOffset = balanceOffset;
    }

    public BigDecimal getBalanceOffset()
    {
        return balanceOffset;
    }
    public void setCurrentBalance(BigDecimal currentBalance)
    {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalance()
    {
        return currentBalance;
    }
    public void setDisplay(Integer display)
    {
        this.display = display;
    }

    public Integer getDisplay()
    {
        return display;
    }
    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getChannel()
    {
        return channel;
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
            .append("uid", getUid())
            .append("wId", getwId())
            .append("balanceOffset", getBalanceOffset())
            .append("currentBalance", getCurrentBalance())
            .append("remark", getRemark())
            .append("display", getDisplay())
            .append("channel", getChannel())
            .toString();
    }
}
