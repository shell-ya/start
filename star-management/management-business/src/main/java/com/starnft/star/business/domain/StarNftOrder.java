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
 * 订单对象 star_nft_order
 *
 * @author shellya
 * @date 2022-05-28
 */
public class StarNftOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderSn;

    /** 主题信息 */
    @Excel(name = "主题信息")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesThemeInfoId;

    /** 主题编号信息id */
    @Excel(name = "主题编号信息id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesThemeId;

    /** 系列id */
    @Excel(name = "系列id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesId;

    /** 系列名称 */
    @Excel(name = "系列名称")
    private String seriesName;

    /** 主题名称 */
    @Excel(name = "主题名称")
    private String themeName;

    /** 主题图片 */
    @Excel(name = "主题图片")
    private String themePic;

    /** 主题类型：1-藏品 2-盲盒 */
    @Excel(name = "主题类型：1-藏品 2-盲盒")
    private Integer themeType;

    /** 状态(0-进行中 1-已完成) */
    @Excel(name = "状态(0-进行中 1-已完成)")
    private Integer status;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private BigDecimal totalAmount;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal payAmount;

    /** 支付单编号 */
    @Excel(name = "支付单编号")
    private String payNumber;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date modifiedAt;

    /** 更新人 */
    @Excel(name = "更新人")
    private String modifiedBy;

    /** 是否删除(0-未删除 1-已删除) */
    @Excel(name = "是否删除(0-未删除 1-已删除)")
    private Integer isDeleted;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderSn(String orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getOrderSn()
    {
        return orderSn;
    }
    public void setSeriesThemeInfoId(Long seriesThemeInfoId)
    {
        this.seriesThemeInfoId = seriesThemeInfoId;
    }

    public Long getSeriesThemeInfoId()
    {
        return seriesThemeInfoId;
    }
    public void setSeriesThemeId(Long seriesThemeId)
    {
        this.seriesThemeId = seriesThemeId;
    }

    public Long getSeriesThemeId()
    {
        return seriesThemeId;
    }
    public void setSeriesId(Long seriesId)
    {
        this.seriesId = seriesId;
    }

    public Long getSeriesId()
    {
        return seriesId;
    }
    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public String getSeriesName()
    {
        return seriesName;
    }
    public void setThemeName(String themeName)
    {
        this.themeName = themeName;
    }

    public String getThemeName()
    {
        return themeName;
    }
    public void setThemePic(String themePic)
    {
        this.themePic = themePic;
    }

    public String getThemePic()
    {
        return themePic;
    }
    public void setThemeType(Integer themeType)
    {
        this.themeType = themeType;
    }

    public Integer getThemeType()
    {
        return themeType;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }
    public void setPayAmount(BigDecimal payAmount)
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount()
    {
        return payAmount;
    }
    public void setPayNumber(String payNumber)
    {
        this.payNumber = payNumber;
    }

    public String getPayNumber()
    {
        return payNumber;
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
    public void setIsDeleted(Integer isDelete)
    {
        this.isDeleted = isDelete;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderSn", getOrderSn())
            .append("seriesThemeInfoId", getSeriesThemeInfoId())
            .append("seriesThemeId", getSeriesThemeId())
            .append("seriesId", getSeriesId())
            .append("seriesName", getSeriesName())
            .append("themeName", getThemeName())
            .append("themePic", getThemePic())
            .append("themeType", getThemeType())
            .append("status", getStatus())
            .append("userId", getUserId())
            .append("totalAmount", getTotalAmount())
            .append("payAmount", getPayAmount())
            .append("payNumber", getPayNumber())
            .append("createdAt", getCreatedAt())
            .append("createdBy", getCreatedBy())
            .append("modifiedAt", getModifiedAt())
            .append("modifiedBy", getModifiedBy())
            .append("isDelete", getIsDeleted())
            .toString();
    }
}
