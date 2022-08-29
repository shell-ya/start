package com.starnft.star.application.process.order.model.dto;

import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderMessageReq implements Serializable {

    private Long userId;

    private String time;

    private Integer isPriority;

    private SecKillGoods goods;

    public OrderMessageReq(Long userId, String time, Integer isPriority, SecKillGoods goods) {
        this.userId = userId;
        this.time = time;
        this.isPriority = isPriority;
        this.goods = goods;
    }
}
