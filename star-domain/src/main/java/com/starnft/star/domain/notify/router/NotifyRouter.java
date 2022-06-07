package com.starnft.star.domain.notify.router;

import com.starnft.star.common.enums.PlatformTypeEnum;
import com.starnft.star.domain.notify.handler.INotifyHandler;
import com.starnft.star.domain.payment.model.res.NotifyRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class NotifyRouter implements INotifyRouter, ApplicationContextAware {
    @Resource
    ApplicationContext context;

    Map<PlatformTypeEnum, INotifyHandler> handlerMap = new ConcurrentHashMap<>(16);

    @PostConstruct
    public void init() {
        Map<String, INotifyHandler> handlers = context.getBeansOfType(INotifyHandler.class);
        for (INotifyHandler value : handlers.values()) {
            handlerMap.put(value.getNotifyChannel(), value);
        }
    }

    @Override
    public NotifyRes doDistribute(String sign, HttpServletRequest request, HttpServletResponse response) {
        PlatformTypeEnum platforms = PlatformTypeEnum.getPlatforms(sign);
        INotifyHandler iNotifyHandler = handlerMap.get(platforms);
        NotifyRes result = iNotifyHandler.doNotify(request);

        return result;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
