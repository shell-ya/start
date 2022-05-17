package com.starnft.star.interfaces.interceptor;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.user.service.impl.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author LaiWeiChun
 */
@Component
@Slf4j
public class StatInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    BaseUserService baseUserService;

    /**
     * pre sin controller
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //接口文档放行
        String requestURI = request.getRequestURI();
        if (requestURI.contains("swagger") || requestURI.contains("api-docs")) {
            return true;
        }

        //todo 签名验证

        //token验证
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<?> beanType = handlerMethod.getBeanType();
        TokenIgnore classAnnotation = beanType.getAnnotation(TokenIgnore.class);
        if (classAnnotation != null) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        TokenIgnore methodAnnotation = method.getAnnotation(TokenIgnore.class);
        if (methodAnnotation != null) {
            return true;
        }

        //校验token
        String token = request.getHeader(StarConstants.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new StarException(StarError.TOKEN_NOT_EXISTS_ERROR);
        } else {
            Long userId = baseUserService.checkTokenAndReturnUserId(token);
            reflectSetHeader(request, StarConstants.USER_ID, userId);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void reflectSetHeader(HttpServletRequest request, String key, Long value) {
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders) headers.get(o1);
            o2.removeHeader(key);
            o2.addValue(key).setLong(value);
        } catch (Exception e) {
            log.info("reflect set header error {}", e);
        }
    }
}
