package com.starnft.star.application.process.number.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2022/6/17 1:49 PM
 * @Author ： shellya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class MarketOrderRes {
    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    @ApiModelProperty(value = "订单信息")
    private String message;
    @ApiModelProperty(value = "锁单时间：单位（s）")
    private Long lockTimes;
}
