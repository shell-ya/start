package com.starnft.star.common.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.starnft.star.common.component.DesensitizedSerialize;
import com.starnft.star.common.enums.SensitiveTypeEnum;

import java.lang.annotation.*;

/**
 * @Date 2022/6/18 10:00 PM
 * @Author ： shellya
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedSerialize.class)
public @interface Desensitized {

    /*脱敏类型(规则)*/
    SensitiveTypeEnum type();

    /*判断注解是否生效的方法*/
    String isEffectiveMethod() default "";
}
