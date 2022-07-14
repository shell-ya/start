package com.starnft.star.common.constant;

import lombok.Getter;

/**
 * @author WeiChunLAI
 */

@Getter
public enum YesOrNoStatusEnum {

    UNKNOW(0, "未知"),
    YES(1, "是"),
    NO(2 ,"否");

    private Integer code;
    private String desc;

    YesOrNoStatusEnum(Integer code , String desc){
        this.code = code;
        this.desc = desc;
    }

}
