package com.starnft.star.infrastructure.mapper.draw;

import com.starnft.star.infrastructure.entity.draw.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 策略表DAO
 */
@Mapper
public interface IStrategyDao {

    /**
     * 查询策略配置
     *
     * @param strategyId 策略ID
     * @return           策略配置信息
     */
    Strategy queryStrategy(Long strategyId);

    /**
     * 插入策略配置
     *
     * @param req 策略配置
     */
    void insert(Strategy req);

}
