package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
@Getter
public enum CouponGetType {

    ACTIVITY(1, "活动"),
    AIRDROP(2, "空投"),
    ;

    private Integer type;
    private String name;

    CouponGetType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
