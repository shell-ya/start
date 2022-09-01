package com.starnft.star.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包对象 nft_wallet
 *
 * @author shellya
 * @date 2022-06-08
 */
public class NftWallet extends BaseEntity
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
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 修改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedAt;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifiedBy;

    /** 钱包id */
    @Excel(name = "钱包id")
    private String wId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 余额 */
    @Excel(name = "余额")
    private BigDecimal balance;

    /** 钱包总收入 */
    @Excel(name = "钱包总收入")
    private BigDecimal walletIncome;

    /** 钱包总支出 */
    @Excel(name = "钱包总支出")
    private BigDecimal walletOutcome;

    /** 是否被冻结 */
    @Excel(name = "是否被冻结")
    private Integer frozen;

    /** 冻结金额 */
    @Excel(name = "冻结金额")
    private BigDecimal frozenFee;

    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;

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
    public void setwId(String wId)
    {
        this.wId = wId;
    }

    public String getwId()
    {
        return wId;
    }
    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Long getUid()
    {
        return uid;
    }
    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }
    public void setWalletIncome(BigDecimal walletIncome)
    {
        this.walletIncome = walletIncome;
    }

    public BigDecimal getWalletIncome()
    {
        return walletIncome;
    }
    public void setWalletOutcome(BigDecimal walletOutcome)
    {
        this.walletOutcome = walletOutcome;
    }

    public BigDecimal getWalletOutcome()
    {
        return walletOutcome;
    }
    public void setFrozen(Integer frozen)
    {
        this.frozen = frozen;
    }

    public Integer getFrozen()
    {
        return frozen;
    }
    public void setFrozenFee(BigDecimal frozenFee)
    {
        this.frozenFee = frozenFee;
    }

    public BigDecimal getFrozenFee()
    {
        return frozenFee;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
            .append("wId", getwId())
            .append("uid", getUid())
            .append("balance", getBalance())
            .append("walletIncome", getWalletIncome())
            .append("walletOutcome", getWalletOutcome())
            .append("frozen", getFrozen())
            .append("frozenFee", getFrozenFee())
            .toString();
    }
}
