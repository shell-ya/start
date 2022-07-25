package com.starnft.star.domain.prop.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PropsRecordVO {
    /**
     * user id
     */
    @ApiModelProperty(name = "user id", notes = "")
    private Long uid;
    /**
     * 道具id
     */
    @ApiModelProperty(name = "道具id", notes = "")
    private Long propId;
    /**
     * 道具订单号
     */
    @ApiModelProperty(name = "道具订单号", notes = "")
    private String orderSn;
    /**
     * 购买数量
     */
    @ApiModelProperty(name = "购买数量", notes = "")
    private Integer count;
    /**
     * 商品单价
     */
    @ApiModelProperty(name = "商品单价", notes = "")
    private BigDecimal unitCost;
    /**
     * 优惠价格
     */
    @ApiModelProperty(name = "优惠价格", notes = "")
    private BigDecimal discounts;
    /**
     * 优惠渠道
     */
    @ApiModelProperty(name = "优惠渠道", notes = "")
    private String discountsChannel;
    /**
     * 订单总价
     */
    @ApiModelProperty(name = "订单总价", notes = "")
    private BigDecimal orderCost;
    /**
     * 订单状态
     */
    @ApiModelProperty(name = "订单状态", notes = "")
    private Integer status;
}
