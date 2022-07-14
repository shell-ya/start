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
 * 积分数据对象 star_nft_scope_record
 *
 * @author shellya
 * @date 2022-06-25
 */
public class StarNftScopeRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /** 积分 */
    @Excel(name = "积分")
    private BigDecimal scope;

    /** 模型 */
    @Excel(name = "模型")
    private Long mold;

    /** 积分类型 */
    @Excel(name = "积分类型")
    private Long scopeType;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setScope(BigDecimal scope)
    {
        this.scope = scope;
    }

    public BigDecimal getScope()
    {
        return scope;
    }
    public void setMold(Long mold)
    {
        this.mold = mold;
    }

    public Long getMold()
    {
        return mold;
    }
    public void setScopeType(Long scopeType)
    {
        this.scopeType = scopeType;
    }

    public Long getScopeType()
    {
        return scopeType;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("scope", getScope())
            .append("mold", getMold())
            .append("scopeType", getScopeType())
            .append("remarks", getRemarks())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
