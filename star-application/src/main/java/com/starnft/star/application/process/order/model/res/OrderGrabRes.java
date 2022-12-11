package com.starnft.star.application.process.order.model.res;

import com.starnft.star.domain.order.model.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("抢单结果响应")
public class OrderGrabRes implements Serializable {

    //抢单状态
    @ApiModelProperty(value = "抢单状态 0 成功")
    private Integer status;

    @ApiModelProperty(value = "抢单消息")
    private String message;

    @ApiModelProperty(value = "下单结果")
    private OrderVO order;

    public OrderGrabRes(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
