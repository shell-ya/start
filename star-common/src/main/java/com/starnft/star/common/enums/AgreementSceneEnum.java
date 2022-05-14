package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author WeiChunLAI
 */
@Getter
public enum AgreementSceneEnum {

    UNKNOWN(0,"未知协议"),
    AGREEMENT_SCENE_REGISTER(1 , "注册场景"),;

    private Integer code;

    private String desc;

    AgreementSceneEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static AgreementSceneEnum getCode(Integer code) {
        for (AgreementSceneEnum value : AgreementSceneEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return AgreementSceneEnum.UNKNOWN;
    }

}
