package com.starnft.star.domain.order.model.req;

import com.starnft.star.common.page.RequestPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("订单查询请求")
public class OrderListReq extends RequestPage implements Serializable {

    private Long userId;

    @ApiModelProperty(value = "订单号")
    private String orderSn;

    @ApiModelProperty("状态(0-进行中 1-已完成 2-已取消)")
    private Integer status;

    public OrderListReq(int page, int size, Long userId, Integer status, String orderSn) {
        super(page, size);
        this.userId = userId;
        this.status = status;
        this.orderSn = orderSn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
