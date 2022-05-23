package com.starnft.star.domain.identify;

public enum IdentifyTypeEnums {
    sw_identify(1,"swIdentifyStrategy","首网实名验证策略"),
    tx_identify(2,"txIdentifyStrategy","腾讯实名验证策略");

    public String getStrategy() {
        return strategy;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getTypes() {
        return types;
    }

    IdentifyTypeEnums(Integer types, String strategy, String desc) {
        this.types = types;
        this.strategy = strategy;
        this.desc = desc;
    }

    private final String strategy;
    private final String desc;
    private final Integer types;
}
