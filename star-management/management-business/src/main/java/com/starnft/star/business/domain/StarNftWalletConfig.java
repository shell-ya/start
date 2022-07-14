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
 * 钱包规则配置对象 star_nft_wallet_config
 *
 * @author shellya
 * @date 2022-06-08
 */
public class StarNftWalletConfig extends BaseEntity
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

    /** 充值限额 */
    @Excel(name = "充值限额")
    private BigDecimal rechargeLimit;

    /** 每天提现次数限制 */
    @Excel(name = "每天提现次数限制")
    private Long withdrawTimes;

    /** 提现限额 */
    @Excel(name = "提现限额")
    private BigDecimal withdrawLimit;

    /** 手续费比例 */
    @Excel(name = "手续费比例")
    private BigDecimal chargeRate;

    /** 版权费比率 */
    @Excel(name = "版权费比率")
    private BigDecimal copyrightRate;

    /** 手续费比例 */
    @Excel(name = "手续费比例")
    private BigDecimal serviceRate;

    /** 固定手续费 */
    @Excel(name = "固定手续费")
    private BigDecimal stableRate;

    /** 所属身份 */
    @Excel(name = "所属身份")
    private String identityCode;

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
    public void setRechargeLimit(BigDecimal rechargeLimit)
    {
        this.rechargeLimit = rechargeLimit;
    }

    public BigDecimal getRechargeLimit()
    {
        return rechargeLimit;
    }
    public void setWithdrawTimes(Long withdrawTimes)
    {
        this.withdrawTimes = withdrawTimes;
    }

    public Long getWithdrawTimes()
    {
        return withdrawTimes;
    }
    public void setWithdrawLimit(BigDecimal withdrawLimit)
    {
        this.withdrawLimit = withdrawLimit;
    }

    public BigDecimal getWithdrawLimit()
    {
        return withdrawLimit;
    }
    public void setChargeRate(BigDecimal chargeRate)
    {
        this.chargeRate = chargeRate;
    }

    public BigDecimal getChargeRate()
    {
        return chargeRate;
    }
    public void setCopyrightRate(BigDecimal copyrightRate)
    {
        this.copyrightRate = copyrightRate;
    }

    public BigDecimal getCopyrightRate()
    {
        return copyrightRate;
    }
    public void setServiceRate(BigDecimal serviceRate)
    {
        this.serviceRate = serviceRate;
    }

    public BigDecimal getServiceRate()
    {
        return serviceRate;
    }
    public void setStableRate(BigDecimal stableRate)
    {
        this.stableRate = stableRate;
    }

    public BigDecimal getStableRate()
    {
        return stableRate;
    }
    public void setIdentityCode(String identityCode)
    {
        this.identityCode = identityCode;
    }

    public String getIdentityCode()
    {
        return identityCode;
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
            .append("rechargeLimit", getRechargeLimit())
            .append("withdrawTimes", getWithdrawTimes())
            .append("withdrawLimit", getWithdrawLimit())
            .append("chargeRate", getChargeRate())
            .append("copyrightRate", getCopyrightRate())
            .append("serviceRate", getServiceRate())
            .append("stableRate", getStableRate())
            .append("identityCode", getIdentityCode())
            .append("channel", getChannel())
            .toString();
    }
}
