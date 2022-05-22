package com.starnft.star.interfaces.interceptor;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.user.service.impl.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author LaiWeiChun
 */
@Component
@Slf4j
public class StatInterceptor extends HandlerInterceptorAdapter {

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
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
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
            UserContext.setUserId(UserResolverInfo.builder().userId(userId).build());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        UserContext.removeUserId();
    }
}
