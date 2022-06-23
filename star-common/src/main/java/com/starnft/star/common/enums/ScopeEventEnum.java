package com.starnft.star.common.enums;

public enum ScopeEventEnum {
    UNKNOWN(-1,"错误标记"),
    market_exchange_buy(1,"市场交易购买获得积分"),
    market_exchange_sell(2,"市场交易出售获得积分");
    private final String  desc;
    private final Integer code;
    ScopeEventEnum(Integer code,String desc) {
        this.desc = desc;
        this.code = code;
    }

    public static ScopeEventEnum getEvent(Integer value) {
        for (ScopeEventEnum scopeEventEnum : values()) {
            if (scopeEventEnum.getCode().equals(value)) {
                return scopeEventEnum;
            }
        }
        return ScopeEventEnum.UNKNOWN;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
