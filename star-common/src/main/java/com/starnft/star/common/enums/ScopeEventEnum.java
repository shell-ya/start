package com.starnft.star.common.enums;

public enum ScopeEventEnum {
    market_exchange_buy(1,"市场交易购买获得积分"),
    market_exchange_sell(2,"市场交易出售获得积分");
    private final String  desc;
    private final Integer code;
    ScopeEventEnum(Integer code,String desc) {
        this.desc = desc;
        this.code = code;
    }
}
