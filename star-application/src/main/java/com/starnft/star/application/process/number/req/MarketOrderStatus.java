package com.starnft.star.application.process.number.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2022/6/17 6:04 PM
 * @Author ： shellya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketOrderStatus {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单流水号
     */
    private String orderSn;

}
