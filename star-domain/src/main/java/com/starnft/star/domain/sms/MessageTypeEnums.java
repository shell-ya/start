package com.starnft.star.domain.sms;

import java.util.Optional;
import java.util.stream.Stream;

public enum MessageTypeEnums {
    sw_message(1,"swMessageStrategy","首网短信发送策略"),
    tx_message(2,"txMessageStrategy","腾讯短信发送策略");
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

    MessageTypeEnums(Integer types, String strategy, String desc) {
        this.types = types;
        this.strategy = strategy;
        this.desc = desc;
    }
 public static MessageTypeEnums getDefaultMessageType(Integer value) {
     Optional<MessageTypeEnums> any = Stream.of(MessageTypeEnums.values()).filter(item -> item.getTypes().equals(value)).findAny();
   return   any.get();
 }
}
