package com.starnft.star.application.process.rebates.model;

import com.starnft.star.common.constant.StarConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Date 2022/7/29 8:56 PM
 * @Author ： shellya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebatesMessage {
    /** 用户id */
    private Long userId;
    /** 付款金额 */
    private BigDecimal payMoney;
    /** 订单类型 */
    private StarConstants.OrderType orderType;

}
