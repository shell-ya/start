package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * 商品类型
 *
 * @author Harlan
 * @date 2022/06/02 11:00
 */
@Getter
public enum CommodityTypeEnum {
    /**
     * 藏品
     */
    WORKS(1, "藏品"),

    /**
     * 盲盒
     */
    BLIND_BOX(2, "盲盒");

    CommodityTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    private final Integer type;
    private final String name;


    public static CommodityTypeEnum getCommodityTypeEnum(Integer type) {
        for (CommodityTypeEnum val : values()) {
            if (val.getType().equals(type)) {
                return val;
            }
        }
        return CommodityTypeEnum.WORKS;
    }
}
