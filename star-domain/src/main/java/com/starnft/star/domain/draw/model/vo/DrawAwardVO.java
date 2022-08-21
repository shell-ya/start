package com.starnft.star.domain.draw.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description: 中奖奖品信息
 */
@ApiModel("抽奖结果")
public class DrawAwardVO {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String uId;

    /**
     * 奖品ID
     */
    @ApiModelProperty(value = "奖品id")
    private String awardId;

    /**
     * 奖品类型（1:藏品、2:兑换码、3:优惠券、4:实物奖品 5:道具 6:原石）
     */
    @ApiModelProperty(value = "奖品类型（1:藏品、2:兑换码、3:优惠券、4:实物奖品 5:道具 6:原石）")
    private Integer awardType;

    /**
     * 奖品名称
     */
    @ApiModelProperty(value = "奖品名称")
    private String awardName;

    /**
     * 奖品内容「描述、奖品码、sku」
     */
    @ApiModelProperty(value = "奖品内容「描述、奖品码、sku」")
    private String awardContent;


    /**
     * 奖品分类id
     */
    @ApiModelProperty(value = "奖品分类id ")
    private Long awardCategoryId;

    /**
     * 奖品数量
     */
    @ApiModelProperty(value = "奖品数量")
    private Integer awardCount;

    /**
     * 奖品图标
     */
    @ApiModelProperty(value = "奖品图标")
    private String awardPic;

    /**
     * 策略方式（1:单项概率、2:总体概率）
     */
    @ApiModelProperty(value = "策略方式")
    private Integer strategyMode;

    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    @ApiModelProperty(value = "发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）")
    private Integer grantType;
    /**
     * 发奖时间
     */
    @ApiModelProperty(value = "发奖时间")
    private Date grantDate;

    public DrawAwardVO() {
    }

    public DrawAwardVO(String uId, String awardId, Integer awardType, String awardName, Long awardCategoryId, Integer awardCount,  String awardPic, String awardContent) {
        this.uId = uId;
        this.awardId = awardId;
        this.awardType = awardType;
        this.awardCategoryId = awardCategoryId;
        this.awardCount = awardCount;
        this.awardPic = awardPic;
        this.awardName = awardName;
        this.awardContent = awardContent;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public DrawAwardVO(String uId) {
        this.uId = uId;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public Integer getAwardType() {
        return awardType;
    }

    public void setAwardType(Integer awardType) {
        this.awardType = awardType;
    }

    public Long getAwardCategoryId() {
        return awardCategoryId;
    }

    public void setAwardCategoryId(Long awardCategoryId) {
        this.awardCategoryId = awardCategoryId;
    }

    public Integer getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(Integer awardCount) {
        this.awardCount = awardCount;
    }

    public String getAwardPic() {
        return awardPic;
    }

    public void setAwardPic(String awardPic) {
        this.awardPic = awardPic;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardContent() {
        return awardContent;
    }

    public void setAwardContent(String awardContent) {
        this.awardContent = awardContent;
    }

    public Integer getStrategyMode() {
        return strategyMode;
    }

    public void setStrategyMode(Integer strategyMode) {
        this.strategyMode = strategyMode;
    }

    public Integer getGrantType() {
        return grantType;
    }

    public void setGrantType(Integer grantType) {
        this.grantType = grantType;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    @Override
    public String toString() {
        return "DrawAwardVO{" +
                "awardId='" + awardId + '\'' +
                ", awardType=" + awardType +
                ", awardName='" + awardName + '\'' +
                ", awardContent='" + awardContent + '\'' +
                ", strategyMode=" + strategyMode +
                ", grantType=" + grantType +
                ", grantDate=" + grantDate +
                '}';
    }
}
