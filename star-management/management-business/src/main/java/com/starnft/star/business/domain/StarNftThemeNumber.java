package com.starnft.star.business.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
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
 * 主题编号对象 star_nft_theme_number
 *
 * @author shellya
 * @date 2022-06-03
 */
@ExcelIgnoreUnannotated
@ColumnWidth(16)
@HeadRowHeight(14)
@HeadFontStyle(fontHeightInPoints = 11)
public class StarNftThemeNumber extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 主题信息id */
//    @Excel(name = "主题信息id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesThemeInfoId;

    /** 主题编号 */
    @Excel(name = "主题编号")
//    @ExcelProperty(value = "主题编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long themeNumber;

    /** 合约地址 */
    @Excel(name = "合约地址")
    private String contractAddress;

    /** 链上标识 */
    @Excel(name = "链上标识")
    private String chainIdentification;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 状态(0-发行未出售  1-发行已出售  2 未挂售  3挂售中) */
    @Excel(name = "状态(0-发行未出售  1-发行已出售  2 未挂售  3挂售中)")
//            ,converter = ThemeNumberConverter.class)
    private Integer status;

    /** 拥有者 */
//    @Excel(name = "拥有者")
    private String ownerBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
//    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
//    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date updateAt;

    /** 是否删除(0:未删除 1:已删除) */
    @Excel(name = "是否删除(0:未删除 1:已删除)")
//            ,converter = DeleteConverter.class)
    private Integer isDelete;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSeriesThemeInfoId(Long seriesThemeInfoId)
    {
        this.seriesThemeInfoId = seriesThemeInfoId;
    }

    public Long getSeriesThemeInfoId()
    {
        return seriesThemeInfoId;
    }
    public void setThemeNumber(Long themeNumber)
    {
        this.themeNumber = themeNumber;
    }

    public Long getThemeNumber()
    {
        return themeNumber;
    }
    public void setContractAddress(String contractAddress)
    {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress()
    {
        return contractAddress;
    }
    public void setChainIdentification(String chainIdentification)
    {
        this.chainIdentification = chainIdentification;
    }

    public String getChainIdentification()
    {
        return chainIdentification;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setOwnerBy(String ownerBy)
    {
        this.ownerBy = ownerBy;
    }

    public String getOwnerBy()
    {
        return ownerBy;
    }
    public void setCreateAt(Date createAt)
    {
        this.createAt = createAt;
    }

    public Date getCreateAt()
    {
        return createAt;
    }
    public void setUpdateAt(Date updateAt)
    {
        this.updateAt = updateAt;
    }

    public Date getUpdateAt()
    {
        return updateAt;
    }
    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("seriesThemeInfoId", getSeriesThemeInfoId())
            .append("themeNumber", getThemeNumber())
            .append("contractAddress", getContractAddress())
            .append("chainIdentification", getChainIdentification())
            .append("price", getPrice())
            .append("status", getStatus())
            .append("ownerBy", getOwnerBy())
            .append("createAt", getCreateAt())
            .append("createBy", getCreateBy())
            .append("updateAt", getUpdateAt())
            .append("updateBy", getUpdateBy())
            .append("isDelete", getIsDelete())
            .toString();
    }
}
