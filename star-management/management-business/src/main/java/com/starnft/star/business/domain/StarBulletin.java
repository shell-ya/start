package com.starnft.star.business.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import com.starnft.star.common.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 公告对象 star_bulletin
 *
 * @author shellya
 * @date 2022-07-25
 */
@TableName("star_bulletin")
public class StarBulletin
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer bulletinType;
    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 公告主图 */
    @Excel(name = "公告主图")
    private String picUrl;

    /** 公告内容 */
    @Xss
    private String content;

    /** 公告类型 */
    private Integer linkType;
    /** 外链地址 */
    private String linkUrl;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 是否删除 0 未删除 1 已删除 */
    @Excel(name = "是否删除 0 未删除 1 已删除")
    private Integer isDeleted;

    /** 创建时间 */
    private Date createdAt;

    /** 创建人 */
    private Long createdBy;

    /** 更新时间 */
    private Date modifiedAt;

    /** 更新人 */
    private Long modifiedBy;

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
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public String getPicUrl()
    {
        return picUrl;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
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
    public void setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy()
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
    public void setModifiedBy(Long modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    public Long getModifiedBy()
    {
        return modifiedBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("picUrl", getPicUrl())
            .append("content", getContent())
            .append("publishTime", getPublishTime())
            .append("isDeleted", getIsDeleted())
            .append("createdAt", getCreatedAt())
            .append("createdBy", getCreatedBy())
            .append("modifiedAt", getModifiedAt())
            .append("modifiedBy", getModifiedBy())
            .toString();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Integer getBulletinType() {
        return bulletinType;
    }

    public void setBulletinType(Integer bulletinType) {
        this.bulletinType = bulletinType;
    }


}
