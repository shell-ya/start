package com.starnft.star.common.enums;

import java.util.stream.Stream;

public enum PromoteTypeEnums {
    sms_promote(1,"smsPromoteStrategy","短信发送策略"),
    email_message(2,"emailPromoteStrategy","邮箱发送策略"),
    un_found_message(-1,"","未知错误");
    private final String strategy;
    private final String desc;
    private final Integer types;
    public String getStrategy() {
        return strategy;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getTypes() {
        return types;
    }

    PromoteTypeEnums(Integer types, String strategy, String desc) {
        this.types = types;
        this.strategy = strategy;
        this.desc = desc;
    }
    public static PromoteTypeEnums getDefaultMessageType(Integer value) {
        return Stream
                .of(PromoteTypeEnums.values())
                .filter(item -> item.getTypes().equals(value))
                .findAny()
                .orElse(un_found_message);
    }
}
