package com.starnft.star.domain.numbers.model;

public enum OrderByEnum {
    PRICE("price",1),
    Number("theme_number",2)

    ;

    OrderByEnum(String values, Integer types) {
        this.values = values;
        this.types = types;
    }

    private final String values;
    private final Integer types;

    public String getValues() {
        return values;
    }

    public Integer getTypes() {
        return types;
    }

    public static OrderByEnum getOrderBy(Integer number){
        OrderByEnum result=OrderByEnum.Number;
        for (OrderByEnum value : values()) {
            if (value.getTypes().equals(number)){
                result=value;
                break;
            }
        }
        return  result;
    }
}
