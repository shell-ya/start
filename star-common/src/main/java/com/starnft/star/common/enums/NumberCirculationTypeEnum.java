package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * 商品流转类型
 *
 * @author Harlan
 * @date 2022/05/26 16:47
 */
@Getter
@SuppressWarnings("all")
public enum NumberCirculationTypeEnum {
    CASTING(0, "铸造"),

    CONSIGNMENT(1, "寄售"),

    CANCEL_CONSIGNMENT(2, "取消寄售"),

    PURCHASE(3, "买入");

    private final Integer code;
    private final String desc;

    NumberCirculationTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
