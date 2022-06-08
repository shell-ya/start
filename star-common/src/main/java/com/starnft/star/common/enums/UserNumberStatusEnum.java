package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * 用户藏品状态
 *
 * @author Harlan
 * @date 2022/06/08 23:05
 */
@Getter
@SuppressWarnings("all")
public enum UserNumberStatusEnum {
    PURCHASED(0, "已购买"),

    ON_CONSIGNMENT(1, "寄售中"),

    SOLD(2, "已出售");

    private final Integer code;
    private final String desc;

    UserNumberStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
