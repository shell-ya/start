package com.starnft.star.common.enums;

public enum ActivitiesConsumeEnum {

    GOODS(1, "blindBoxConsumeExec"),
    SCOPE(2, "scopeConsumeExec"),
    ;

    private Integer code;

    private String beanName;

    ActivitiesConsumeEnum(Integer code, String beanName) {
        this.code = code;
        this.beanName = beanName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
