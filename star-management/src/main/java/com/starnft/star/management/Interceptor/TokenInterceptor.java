package com.starnft.star.management.Interceptor;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.infrastructure.entity.sysuser.SysUser;
import com.starnft.star.management.annotation.Pass;
import com.starnft.star.management.constants.SessionConstants;
import com.starnft.star.management.exception.ExceptionAssert;
import com.starnft.star.management.model.dto.SysUserDto;
import com.starnft.star.management.service.SysUserService;
import com.starnft.star.management.utils.IpUtil;
import com.starnft.star.management.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

@Configuration//声明这是一个配置
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final static String REQUEST_ID = "requestId";
    private final static String USER_ID = "userId";

    @Resource
    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("########验证token#############：{}", request.getRequestURI());
        // 如果不是响应方法，静态资源直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String uuid = UUID.randomUUID().toString();
        log.info("put requestId ({}) to logger", uuid);
        MDC.put(REQUEST_ID, uuid);

        if (method.isAnnotationPresent(Pass.class)) {
            Pass annotation = method.getAnnotation(Pass.class);
            if (annotation.required()) {
                log.info("pass 无需拦截接口");
                return true;
            }
        }
        ExceptionAssert.assertCondition(!StpUtil.isLogin(), StarError.USER_NOT_LOGIN,"用户未登录");
        log.info("接口拦截校验，用户id:{}", StpUtil.getLoginId());
        final SaSession session = StpUtil.getSessionByLoginId(StpUtil.getLoginId());
        final Object user = session.get(SessionConstants.USER_INFO);
        SysUser sysUser = (SysUser) user;
        if (sysUser == null) {
            log.info("用户不存在");
            StpUtil.logout(StpUtil.getLoginId());
            throw new StarException(StarError.USER_NOT_EXISTS,"用户不存在");
        }
        if (sysUser.getIsDeleted()) {
            log.info("用户不存在");
            StpUtil.logout(StpUtil.getLoginId());
            throw new StarException(StarError.USER_NOT_EXISTS,"用户不存在");
        }
        if (sysUser.getStatus()) {
            log.info("用户已被禁用");
            throw new StarException(StarError.USER_NOT_EXISTS,"用户已被禁用");
        }
        final String ipAddr = IpUtil.getIpAddr(request);
        log.info("request id:{}, uid->{} ip:{}", uuid, StpUtil.getLoginIdAsInt(), ipAddr);
        MDC.put(USER_ID, "USER-ID".concat(StpUtil.getLoginIdAsInt()+""));
        SysUserDto userDto = SysUserDto.builder()
                                .id(sysUser.getId())
                                .status(sysUser.getStatus())
                                        .build();
        userDto.setToken(StpUtil.getTokenValue());
        UserContextHolder.setCurrUser(userDto);
        log.info("已登录");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.remove(REQUEST_ID);
        MDC.remove(USER_ID);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}