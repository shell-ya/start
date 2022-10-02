package com.starnft.star.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 活动配置对象 draw_activity
 *
 * @author zz
 * @date 2022-09-28
 */
public class DrawActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String activityName;

    /** 活动描述 */
    @Excel(name = "活动描述")
    private String activityDesc;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginDateTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDateTime;

    /** 库存 */
    @Excel(name = "库存")
    private Long stockCount;

    /** 库存剩余 */
    @Excel(name = "库存剩余")
    private Long stockSurplusCount;

    /** 每人可参与次数 */
    @Excel(name = "每人可参与次数")
    private Long takeCount;

    /** 抽奖策略ID */
    @Excel(name = "抽奖策略ID")
    private Long strategyId;

    /** 活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启 */
    @Excel(name = "活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启")
    private Long state;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 消耗品 */
    @Excel(name = "消耗品")
    private Long consumables;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public Long getActivityId()
    {
        return activityId;
    }
    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getActivityName()
    {
        return activityName;
    }
    public void setActivityDesc(String activityDesc)
    {
        this.activityDesc = activityDesc;
    }

    public String getActivityDesc()
    {
        return activityDesc;
    }
    public void setBeginDateTime(Date beginDateTime)
    {
        this.beginDateTime = beginDateTime;
    }

    public Date getBeginDateTime()
    {
        return beginDateTime;
    }
    public void setEndDateTime(Date endDateTime)
    {
        this.endDateTime = endDateTime;
    }

    public Date getEndDateTime()
    {
        return endDateTime;
    }
    public void setStockCount(Long stockCount)
    {
        this.stockCount = stockCount;
    }

    public Long getStockCount()
    {
        return stockCount;
    }
    public void setStockSurplusCount(Long stockSurplusCount)
    {
        this.stockSurplusCount = stockSurplusCount;
    }

    public Long getStockSurplusCount()
    {
        return stockSurplusCount;
    }
    public void setTakeCount(Long takeCount)
    {
        this.takeCount = takeCount;
    }

    public Long getTakeCount()
    {
        return takeCount;
    }
    public void setStrategyId(Long strategyId)
    {
        this.strategyId = strategyId;
    }

    public Long getStrategyId()
    {
        return strategyId;
    }
    public void setState(Long state)
    {
        this.state = state;
    }

    public Long getState()
    {
        return state;
    }
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }
    public void setConsumables(Long consumables)
    {
        this.consumables = consumables;
    }

    public Long getConsumables()
    {
        return consumables;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityId", getActivityId())
            .append("activityName", getActivityName())
            .append("activityDesc", getActivityDesc())
            .append("beginDateTime", getBeginDateTime())
            .append("endDateTime", getEndDateTime())
            .append("stockCount", getStockCount())
            .append("stockSurplusCount", getStockSurplusCount())
            .append("takeCount", getTakeCount())
            .append("strategyId", getStrategyId())
            .append("state", getState())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("consumables", getConsumables())
            .toString();
    }
}
