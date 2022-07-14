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
 * 轮播图对象 star_banner
 *
 * @author shellya
 * @date 2022-06-10
 */
public class StarBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgUrl;

    /** 展示位置：1头部; 2中间；3底部; */
    @Excel(name = "展示位置：1头部; 2中间；3底部;")
    private Long position;

    /** 是否展示：0，否；1，是 */
    @Excel(name = "是否展示：0，否；1，是")
    private Long display;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 外链地址 */
    @Excel(name = "外链地址")
    private String url;

    /** 是否已经删除：0，未删除；1，已删除 */
    @Excel(name = "是否已经删除：0，未删除；1，已删除")
    private Long isDeleted;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private String createdBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date modifiedAt;

    /** 更新用户 */
    @Excel(name = "更新用户")
    private String modifiedBy;

    /** 跳转类型（1:外链接。2内链接 ） */
    @Excel(name = "跳转类型", readConverterExp = "1=:外链接。2内链接")
    private Integer jumpType;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }
    public void setPosition(Long position)
    {
        this.position = position;
    }

    public Long getPosition()
    {
        return position;
    }
    public void setDisplay(Long display)
    {
        this.display = display;
    }

    public Long getDisplay()
    {
        return display;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
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
    public void setJumpType(Integer jumpType)
    {
        this.jumpType = jumpType;
    }

    public Integer getJumpType()
    {
        return jumpType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("imgUrl", getImgUrl())
            .append("position", getPosition())
            .append("display", getDisplay())
            .append("sort", getSort())
            .append("url", getUrl())
            .append("isDeleted", getIsDeleted())
            .append("createdAt", getCreatedAt())
            .append("createdBy", getCreatedBy())
            .append("modifiedAt", getModifiedAt())
            .append("modifiedBy", getModifiedBy())
            .append("jumpType", getJumpType())
            .toString();
    }
}
