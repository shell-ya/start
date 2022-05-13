package com.starnft.star.domain.sms;

public enum MessageTypeEnums {
    sw_message(1,"swMessageStrategy","首网短信发送策略");

    public String getStrategy() {
        return strategy;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getTypes() {
        return types;
    }

    MessageTypeEnums(Integer types, String strategy, String desc) {
        this.types = types;
        this.strategy = strategy;
        this.desc = desc;
    }

    private final String strategy;
    private final String desc;
    private final Integer types;
}
