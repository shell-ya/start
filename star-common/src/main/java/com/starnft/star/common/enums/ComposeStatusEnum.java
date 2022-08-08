package com.starnft.star.common.enums;

import lombok.Getter;

@Getter
public enum ComposeStatusEnum {
    Ready( 0, "未开启"),
    Running( 1, "开启"),
    Stop( 2, "已结束"),

    ;
    private final Integer code;
    private final String desc;

    ComposeStatusEnum( Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
