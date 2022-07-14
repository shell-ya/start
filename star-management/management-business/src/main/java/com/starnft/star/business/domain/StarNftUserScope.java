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
 * 用户积分对象 star_nft_user_scope
 *
 * @author shellya
 * @date 2022-06-25
 */
public class StarNftUserScope extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /** 用户积分 */
    @Excel(name = "用户积分")
    private BigDecimal userScope;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedAt;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 积分类型 */
    @Excel(name = "积分类型")
    private Long scopeType;

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
    public void setUserScope(BigDecimal userScope)
    {
        this.userScope = userScope;
    }

    public BigDecimal getUserScope()
    {
        return userScope;
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
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setScopeType(Long scopeType)
    {
        this.scopeType = scopeType;
    }

    public Long getScopeType()
    {
        return scopeType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userScope", getUserScope())
            .append("createdAt", getCreatedAt())
            .append("modifiedAt", getModifiedAt())
            .append("version", getVersion())
            .append("scopeType", getScopeType())
            .toString();
    }
}
