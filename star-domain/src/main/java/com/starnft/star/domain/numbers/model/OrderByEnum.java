package com.starnft.star.domain.numbers.model;

public enum OrderByEnum {
    /**
     * 无
     */
    DEFAULT("", 0),
    /**
     * 价格
     */
    PRICE("price", 1),
    /**
     * 编号
     */
    NUMBER("theme_number", 2),
    /**
     * 发行时间
     */
    PUB_DATE("create_at", 3);

    OrderByEnum(String values, Integer types) {
        this.values = values;
        this.types = types;
    }

    private final String values;
    private final Integer types;

    public String getValues() {
        return this.values;
    }

    public Integer getTypes() {
        return this.types;
    }

    public static OrderByEnum getOrderBy(Integer number) {
        OrderByEnum result = OrderByEnum.DEFAULT;
        for (OrderByEnum value : values()) {
            if (value.getTypes().equals(number)) {
                result = value;
                break;
            }
        }
        return result;
    }
}
