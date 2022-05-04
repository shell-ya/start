package com.starnft.star.infrastructure.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面日志打印
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.starnft.star.infrastructure.*.*(..))")
    private void controllerAspect() {
    }


    /**
     * 请求入口
     * @param joinPoint
     */
    @Before(value = "controllerAspect()")
    public void beforeHandle(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            log.info("=========================REQUEST CONTENT START=========================");
            try {
                log.info("request method and url:{} , {}", request.getMethod(), request.getRequestURL().toString());
                log.info("request header:{}", JSON.toJSONString(getRequestHeaderMap(request)));
                log.info("request param:{}", Arrays.toString(joinPoint.getArgs()));
                log.info("类路径,方法名 :{},{} ", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            } catch (Exception e) {
                log.error("beforeHandle", e);
            }
            log.info("=========================REQUEST CONTENT END===========================");
        }

    }

    private Object getRequestHeaderMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            // 排除Cookie字段
            if (key.equalsIgnoreCase("Cookie")) {
                continue;
            }
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 请求出口
     * @param response
     */
    @AfterReturning(returning = "response", value = "controllerAspect()")
    public void afterHandle(Object response) {
        if (response != null) {
            log.info("=========================RESPONSE CONTENT START========================");
            log.info("response content is:{}", JSONObject.toJSONString(response));
            log.info("=========================RESPONSE CONTENT END==========================");
        }

    }

}