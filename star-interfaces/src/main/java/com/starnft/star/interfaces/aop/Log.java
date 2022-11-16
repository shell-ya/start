package com.starnft.star.interfaces.aop;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author fan.shen
 * @date 2022-09-28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface Log {

    /**
     * 描述
     *
     * @return
     */
    String title() default "";

    /**
     * 业务类型
     *
     * @return
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 是否保存请求的数据
     *
     * @return
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的数据
     *
     * @return
     */
    boolean isSaveResponseData() default true;

}
