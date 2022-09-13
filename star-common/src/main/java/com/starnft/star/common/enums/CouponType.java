package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
@Getter
public enum CouponType {

    /**
     * 优先购卡劵
     */
    PRIORITY_PURCHASE(1, "优先购" , "PP"),

    /**
     * 手续费抵扣
     */
    SERVICE_CHARGE_DEDUCTION(2, "手续费抵扣", "SCD"),

    /**
     * 打折
     */
    DISCOUNT_ROLL(3, "打折", "DR"),

    /**
     * 优惠金额
     */
    PREFERENTIAL_AMOUNT(4, "优惠金额", "PA"),

    ;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 卡劵类型名称
     */
    private String name;

    /**
     * 卡劵 id 前缀
     */
    private String prefix;

    CouponType(Integer type, String name, String prefix) {
        this.type = type;
        this.name = name;
        this.prefix = prefix;
    }
}
