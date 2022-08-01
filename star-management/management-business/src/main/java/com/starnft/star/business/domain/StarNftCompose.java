package com.starnft.star.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;

/**
 * 合成活动对象 star_nft_compose
 *
 * @author ruoyi
 * @date 2022-07-30
 */
public class StarNftCompose extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 合成标题 */
    @Excel(name = "合成标题")
    private String composeName;

    /** 标题图片 */
    @Excel(name = "标题图片")
    private String composeImages;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startedAt;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endAt;

    /** 状态 */
    @Excel(name = "状态")
    private Long composeStatus;

    /** 描述 */
    @Excel(name = "描述")
    private String composeRemark;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long isDeleted;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setComposeName(String composeName)
    {
        this.composeName = composeName;
    }

    public String getComposeName()
    {
        return composeName;
    }
    public void setComposeImages(String composeImages)
    {
        this.composeImages = composeImages;
    }

    public String getComposeImages()
    {
        return composeImages;
    }
    public void setStartedAt(Date startedAt)
    {
        this.startedAt = startedAt;
    }

    public Date getStartedAt()
    {
        return startedAt;
    }
    public void setEndAt(Date endAt)
    {
        this.endAt = endAt;
    }

    public Date getEndAt()
    {
        return endAt;
    }
    public void setComposeStatus(Long composeStatus)
    {
        this.composeStatus = composeStatus;
    }

    public Long getComposeStatus()
    {
        return composeStatus;
    }
    public void setComposeRemark(String composeRemark)
    {
        this.composeRemark = composeRemark;
    }

    public String getComposeRemark()
    {
        return composeRemark;
    }
    public void setIsDeleted(Long isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted()
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("composeName", getComposeName())
            .append("composeImages", getComposeImages())
            .append("startedAt", getStartedAt())
            .append("endAt", getEndAt())
            .append("composeStatus", getComposeStatus())
            .append("composeRemark", getComposeRemark())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
