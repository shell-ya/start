package com.starnft.star.application.process.order.white.rule;

import com.starnft.star.application.process.number.INumberCore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public abstract class AbstractWhiteRule implements IWhiteRule{

    @Resource
    protected INumberCore numberCore;

}
