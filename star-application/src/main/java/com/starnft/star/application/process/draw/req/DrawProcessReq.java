package com.starnft.star.application.process.draw.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;


/**
 * @description: 抽奖请求
 */
@ApiModel("抽奖请求")
public class DrawProcessReq {

    /**
     * 用户ID
     */
    private String uId;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID 盲盒对应主题id", required = true)
    @NotBlank(message = "activityId不能为空")
    private Long activityId;

    /**
     * numberId
     */
    @ApiModelProperty(value = "numberId", required = true)
    private String numberId;
    /**
     * 抽奖消耗品ID
     */
    @ApiModelProperty(value = "抽奖消耗品ID 盲盒编号number", required = false)
    private String number;

    public DrawProcessReq() {
    }

    public DrawProcessReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
