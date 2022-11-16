package com.starnft.star.interfaces.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作状态枚举
 *
 * @author: fan.shen
 * @date: 2022-09-29
 */
@Getter
@AllArgsConstructor
public enum OperateStatusEnum {

    SUCCESS(1, "操作成功"),
    FAIL(2, "操作失败"),
    ;

    private Integer code;
    private String name;
}
