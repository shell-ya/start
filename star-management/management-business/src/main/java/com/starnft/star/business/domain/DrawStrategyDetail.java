package com.starnft.star.business.domain;

import java.math.BigDecimal;

import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 策略明细对象 draw_strategy_detail
 *
 * @author zz
 * @date 2022-09-30
 */
public class DrawStrategyDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 策略ID */
    @Excel(name = "策略ID")
    private Long strategyId;

    /** 奖品ID */
    @Excel(name = "奖品ID")
    private String awardId;

    /** 奖品描述 */
    @Excel(name = "奖品描述")
    private String awardName;

    /** 奖品库存 */
    @Excel(name = "奖品库存")
    private Long awardCount;

    /** 奖品剩余库存 */
    @Excel(name = "奖品剩余库存")
    private Long awardSurplusCount;

    /** 中奖概率 */
    @Excel(name = "中奖概率")
    private BigDecimal awardRate;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setStrategyId(Long strategyId)
    {
        this.strategyId = strategyId;
    }

    public Long getStrategyId()
    {
        return strategyId;
    }
    public void setAwardId(String awardId)
    {
        this.awardId = awardId;
    }

    public String getAwardId()
    {
        return awardId;
    }
    public void setAwardName(String awardName)
    {
        this.awardName = awardName;
    }

    public String getAwardName()
    {
        return awardName;
    }
    public void setAwardCount(Long awardCount)
    {
        this.awardCount = awardCount;
    }

    public Long getAwardCount()
    {
        return awardCount;
    }
    public void setAwardSurplusCount(Long awardSurplusCount)
    {
        this.awardSurplusCount = awardSurplusCount;
    }

    public Long getAwardSurplusCount()
    {
        return awardSurplusCount;
    }
    public void setAwardRate(BigDecimal awardRate)
    {
        this.awardRate = awardRate;
    }

    public BigDecimal getAwardRate()
    {
        return awardRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("strategyId", getStrategyId())
            .append("awardId", getAwardId())
            .append("awardName", getAwardName())
            .append("awardCount", getAwardCount())
            .append("awardSurplusCount", getAwardSurplusCount())
            .append("awardRate", getAwardRate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
