package com.starnft.star.domain.support.process.impl;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.support.process.InteractBase;
import com.starnft.star.domain.support.process.context.ConnContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
public class SandPayHttpInteraction extends InteractBase {
    @Override
    public StarConstants.ProcessType getSerSerializationType() {
        return null;
    }

    @Override
    protected String doInteract(ConnContext context, Supplier<Map<?, ?>> urlParams) {
        return null;
    }
}
