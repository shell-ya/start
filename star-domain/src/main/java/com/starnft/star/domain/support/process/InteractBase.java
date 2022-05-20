package com.starnft.star.domain.support.process;

import com.starnft.star.domain.support.process.context.ConnContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.function.Supplier;

public abstract class InteractBase implements IInteract{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String interact(ConnContext context, Supplier<Map<?, ?>> urlParams) {

        //Rest接口 Get请求参数传递
        if (null != context.getRestMethod() && context.getRestMethod() == RequestMethod.GET) {
            return doInteract(context, urlParams);
        }
        return doInteract(context, () -> null);

    }

    protected abstract String doInteract(ConnContext context, Supplier<Map<?, ?>> urlParams);

}
