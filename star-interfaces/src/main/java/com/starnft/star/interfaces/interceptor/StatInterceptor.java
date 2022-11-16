package com.starnft.star.interfaces.interceptor;

import cn.hutool.core.util.IdUtil;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.service.impl.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author LaiWeiChun
 */
@Component
@Slf4j
public class StatInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    BaseUserService baseUserService;

    @Resource
    RedisUtil redisUtil;

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

        //如果有上层调用就用上层的ID
        String traceId = request.getHeader("traceId");
        if (traceId == null) {
            traceId = IdUtil.fastSimpleUUID();
        }
        MDC.put("traceId", traceId);

        //接口文档放行
        String requestURI = request.getRequestURI();
        if (requestURI.contains("swagger") || requestURI.contains("api-docs") || requestURI.contains("error")) {
            return true;
        }

        //todo 签名验证

        if (request.getMethod().equals(RequestMethod.POST)) {
            String postData = getPostData(request);
        }

        //token验证
        if (handler instanceof HandlerMethod) {
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
        } else {
            return true;
        }

        //校验token
        String token = request.getHeader(StarConstants.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new StarException(StarError.TOKEN_NOT_EXISTS_ERROR);
        } else {
            UserInfo userInfo = this.baseUserService.checkTokenAndReturnUserId(token);
            Boolean isBlack = redisUtil.getTemplate().opsForSet().isMember(RedisKey.BLACK_MEMBERS.getKey(), userInfo.getAccount());
            if (isBlack) {
                throw new StarException(StarError.SYSTEM_ERROR, "您由于非法操作被列入黑名单，请联系客服解决！");
            }
            UserContext.setUserResolverInfo(UserResolverInfo.builder().userId(userInfo.getAccount()).phone(userInfo.getPhone()).build());
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

        MDC.clear();
    }

    /**
     * If the parameter data was sent in the request body, such as occurs
     * with an HTTP POST request, then reading the body directly via
     *
     * @param request HttpServletRequest
     * @return String
     * @see javax.servlet.ServletRequest#getInputStream or
     * @see javax.servlet.ServletRequest#getReader
     */
    public static String getPostData(HttpServletRequest request) {
        StringBuilder data = new StringBuilder();
        String line;
        BufferedReader reader;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return data.toString();
    }
}
