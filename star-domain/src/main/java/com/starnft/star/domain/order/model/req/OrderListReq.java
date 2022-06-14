package com.starnft.star.domain.order.model.req;

import com.starnft.star.common.page.RequestPage;

import java.io.Serializable;

public class OrderListReq extends RequestPage implements Serializable {

    private Long userId;

    private Integer status;

    public OrderListReq(int page, int size, Long userId, Integer status) {
        super(page, size);
        this.userId = userId;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
