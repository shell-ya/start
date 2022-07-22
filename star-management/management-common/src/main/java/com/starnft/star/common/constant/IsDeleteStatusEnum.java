package com.starnft.star.common.constant;

import lombok.Getter;

@Getter
public enum IsDeleteStatusEnum {

    UNKNOW(-1, "未知"),
    YES(1, "是"),
    NO(0 ,"否");

    private Integer code;
    private String desc;

    IsDeleteStatusEnum(Integer code , String desc){
        this.code = code;
        this.desc = desc;
    }
}
