package com.starnft.star.application.process.order.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel("订单状态")
public class OrderGrabStatus implements Serializable {

    private Long uid;

    @ApiModelProperty(value = "订单状态 0排队中 -1抢单失败 1抢单成功")
    private Integer status;

    @ApiModelProperty(value = "订单号")
    private String orderSn;

    @ApiModelProperty(value = "商品开始时间")
    private String creatTime;


}
