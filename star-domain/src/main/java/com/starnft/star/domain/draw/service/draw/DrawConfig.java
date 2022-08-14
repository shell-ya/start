package com.starnft.star.domain.draw.service.draw;

import com.starnft.star.domain.draw.annotation.Strategy;
import com.starnft.star.domain.draw.service.algorithm.IDrawAlgorithm;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 抽奖统一配置信息类
 */
public class DrawConfig {

    @Resource
    private List<IDrawAlgorithm> algorithmList;

    /**
     * 抽奖策略组
     */
    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        algorithmList.forEach(algorithm -> {
            Strategy strategy = AnnotationUtils.findAnnotation(algorithm.getClass(), Strategy.class);
            if (null != strategy) {
                drawAlgorithmGroup.put(strategy.strategyMode().getCode(), algorithm);
            }
        });
    }

}
