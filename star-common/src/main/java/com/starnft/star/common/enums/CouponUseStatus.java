package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
@Getter
public enum CouponUseStatus {

    ACTIVITY(1, "未使用"),
    Airdrop(2, "已使用"),
    ;

    private Integer type;
    private String name;

    CouponUseStatus(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
