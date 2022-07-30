package com.starnft.star.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;

/**
 * 合成素材对象 star_nft_compose_category
 *
 * @author ruoyi
 * @date 2022-07-30
 */
public class StarNftComposeCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 合成id */
    @Excel(name = "合成id")
    private Long composeId;

    /** 合成材料 */
    @Excel(name = "合成材料")
    private String composeMaterial;

    /** 是否需要消耗积分 */
    @Excel(name = "是否需要消耗积分")
    private Integer isScore;

    /** 积分类型 */
    @Excel(name = "积分类型")
    private Long composeScopeType;

    /** 积分数量 */
    @Excel(name = "积分数量")
    private Long composeScopeNumber;

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
    public void setComposeId(Long composeId)
    {
        this.composeId = composeId;
    }

    public Long getComposeId()
    {
        return composeId;
    }
    public void setComposeMaterial(String composeMaterial)
    {
        this.composeMaterial = composeMaterial;
    }

    public String getComposeMaterial()
    {
        return composeMaterial;
    }
    public void setIsScore(Integer isScore)
    {
        this.isScore = isScore;
    }

    public Integer getIsScore()
    {
        return isScore;
    }
    public void setComposeScopeType(Long composeScopeType)
    {
        this.composeScopeType = composeScopeType;
    }

    public Long getComposeScopeType()
    {
        return composeScopeType;
    }
    public void setComposeScopeNumber(Long composeScopeNumber)
    {
        this.composeScopeNumber = composeScopeNumber;
    }

    public Long getComposeScopeNumber()
    {
        return composeScopeNumber;
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
            .append("composeId", getComposeId())
            .append("composeMaterial", getComposeMaterial())
            .append("isScore", getIsScore())
            .append("composeScopeType", getComposeScopeType())
            .append("composeScopeNumber", getComposeScopeNumber())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
