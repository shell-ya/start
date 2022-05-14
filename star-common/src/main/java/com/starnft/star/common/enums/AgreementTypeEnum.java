package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author WeiChunLAI
 */

@Getter
public enum AgreementTypeEnum {

    UNKNOWN(0,"未知协议"),
    TYPE_AGREEMENT_PRIVACY(1 , "隐私协议"),
    TYPE_AGREEMENT_SERVICE(2 ,"服务协议");

    private Integer code;

    private String desc;

    AgreementTypeEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static AgreementTypeEnum getCode(Integer code) {
        for (AgreementTypeEnum value : AgreementTypeEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return AgreementTypeEnum.UNKNOWN;
    }
}
