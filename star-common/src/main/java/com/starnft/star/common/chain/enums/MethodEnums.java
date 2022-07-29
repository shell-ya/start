package com.starnft.star.common.chain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum MethodEnums {
    transferFrom("transferFrom", "交易"),
    burn("burn", "销毁"),
    mint("mint", "发行");
    @JsonValue
    private final String name;
    private final String desc;


    MethodEnums(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
