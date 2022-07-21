package com.starnft.star.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户藏品对象 star_nft_user_theme
 *
 * @author ruoyi
 * @date 2022-07-20
 */
public class StarNftUserTheme
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 主题编号信息id */
    @Excel(name = "主题编号信息id")
    private Long seriesThemeId;

    /** 主题信息id */
    @Excel(name = "主题信息id")
    private Long seriesThemeInfoId;

    /** 状态(0-已购买 1-挂售中 2-已出售 ) */
    @Excel(name = "状态(0-已购买 1-挂售中 2-已出售 )")
    private Integer status;

    /** 拥有者 */
    @Excel(name = "拥有者")
    private String userId;

    /** 购买来源(1-藏品 2-盲盒) */
    @Excel(name = "购买来源(1-藏品 2-盲盒)")
    private Integer source;

    /** 税前价格 */
    @Excel(name = "税前价格")
    private BigDecimal preTaxPrice;

    /** 平台税 */
    @Excel(name = "平台税")
    private BigDecimal platformTax;

    /** 版权税 */
    @Excel(name = "版权税")
    private BigDecimal copyrightTax;

    /** 税后价格 */
    @Excel(name = "税后价格")
    private BigDecimal afterTaxPrice;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateAt;

    /** 是否删除(0-未删除 1-已删除) */
    @Excel(name = "是否删除(0-未删除 1-已删除)")
    private Integer isDelete;

    /** 系列id */
    @Excel(name = "系列id")
    private Long seriesId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSeriesThemeId(Long seriesThemeId)
    {
        this.seriesThemeId = seriesThemeId;
    }

    public Long getSeriesThemeId()
    {
        return seriesThemeId;
    }
    public void setSeriesThemeInfoId(Long seriesThemeInfoId)
    {
        this.seriesThemeInfoId = seriesThemeInfoId;
    }

    public Long getSeriesThemeInfoId()
    {
        return seriesThemeInfoId;
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
    public void setSource(Integer source)
    {
        this.source = source;
    }

    public Integer getSource()
    {
        return source;
    }
    public void setPreTaxPrice(BigDecimal preTaxPrice)
    {
        this.preTaxPrice = preTaxPrice;
    }

    public BigDecimal getPreTaxPrice()
    {
        return preTaxPrice;
    }
    public void setPlatformTax(BigDecimal platformTax)
    {
        this.platformTax = platformTax;
    }

    public BigDecimal getPlatformTax()
    {
        return platformTax;
    }
    public void setCopyrightTax(BigDecimal copyrightTax)
    {
        this.copyrightTax = copyrightTax;
    }

    public BigDecimal getCopyrightTax()
    {
        return copyrightTax;
    }
    public void setAfterTaxPrice(BigDecimal afterTaxPrice)
    {
        this.afterTaxPrice = afterTaxPrice;
    }

    public BigDecimal getAfterTaxPrice()
    {
        return afterTaxPrice;
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
    public void setSeriesId(Long seriesId)
    {
        this.seriesId = seriesId;
    }

    public Long getSeriesId()
    {
        return seriesId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("seriesThemeId", getSeriesThemeId())
            .append("seriesThemeInfoId", getSeriesThemeInfoId())
            .append("status", getStatus())
            .append("userId", getUserId())
            .append("source", getSource())
            .append("preTaxPrice", getPreTaxPrice())
            .append("platformTax", getPlatformTax())
            .append("copyrightTax", getCopyrightTax())
            .append("afterTaxPrice", getAfterTaxPrice())
            .append("createAt", getCreateAt())
            .append("updateAt", getUpdateAt())
            .append("isDelete", getIsDelete())
            .append("seriesId", getSeriesId())
            .toString();
    }
}
