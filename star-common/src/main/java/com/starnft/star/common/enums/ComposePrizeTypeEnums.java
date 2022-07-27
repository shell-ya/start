package com.starnft.star.common.enums;

import lombok.Getter;

@Getter
public enum ComposePrizeTypeEnums {
    UNKNOWN(0, "UNKNOWN", "未知"),
    ThemeComposePrizeStrategy(1, "themeComposePrizeStrategy", "主题合成策略"),
    ScopeComposePrizeStrategy(2, "scopeComposePrizeStrategy", "积分合成策略"),
    PropComposePrizeStrategy(3, "propComposePrizeStrategy", "道具合成策略"),;
    private Integer code;
    private String strategy;
    private String desc;

    ComposePrizeTypeEnums(Integer code, String strategy, String desc) {
        this.code = code;
        this.strategy = strategy;
        this.desc = desc;
    }

    public static ComposePrizeTypeEnums getComposePrizeType(Integer code) {
        for (ComposePrizeTypeEnums c : ComposePrizeTypeEnums.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return ComposePrizeTypeEnums.UNKNOWN;
    }
}
