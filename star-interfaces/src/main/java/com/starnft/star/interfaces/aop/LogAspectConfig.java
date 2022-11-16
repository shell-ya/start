package com.starnft.star.interfaces.aop;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志 Aspect 基类
 *
 * @author: fan.shen
 * @date: 2022-10-29
 */
@Slf4j
@Aspect
@Component
public class LogAspectConfig {

    /**
     * 开始时间
     */
    private Long startTime;

    @Pointcut("@annotation(com.starnft.star.interfaces.aop.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime = System.currentTimeMillis();
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            Log annotationLog = this.getAnnotationLog(joinPoint);
            if (annotationLog == null && !log.isDebugEnabled()) {
                return;
            }

            SysOperateLog operLog = new SysOperateLog();
            if (e == null) {
                operLog.setStatus(OperateStatusEnum.SUCCESS.getCode());
            } else {
                operLog.setStatus(OperateStatusEnum.FAIL.getCode());
                operLog.setErrorMsg(StrUtil.sub(e.getMessage(), 0, 2000));
            }

            this.setControllerMethodDescription(annotationLog, operLog);
            this.setRequestInfo(annotationLog, operLog, joinPoint);
            this.setResponseInfo(annotationLog, operLog, jsonResult);

            log.info("operateLog = {}", JSONUtil.toJsonStr(operLog));

        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param annotationLog
     * @param operLog
     */
    public void setControllerMethodDescription(Log annotationLog, SysOperateLog operLog) {
        if (annotationLog == null) {
            return;
        }

        // 设置action动作
        operLog.setBusinessType(annotationLog.businessType().getCode());
        // 设置标题
        operLog.setTitle(annotationLog.title());
    }

    /**
     * 设置操作人信息
     *
     * @param operLog
     */
    private void setRequestInfo(Log annotationLog, SysOperateLog operLog, JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        operLog.setRequestId(MDC.get("traceid"));
        operLog.setRequestUrl(request.getRequestURI());
        operLog.setRequestMethod(request.getMethod());

        // 设置请求参数
        if (annotationLog == null) {
            operLog.setRequestDataJson(joinPoint.getArgs());
        } else if (annotationLog.isSaveRequestData()) {
            operLog.setRequestDataJson(joinPoint.getArgs());
            operLog.setRequestData(StrUtil.sub(JSONUtil.toJsonStr(joinPoint.getArgs()), 0, 2000));
        }

        // 设置方法名称
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operLog.setClassMethod(StrFormatter.format("{}.{}", className, methodName));

        // 设置操作人ID
        // operLog.setOperId(Convert.toLong(request.getHeader(Constants.CURRENT_USER_ID)));

        // operLog.setOperIp(IPUtils.getIp(request));
        operLog.setOpTime(new Date());

        // 执行时间（毫秒）
        operLog.setConsumeTime((System.currentTimeMillis() - startTime));
    }

    private void setResponseInfo(Log annotationLog, SysOperateLog operLog, Object jsonResult) {
        if (jsonResult != null) {
            if (annotationLog == null) {
                // 不记录返回值
            } else if (annotationLog.isSaveResponseData()) {
                operLog.setResponseData(StrUtil.sub(JSONUtil.toJsonStr(jsonResult), 0, 2000));
            }
        }
    }

}
