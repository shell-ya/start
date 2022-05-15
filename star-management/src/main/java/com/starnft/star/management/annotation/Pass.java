package com.starnft.star.management.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = ElementType.METHOD)
@Order(-1)
public @interface Pass {
    boolean required() default true;
}
