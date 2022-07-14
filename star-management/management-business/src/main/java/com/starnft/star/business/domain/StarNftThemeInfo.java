package com.starnft.star.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import com.starnft.star.common.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 主题对象 star_nft_theme_info
 *
 * @author shellya
 * @date 2022-06-03
 */
public class StarNftThemeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 系列id */
    @Excel(name = "系列id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesId;

    /** 主题类型(1-藏品 2-盲盒) */
    @Excel(name = "主题类型(1-藏品 2-盲盒)")
    private Integer themeType;

    /** 主题名称 */
    @Excel(name = "主题名称")
    private String themeName;

    /** 主题图片 */
    @Excel(name = "主题图片")
    private String themePic;

    /** 发行数量 */
    @Excel(name = "发行数量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long publishNumber;

    /** 主题描述 */
    @Excel(name = "主题描述")
    @Xss
    private String descrption;

    /** 主题级别 */
    @Excel(name = "主题级别")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long themeLevel;

    /** 库存 */
    @Excel(name = "库存")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long stock;

    /** 发行价格 */
    @Excel(name = "发行价格")
    private BigDecimal lssuePrice;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateAt;

    /** 是否删除(0:未删除 1:已删除) */
    @Excel(name = "是否删除(0:未删除 1:已删除)")
    private Integer isDelete;

    /** 是否推荐 */
    @Excel(name = "是否推荐")
    private Integer isRecommend;

    /** 限制数量 */
    @Excel(name = "限制数量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long restrictNumber;

    /** 标记 */
    @Excel(name = "标记")
    private String tags;

    /** 合约地址 */
    @Excel(name = "合约地址")
    private String contractAddress;

    /**
     * 发行商
     */
    private Long publisherId;

    /** 市场开放时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
//    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date marketOpenTime;

    /**
     * 可转售状态
     */
    private Integer isResale;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSeriesId(Long seriesId)
    {
        this.seriesId = seriesId;
    }

    public Long getSeriesId()
    {
        return seriesId;
    }
    public void setThemeType(Integer themeType)
    {
        this.themeType = themeType;
    }

    public Integer getThemeType()
    {
        return themeType;
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
    public void setPublishNumber(Long publishNumber)
    {
        this.publishNumber = publishNumber;
    }

    public Long getPublishNumber()
    {
        return publishNumber;
    }
    @Xss
    public void setDescrption(String descrption)
    {
        this.descrption = descrption;
    }

    public String getDescrption()
    {
        return descrption;
    }
    public void setThemeLevel(Long themeLevel)
    {
        this.themeLevel = themeLevel;
    }

    public Long getThemeLevel()
    {
        return themeLevel;
    }
    public void setStock(Long stock)
    {
        this.stock = stock;
    }

    public Long getStock()
    {
        return stock;
    }
    public void setLssuePrice(BigDecimal lssuePrice)
    {
        this.lssuePrice = lssuePrice;
    }

    public BigDecimal getLssuePrice()
    {
        return lssuePrice;
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
    public void setIsRecommend(Integer isRecommend)
    {
        this.isRecommend = isRecommend;
    }

    public Integer getIsRecommend()
    {
        return isRecommend;
    }
    public void setRestrictNumber(Long restrictNumber)
    {
        this.restrictNumber = restrictNumber;
    }

    public Long getRestrictNumber()
    {
        return restrictNumber;
    }
    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public String getTags()
    {
        return tags;
    }
    public void setContractAddress(String contractAddress)
    {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress()
    {
        return contractAddress;
    }



    public void setPublisherId(Long publisherId)
    {
        this.publisherId = publisherId;
    }

    public Long getPublisherId()
    {
        return publisherId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("seriesId", getSeriesId())
            .append("themeType", getThemeType())
            .append("themeName", getThemeName())
            .append("themePic", getThemePic())
            .append("publishNumber", getPublishNumber())
            .append("descrption", getDescrption())
            .append("themeLevel", getThemeLevel())
            .append("stock", getStock())
            .append("lssuePrice", getLssuePrice())
            .append("createAt", getCreateAt())
            .append("createBy", getCreateBy())
            .append("updateAt", getUpdateAt())
            .append("updateBy", getUpdateBy())
            .append("isDelete", getIsDelete())
            .append("isRecommend", getIsRecommend())
            .append("restrictNumber", getRestrictNumber())
            .append("tags", getTags())
            .append("contractAddress", getContractAddress())
            .append("publisherId", getPublisherId())
            .toString();
    }

    public Date getMarketOpenTime() {
        return marketOpenTime;
    }

    public void setMarketOpenTime(Date marketOpenTime) {
        this.marketOpenTime = marketOpenTime;
    }


    public Integer getIsResale() {
        return isResale;
    }

    public void setIsResale(Integer isResale) {
        this.isResale = isResale;
    }
}
