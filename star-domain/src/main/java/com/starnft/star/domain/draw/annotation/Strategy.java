package com.starnft.star.domain.draw.annotation;

import com.starnft.star.common.constant.StarConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 抽奖策略模型注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Strategy {

    StarConstants.StrategyMode strategyMode();

}
