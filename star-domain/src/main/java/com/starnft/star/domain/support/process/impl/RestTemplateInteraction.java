package com.starnft.star.domain.support.process.impl;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.support.process.InteractBase;
import com.starnft.star.domain.support.process.context.ConnContext;
import com.starnft.star.domain.support.process.helper.RestTemplateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

@Component("restTemplateInteraction")
public class RestTemplateInteraction extends InteractBase<ResponseEntity<?>> {


    @Override
    protected ResponseEntity<?> doInteract(ConnContext context, Supplier<Map<?, ?>> urlParams) {
        switch (context.getRestMethod()) {
            case GET:
                return doGetWithParams(context, urlParams.get());
            case PUT:
                return doPut(context, context.getHttpHeaders());
            case DELETE:
                return doDelete();//TODO
            default:
                return doPost(context, context.getHttpHeaders());
        }
    }

    /**
     * GET请求
     *
     * @return ResponseEntity
     */
    private ResponseEntity<?> doGet(ConnContext context) {
        return RestTemplateHelper.simpleGet(new HttpHeaders(), context.getUrl());
    }

    /**
     * GET请求
     *
     * @return ResponseEntity
     */
    private ResponseEntity<?> doGetWithParams(ConnContext context, Map<?, ?> urlParams) {
        if (CollectionUtils.isEmpty(Collections.singleton(urlParams))) {
            return doGet(context);
        }
        return RestTemplateHelper.executeGetParam(new HttpHeaders(), context.getUrl(), (Map<String, String>) urlParams);
    }

    /**
     * POST请求
     *
     * @return ResponseEntity
     */
    private ResponseEntity<?> doPost(ConnContext context, HttpHeaders httpHeaders) {
        return RestTemplateHelper.executePostBodyParam(httpHeaders, context.getUrl(), context.getContent());
    }

    /**
     * PUT请求
     *
     * @return ResponseEntity
     */
    private ResponseEntity<?> doPut(ConnContext context, HttpHeaders httpHeaders) {
        return RestTemplateHelper.executePutBodyParam(httpHeaders, context.getUrl(), context.getContent());
    }

    /**
     * DELETE请求 TODO
     *
     * @return ResponseEntity
     */
    private ResponseEntity<?> doDelete() {
        return null;
    }


    @Override
    public StarConstants.ProcessType getSerSerializationType() {
        return StarConstants.ProcessType.JSON;
    }

}
