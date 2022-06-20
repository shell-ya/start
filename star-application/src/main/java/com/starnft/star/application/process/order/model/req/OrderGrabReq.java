package com.starnft.star.application.process.order.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("秒杀下单请求")
public class OrderGrabReq implements Serializable {

    private Long userId;

    @ApiModelProperty(value = "商品秒杀开始时间时间戳 eg:2022061922 按该格式传")
    private String time;

    @ApiModelProperty(value = "商品主题ID")
    private Long themeId;

}
