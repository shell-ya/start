package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * 数据排序枚举
 *
 * @author Harlan
 * @date 2022/05/24 17:45
 */
@Getter
public enum SortTypeEnum {

    /**
     * 默认
     */
    DEFAULT(0, ""),

    /**
     * 升序
     */
    ASC(1, "ASC"),

    /**
     * 降序
     */
    DESC(2, "DESC");

    private final Integer code;
    private final String value;

    SortTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static SortTypeEnum getSortTypeEnum(Integer code) {
        for (SortTypeEnum c : SortTypeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return SortTypeEnum.DEFAULT;
    }
}
