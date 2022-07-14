package com.starnft.star.business.domain.vo;

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
 * 秒杀活动对象 star_schedule_seckill
 *
 * @author shellya
 * @date 2022-06-26
 */
public class StarScheduleSeckillVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** isDeleted */
    @Excel(name = "isDeleted")
    private Long isDeleted;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 修改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedAt;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifiedBy;

    /** 商家id */
    @Excel(name = "商家id")
    private Long sellerId;

    private String publisher;

    private String publisherPic;

    /** spu id */
    @Excel(name = "spu id")
    private Long spuId;

    private String themeName;

    private String themePic;

    /** sku id */
    @Excel(name = "sku id")
    private Long skuId;

    /** 库存 */
    @Excel(name = "库存")
    private Long stock;

    /** 秒杀商品数 */
    @Excel(name = "秒杀商品数")
    private Long goodsNum;

    /** 秒杀定价 */
    @Excel(name = "秒杀定价")
    private BigDecimal secCost;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String goodsDesc;

    /** 审核状态 0未审核 1审核通过 */
    @Excel(name = "审核状态 0未审核 1审核通过")
    private Long status;

    /** 乐观锁 */
    @Excel(name = "乐观锁")
    private Long version;

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
    public void setSellerId(Long sellerId)
    {
        this.sellerId = sellerId;
    }

    public Long getSellerId()
    {
        return sellerId;
    }
    public void setSpuId(Long spuId)
    {
        this.spuId = spuId;
    }

    public Long getSpuId()
    {
        return spuId;
    }
    public void setSkuId(Long skuId)
    {
        this.skuId = skuId;
    }

    public Long getSkuId()
    {
        return skuId;
    }
    public void setStock(Long stock)
    {
        this.stock = stock;
    }

    public Long getStock()
    {
        return stock;
    }
    public void setGoodsNum(Long goodsNum)
    {
        this.goodsNum = goodsNum;
    }

    public Long getGoodsNum()
    {
        return goodsNum;
    }
    public void setSecCost(BigDecimal secCost)
    {
        this.secCost = secCost;
    }

    public BigDecimal getSecCost()
    {
        return secCost;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setGoodsDesc(String goodsDesc)
    {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodsDesc()
    {
        return goodsDesc;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
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
            .append("sellerId", getSellerId())
            .append("spuId", getSpuId())
            .append("skuId", getSkuId())
            .append("stock", getStock())
            .append("goodsNum", getGoodsNum())
            .append("secCost", getSecCost())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("goodsDesc", getGoodsDesc())
            .append("status", getStatus())
            .append("version", getVersion())
            .toString();
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemePic() {
        return themePic;
    }

    public void setThemePic(String themePic) {
        this.themePic = themePic;
    }

    public String getPublisherPic() {
        return publisherPic;
    }

    public void setPublisherPic(String publisherPic) {
        this.publisherPic = publisherPic;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
