package com.starnft.star.application.process.number.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 市场订单请求类
 * @Date 2022/6/17 1:45 PM
 * @Author ： shellya
 */
@Data
@ApiModel
public class MarketOrderReq implements Serializable {

    @ApiModelProperty(value = "主题id")
    private Long numberId;
    private Long userId;
}
