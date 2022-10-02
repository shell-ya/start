package com.starnft.star.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 用户策略计算结果对象 user_strategy_export
 *
 * @author ruoyi
 * @date 2022-09-29
 */
public class UserStrategyExport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String uId;

    /** 活动ID */
    @Excel(name = "活动ID")
    private Long activityId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 策略ID */
    @Excel(name = "策略ID")
    private Long strategyId;

    /** 策略方式（1:单项概率、2:总体概率） */
    @Excel(name = "策略方式", readConverterExp = "1=:单项概率、2:总体概率")
    private Long strategyMode;

    /** 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工） */
    @Excel(name = "发放奖品方式", readConverterExp = "1=:即时、2:定时[含活动结束]、3:人工")
    private Long grantType;

    /** 发奖时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发奖时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date grantDate;

    /** 发奖状态 */
    @Excel(name = "发奖状态")
    private Integer grantState;

    /** 发奖ID */
    @Excel(name = "发奖ID")
    private String awardId;

    /** 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品） */
    @Excel(name = "奖品类型", readConverterExp = "1=:文字描述、2:兑换码、3:优惠券、4:实物奖品")
    private Long awardType;

    /** 奖品名称 */
    @Excel(name = "奖品名称")
    private String awardName;

    /** 奖品内容「文字描述、Key、码」 */
    @Excel(name = "奖品内容「文字描述、Key、码」")
    private String awardContent;

    /** 防重ID */
    @Excel(name = "防重ID")
    private String uuid;

    /** 消息发送状态（0未发送、1发送成功、2发送失败） */
    @Excel(name = "消息发送状态", readConverterExp = "0=未发送、1发送成功、2发送失败")
    private Long mqState;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setuId(String uId)
    {
        this.uId = uId;
    }

    public String getuId()
    {
        return uId;
    }
    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public Long getActivityId()
    {
        return activityId;
    }
    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }
    public void setStrategyId(Long strategyId)
    {
        this.strategyId = strategyId;
    }

    public Long getStrategyId()
    {
        return strategyId;
    }
    public void setStrategyMode(Long strategyMode)
    {
        this.strategyMode = strategyMode;
    }

    public Long getStrategyMode()
    {
        return strategyMode;
    }
    public void setGrantType(Long grantType)
    {
        this.grantType = grantType;
    }

    public Long getGrantType()
    {
        return grantType;
    }
    public void setGrantDate(Date grantDate)
    {
        this.grantDate = grantDate;
    }

    public Date getGrantDate()
    {
        return grantDate;
    }
    public void setGrantState(Integer grantState)
    {
        this.grantState = grantState;
    }

    public Integer getGrantState()
    {
        return grantState;
    }
    public void setAwardId(String awardId)
    {
        this.awardId = awardId;
    }

    public String getAwardId()
    {
        return awardId;
    }
    public void setAwardType(Long awardType)
    {
        this.awardType = awardType;
    }

    public Long getAwardType()
    {
        return awardType;
    }
    public void setAwardName(String awardName)
    {
        this.awardName = awardName;
    }

    public String getAwardName()
    {
        return awardName;
    }
    public void setAwardContent(String awardContent)
    {
        this.awardContent = awardContent;
    }

    public String getAwardContent()
    {
        return awardContent;
    }
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setMqState(Long mqState)
    {
        this.mqState = mqState;
    }

    public Long getMqState()
    {
        return mqState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("uId", getuId())
            .append("activityId", getActivityId())
            .append("orderId", getOrderId())
            .append("strategyId", getStrategyId())
            .append("strategyMode", getStrategyMode())
            .append("grantType", getGrantType())
            .append("grantDate", getGrantDate())
            .append("grantState", getGrantState())
            .append("awardId", getAwardId())
            .append("awardType", getAwardType())
            .append("awardName", getAwardName())
            .append("awardContent", getAwardContent())
            .append("uuid", getUuid())
            .append("mqState", getMqState())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
