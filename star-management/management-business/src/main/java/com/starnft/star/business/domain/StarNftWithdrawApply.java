package com.starnft.star.business.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现申请对象 star_nft_withdraw_apply
 *
 * @author shellya
 * @date 2022-05-28
 */
@ExcelIgnoreUnannotated
@ColumnWidth(16)
@HeadRowHeight(14)
@HeadFontStyle(fontHeightInPoints = 11)
public class StarNftWithdrawApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** isDeleted */
    @ExcelProperty(value ="isDeleted")
    private Long isDeleted;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;

    /** 创建人 */
    @ExcelProperty(value ="创建人")
    private String createdBy;

    /** 修改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @ExcelProperty(value ="修改日期")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:SS")
    private Date modifiedAt;

    /** 修改人 */
    @ExcelProperty(value ="修改人")
    private String modifiedBy;

    /** 申请提现uid */
    @ExcelProperty(value ="申请提现uid")
    @JsonSerialize(using = ToStringSerializer.class)
    @NumberFormat
    private Long withdrawUid;

    /** 提现金额 */
    @ExcelProperty(value ="提现金额")
    private BigDecimal withdrawMoney;

    /** 提现流水号 */
    @ExcelProperty(value ="提现流水号")
    private String withdrawTradeNo;

    /** 提现银行卡号 */
    @ExcelProperty(value ="提现银行卡号")
    @JsonSerialize(using = ToStringSerializer.class)
    @NumberFormat
    private Long withdrawBankNo;

    /** 持卡人姓名 */
    @ExcelProperty(value ="持卡人姓名")
    private String bankMatchName;

    /** 提现渠道 */
    @ExcelProperty(value ="提现渠道")
    private String withdrawChannel;

    /** 提现申请时间 */
    @ExcelProperty(value ="提现申请时间")
    private String applyTime;

    /** 提现申请通过时间 */
    @ExcelProperty(value ="提现申请通过时间")
    private String applyPassTime;

    /** 提现状态 */
    @ExcelProperty(value ="提现状态")
    private Long applyStatus;

    private String applyMsg;

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
    public void setWithdrawUid(Long withdrawUid)
    {
        this.withdrawUid = withdrawUid;
    }

    public Long getWithdrawUid()
    {
        return withdrawUid;
    }
    public void setWithdrawMoney(BigDecimal withdrawMoney)
    {
        this.withdrawMoney = withdrawMoney;
    }

    public BigDecimal getWithdrawMoney()
    {
        return withdrawMoney;
    }
    public void setWithdrawTradeNo(String withdrawTradeNo)
    {
        this.withdrawTradeNo = withdrawTradeNo;
    }

    public String getWithdrawTradeNo()
    {
        return withdrawTradeNo;
    }
    public void setWithdrawBankNo(Long withdrawBankNo)
    {
        this.withdrawBankNo = withdrawBankNo;
    }

    public Long getWithdrawBankNo()
    {
        return withdrawBankNo;
    }
    public void setBankMatchName(String bankMatchName)
    {
        this.bankMatchName = bankMatchName;
    }

    public String getBankMatchName()
    {
        return bankMatchName;
    }
    public void setWithdrawChannel(String withdrawChannel)
    {
        this.withdrawChannel = withdrawChannel;
    }

    public String getWithdrawChannel()
    {
        return withdrawChannel;
    }
    public void setApplyTime(String applyTime)
    {
        this.applyTime = applyTime;
    }

    public String getApplyTime()
    {
        return applyTime;
    }
    public void setApplyPassTime(String applyPassTime)
    {
        this.applyPassTime = applyPassTime;
    }

    public String getApplyPassTime()
    {
        return applyPassTime;
    }
    public void setApplyStatus(Long applyStatus)
    {
        this.applyStatus = applyStatus;
    }

    public Long getApplyStatus()
    {
        return applyStatus;
    }

    public void setApplyMsg(String applyMsg){
        this.applyMsg = applyMsg;
    }

    public String getApplyMsg(){
        return applyMsg;
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
            .append("withdrawUid", getWithdrawUid())
            .append("withdrawMoney", getWithdrawMoney())
            .append("withdrawTradeNo", getWithdrawTradeNo())
            .append("withdrawBankNo", getWithdrawBankNo())
            .append("bankMatchName", getBankMatchName())
            .append("withdrawChannel", getWithdrawChannel())
            .append("applyTime", getApplyTime())
            .append("applyPassTime", getApplyPassTime())
            .append("applyStatus", getApplyStatus())
                .append("applyMsg",getApplyMsg())
            .toString();
    }

}
