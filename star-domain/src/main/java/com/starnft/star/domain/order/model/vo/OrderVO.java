package com.starnft.star.domain.order.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderVO implements Serializable {

    private Long userId;

    private String orderSn;

}
