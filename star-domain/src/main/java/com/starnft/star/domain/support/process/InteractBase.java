package com.starnft.star.domain.support.process;

import com.alibaba.fastjson.JSON;
import com.starnft.star.domain.support.process.assign.StarRequestMethod;
import com.starnft.star.domain.support.process.context.ConnContext;
import com.starnft.star.domain.support.process.res.RemoteRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Supplier;

public abstract class InteractBase implements IInteract {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String interact(ConnContext context, Supplier<Map<?, ?>> urlParams) {

        //Rest接口 Get请求参数传递
        if (null != context.getRestMethod() && context.getRestMethod() == StarRequestMethod.GET) {
            return doInteract(context, urlParams);
        }
        return doInteract(context, () -> null);

    }

    @Override
    public <T> T verifyResAndGet(RemoteRes remoteRes, Class<T> resClazz) {
        if (null == remoteRes) {
            throw new RuntimeException("接口响应为空");
        }
        if (remoteRes.getCode() == -1) {
            throw new RuntimeException(remoteRes.getMessage());
        }
        return JSON.parseObject(remoteRes.getContext(), resClazz);
    }

    protected abstract String doInteract(ConnContext context, Supplier<Map<?, ?>> urlParams);

}
