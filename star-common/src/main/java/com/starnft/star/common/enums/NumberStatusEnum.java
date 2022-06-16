package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * 商品状态
 *
 * @author Harlan
 * @date 2022/05/25 20:58
 */
@Getter
@SuppressWarnings("all")
public enum NumberStatusEnum {

    NOT_SOLD(0, "发行未出售"),

    SOLD(1, "发行已出售"),

    ON_CONSIGNMENT(2, "寄售中");
    
    private final Integer code;
    private final String desc;

    NumberStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
