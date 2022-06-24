package com.starnft.star.domain.number.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("商品转移请求")
public class HandoverReq implements Serializable {

    @ApiModelProperty(value = "操作人uid")
    private Long uid;

    @ApiModelProperty(value = "from uid 被转移uid")
    private Long fromUid;

    @ApiModelProperty(value = "to uid 转移到uid")
    private Long toUid;

    @ApiModelProperty(value = "藏品id")
    private Long numberId;

    @ApiModelProperty(value = "系列id")
    private Long seriesId;

    @ApiModelProperty(value = "主题id")
    private Long themeId;

    @ApiModelProperty(value = "转移状态 0-发行未出售  1-发行已出售  2 挂售中")
    private Integer itemStatus;

    @ApiModelProperty(value = "藏品类型 1藏品 2盲盒")
    private Integer categoryType;

    @ApiModelProperty(value = "流转类型")
    private Integer type;

    @ApiModelProperty(value = "变化前价格")
    private BigDecimal preMoney;

    @ApiModelProperty(value = "变化后价格")
    private BigDecimal currMoney;

    @ApiModelProperty(value = "订单类型 ture 发行 、false 市场订单")
    private Boolean orderType;


}
