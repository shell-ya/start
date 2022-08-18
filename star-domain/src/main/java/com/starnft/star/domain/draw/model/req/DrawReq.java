package com.starnft.star.domain.draw.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 抽奖请求对象
 */
@ApiModel("抽奖请求")
public class DrawReq {

    /**
     * 用户ID
     */
    private String uId;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id", required = true)
    private String activityId;

    /**
     * 策略ID
     */
    @ApiModelProperty(value = "策略ID")
    private Long strategyId;

    /**
     * 防重ID
     */
    @ApiModelProperty(value = "防重ID uuid", required = true)
    private String uuid;

    public DrawReq() {
    }

    public DrawReq(String uId, Long strategyId) {
        this.uId = uId;
        this.strategyId = strategyId;
    }

    public DrawReq(String uId, Long strategyId, String uuid) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.uuid = uuid;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
