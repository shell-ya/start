package com.starnft.star.domain.sms;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SmsType {
    login(1, "sms:verification_code:"),
    password(2, "sms:password_code:"),
    bind(3, "sms:bind_code:")
    ;
    @JsonValue
    private final int value;
    private final String prefix;

    SmsType(int value, String prefix) {
        this.value = value;
        this.prefix = prefix;
    }

    public int getValue() {
        return value;
    }

    public String getPrefix() {
        return prefix;
    }
}
