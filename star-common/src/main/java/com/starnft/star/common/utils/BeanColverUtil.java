package com.starnft.star.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据库查询 转换 对外接口展示类
 *
 * @author lwc
 */
@Slf4j
public class BeanColverUtil {


    /**
     * 单个类转换
     *
     * @param sourceObj
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T colver(Object sourceObj, Class<T> targetClass) {
        if (sourceObj == null || targetClass == null) {
            return null;
        }
        T targetObj = null;
        try {
            targetObj = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("sourceObj:{},targetClass:{}", sourceObj, targetClass, e);
            return null;
        }
        BeanUtils.copyProperties(sourceObj, targetObj);
        return targetObj;
    }

    /**
     * List之间转换
     *
     * @param <T>
     * @param sourceList
     * @param targetClass
     * @return
     */
    public static <T> List<T> colverList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList) || targetClass == null) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(sourceObj -> colver(sourceObj, targetClass)).collect(Collectors.toList());
    }

}

