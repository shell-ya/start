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
 * 排行榜对象 star_rank_config
 *
 * @author shellya
 * @date 2022-06-28
 */
public class StarRankConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 1:拉新 2 消费 3 持有 */
    @Excel(name = "1:拉新 2 消费 3 持有")
    private Long rankType;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 是否多余字段 */
    @Excel(name = "是否多余字段")
    private Integer isExtend;

    /** 排行榜名字 */
    @Excel(name = "排行榜名字")
    private String rankName;

    /** 排行榜说明 */
    @Excel(name = "排行榜说明")
    private String rankRemark;

    /** 是否有时间限制 */
    @Excel(name = "是否有时间限制")
    private Integer isTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedAt;

    /** 是否删除 */
    private Long isDeleted;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setRankType(Long rankType)
    {
        this.rankType = rankType;
    }

    public Long getRankType()
    {
        return rankType;
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
    public void setIsExtend(Integer isExtend)
    {
        this.isExtend = isExtend;
    }

    public Integer getIsExtend()
    {
        return isExtend;
    }
    public void setRankName(String rankName)
    {
        this.rankName = rankName;
    }

    public String getRankName()
    {
        return rankName;
    }
    public void setRankRemark(String rankRemark)
    {
        this.rankRemark = rankRemark;
    }

    public String getRankRemark()
    {
        return rankRemark;
    }
    public void setIsTime(Integer isTime)
    {
        this.isTime = isTime;
    }

    public Integer getIsTime()
    {
        return isTime;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }
    public void setModifiedAt(Date modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public Date getModifiedAt()
    {
        return modifiedAt;
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
            .append("rankType", getRankType())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("isExtend", getIsExtend())
            .append("rankName", getRankName())
            .append("rankRemark", getRankRemark())
            .append("isTime", getIsTime())
            .append("createdAt", getCreatedAt())
            .append("modifiedAt", getModifiedAt())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
