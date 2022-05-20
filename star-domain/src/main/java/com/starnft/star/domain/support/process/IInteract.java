package com.starnft.star.domain.support.process;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.support.process.context.ConnContext;

import java.util.Map;
import java.util.function.Supplier;

public interface IInteract {

    /**
     * 执行接口调用
     *
     * @param context   请求串
     * @param urlParams Get请求参数 非get请求默认置空 （）-> null;
     * @return T 响应
     */
    String interact(ConnContext context, Supplier<Map<?, ?>> urlParams);

    /**
     * 获取解析类型
     *
     * @return [StarConstants.ProcessType]
     */
    StarConstants.ProcessType getSerSerializationType();
}
