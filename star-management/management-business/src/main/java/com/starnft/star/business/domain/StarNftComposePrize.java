package com.starnft.star.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;

/**
 * 合成奖品对象 star_nft_compose_prize
 *
 * @author ruoyi
 * @date 2022-07-30
 */
public class StarNftComposePrize extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 合成id */
    @Excel(name = "合成id")
    private Long composeId;

    /** 奖励类型 */
    @Excel(name = "奖励类型")
    private Long prizeType;

    /** 奖励标记 */
    @Excel(name = "奖励标记")
    private String prizeStamp;

    /** 奖励几率 */
    @Excel(name = "奖励几率")
    private BigDecimal prizeProbability;

    /** 是否删除 */
    private Integer isDeleted;

    /** 奖品数量 */
    @Excel(name = "奖品数量")
    private BigDecimal prizeNumber;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedAt;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /** 当前奖品数量 */
    @Excel(name = "当前奖品数量")
    private BigDecimal currentPrizeNumber;

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
    public void setPrizeType(Long prizeType)
    {
        this.prizeType = prizeType;
    }

    public Long getPrizeType()
    {
        return prizeType;
    }
    public void setPrizeStamp(String prizeStamp)
    {
        this.prizeStamp = prizeStamp;
    }

    public String getPrizeStamp()
    {
        return prizeStamp;
    }
    public void setPrizeProbability(BigDecimal prizeProbability)
    {
        this.prizeProbability = prizeProbability;
    }

    public BigDecimal getPrizeProbability()
    {
        return prizeProbability;
    }
    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }
    public void setPrizeNumber(BigDecimal prizeNumber)
    {
        this.prizeNumber = prizeNumber;
    }

    public BigDecimal getPrizeNumber()
    {
        return prizeNumber;
    }
    public void setModifiedAt(Date modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public Date getModifiedAt()
    {
        return modifiedAt;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }
    public void setCurrentPrizeNumber(BigDecimal currentPrizeNumber)
    {
        this.currentPrizeNumber = currentPrizeNumber;
    }

    public BigDecimal getCurrentPrizeNumber()
    {
        return currentPrizeNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("composeId", getComposeId())
            .append("prizeType", getPrizeType())
            .append("prizeStamp", getPrizeStamp())
            .append("prizeProbability", getPrizeProbability())
            .append("isDeleted", getIsDeleted())
            .append("prizeNumber", getPrizeNumber())
            .append("modifiedAt", getModifiedAt())
            .append("createdAt", getCreatedAt())
            .append("currentPrizeNumber", getCurrentPrizeNumber())
            .toString();
    }
}
