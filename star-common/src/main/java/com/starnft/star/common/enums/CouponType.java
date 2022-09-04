package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
@Getter
public enum CouponType {

    PRIORITY_PURCHASE(1, "优先购"),
    SERVICE_CHARGE_DEDUCTION(2, "手续费抵扣"),
    DISCOUNT_ROLL(3, "打折"),
    PREFERENTIAL_AMOUNT(4, "优惠金额"),
    ;

    private Integer type;
    private String name;

    CouponType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
