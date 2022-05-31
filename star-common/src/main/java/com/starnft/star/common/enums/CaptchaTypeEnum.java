package com.starnft.star.common.enums;

import lombok.Getter;

/**
 * @author Harlan
 * @date 2022/05/30 23:31
 */
@Getter
@SuppressWarnings("all")
public enum CaptchaTypeEnum {

    SLIDER(1, "SLIDER", "滑块"),

    ROTATE(2, "ROTATE", "旋转"),

    CONCAT(3, "CONCAT", "滑动还原"),

    IMAGE_CLICK(4, "IMAGE_CLICK", "图片点选"),

    WORD_IMAGE_CLICK(5, "WORD_IMAGE_CLICK", "单词点选");

    private Integer code;
    private String type;
    private String desc;

    CaptchaTypeEnum(Integer code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static CaptchaTypeEnum getCaptchaTypeEnum(Integer code) {
        for (CaptchaTypeEnum c : CaptchaTypeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return CaptchaTypeEnum.SLIDER;
    }
}
