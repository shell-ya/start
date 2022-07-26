package com.starnft.star.common.enums;

import lombok.Getter;

@Getter
public enum ComposeDrawLotteryStrategyEnums {
    UNKNOWN(0, "UNKNOWN", "未知"),

    SCALE_LOTTERY_STRATEGY(1, "composeDrawScaleLotteryStrategy", "比例策略"),

//    PHONE_CODE_LOGIN(2, "loginByVerificationCodeStrategy", "短信验证码登录"),
    ;

    private Integer code;
    private String strategy;
    private String desc;

    ComposeDrawLotteryStrategyEnums(Integer code, String strategy, String desc) {
        this.code = code;
        this.strategy = strategy;
        this.desc = desc;
    }

    public static ComposeDrawLotteryStrategyEnums getScaleLotteryStrategy(Integer code) {
        for (ComposeDrawLotteryStrategyEnums c : ComposeDrawLotteryStrategyEnums.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return ComposeDrawLotteryStrategyEnums.UNKNOWN;
    }
}
