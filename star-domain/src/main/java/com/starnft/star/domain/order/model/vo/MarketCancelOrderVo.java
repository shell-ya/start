package com.starnft.star.domain.order.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Date 2022/6/19 10:55 PM
 * @Author ： shellya
 */
@Data
@Builder
public class MarketCancelOrderVo {

    private Long uid;
    private String orderSn;
}
