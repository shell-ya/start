package com.starnft.star.common.enums;

import lombok.Getter;

@Getter
public enum PrizeEnum {
    UNKNOWN(0,  "未知"),
    THEME(1, "主题"),
    SCOPE(2, "积分"),
    PROP(3, "道具"),
    ;

    private Integer code;

    private String desc;

    PrizeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PrizeEnum getPrizeEnum(Integer code) {
        for (PrizeEnum c : PrizeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return PrizeEnum.UNKNOWN;
    }
}
