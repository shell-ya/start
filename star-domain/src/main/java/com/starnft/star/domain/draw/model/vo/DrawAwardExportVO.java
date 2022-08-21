package com.starnft.star.domain.draw.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("抽奖记录参数")
public class DrawAwardExportVO {

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private Long orderId;
    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    @ApiModelProperty(value = "发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）")
    private Integer grantType;
    /**
     * 发奖状态
     */
    @ApiModelProperty(value = "发奖状态")
    private Integer grantState;
    /**
     * 发奖ID
     */
    @ApiModelProperty(value = "发奖ID")
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
     * 奖品内容「文字描述、Key、码」
     */
    @ApiModelProperty(value = "奖品内容「文字描述、Key、码」")
    private String awardContent;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
