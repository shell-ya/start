package com.starnft.star.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 系列对象 star_nft_series
 *
 * @author shellya
 * @date 2022-05-27
 */
public class StarNftSeries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 系列名称 */
    @Excel(name = "系列名称")
    private String seriesName;

    /** 系列类型(1-主题 2-盲盒) */
    @Excel(name = "系列类型(1-主题 2-盲盒)")
    private Integer seriesType;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date updateAt;

    /** 是否删除(0-未删除 1-已删除) */
    @Excel(name = "是否删除(0-未删除 1-已删除)")
    private Integer isDelete;

    /** 主题图片 */
    @Excel(name = "主题图片")
    private String seriesImages;

    /** 模型地址 */
    @Excel(name = "模型地址")
    private String seriesModels;

    /** 1 违法性 2已发行 3 发行结束 */
    @Excel(name = "1 违法性 2已发行 3 发行结束")
    private Long seriesStatus;

    /** 系列描述     */
    private String seriesDescrption;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public String getSeriesName()
    {
        return seriesName;
    }
    public void setSeriesType(Integer seriesType)
    {
        this.seriesType = seriesType;
    }

    public Integer getSeriesType()
    {
        return seriesType;
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
    public void setSeriesImages(String seriesImages)
    {
        this.seriesImages = seriesImages;
    }

    public String getSeriesImages()
    {
        return seriesImages;
    }
    public void setSeriesModels(String seriesModels)
    {
        this.seriesModels = seriesModels;
    }

    public String getSeriesModels()
    {
        return seriesModels;
    }
    public void setSeriesStatus(Long seriesStatus)
    {
        this.seriesStatus = seriesStatus;
    }

    public Long getSeriesStatus()
    {
        return seriesStatus;
    }

    public void setSeriesDescrption(String SeriesDescrption)
    {
        this.seriesDescrption = SeriesDescrption;
    }

    public String getSeriesDescrption()
    {
        return seriesDescrption;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("seriesName", getSeriesName())
            .append("seriesType", getSeriesType())
            .append("createAt", getCreateAt())
            .append("createBy", getCreateBy())
            .append("updateAt", getUpdateAt())
            .append("updateBy", getUpdateBy())
            .append("isDelete", getIsDelete())
            .append("seriesImages", getSeriesImages())
            .append("seriesModels", getSeriesModels())
            .append("seriesStatus", getSeriesStatus())
            .toString();
    }
}
