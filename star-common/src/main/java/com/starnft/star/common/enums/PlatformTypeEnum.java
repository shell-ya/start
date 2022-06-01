package com.starnft.star.common.enums;

public enum PlatformTypeEnum {
    sand_pay("sand_pay");
    private final String value;

    PlatformTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

 public static PlatformTypeEnum getPlatforms(String value) {
        for (PlatformTypeEnum platformTypeEnum : values()) {
            if (platformTypeEnum.getValue().equals(value)) {
                return platformTypeEnum;
            }
        }
        throw new RuntimeException("错误的平台标记");
    }
}
