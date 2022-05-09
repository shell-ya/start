package com.starnft.star.common.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author WeiChunLAI
 */
@Getter
public enum LoginStatus {

    LOGINED(1,"已登录"),
    NOT_LOGINED(2,"未登录"),;

    LoginStatus(Integer code,String des){
        this.code = code;
        this.des = des;
    }

    private Integer code;
    private String des;

}
