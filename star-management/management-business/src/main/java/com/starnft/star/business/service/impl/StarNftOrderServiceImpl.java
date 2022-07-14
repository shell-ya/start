package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftOrder;
import com.starnft.star.business.mapper.StarNftOrderMapper;
import com.starnft.star.business.service.IStarNftOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author shellya
 * @date 2022-05-28
 */
@Service
public class StarNftOrderServiceImpl implements IStarNftOrderService
{
    @Autowired
    private StarNftOrderMapper starNftOrderMapper;

    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public StarNftOrder selectStarNftOrderById(Long id)
    {
        return starNftOrderMapper.selectStarNftOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param starNftOrder 订单
     * @return 订单
     */
    @Override
    public List<StarNftOrder> selectStarNftOrderList(StarNftOrder starNftOrder)
    {
        return starNftOrderMapper.selectStarNftOrderList(starNftOrder);
    }

    /**
     * 新增订单
     *
     * @param starNftOrder 订单
     * @return 结果
     */
    @Override
    public int insertStarNftOrder(StarNftOrder starNftOrder)
    {
        return starNftOrderMapper.insertStarNftOrder(starNftOrder);
    }

    /**
     * 修改订单
     *
     * @param starNftOrder 订单
     * @return 结果
     */
    @Override
    public int updateStarNftOrder(StarNftOrder starNftOrder)
    {
        return starNftOrderMapper.updateStarNftOrder(starNftOrder);
    }

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteStarNftOrderByIds(Long[] ids)
    {
        return starNftOrderMapper.deleteStarNftOrderByIds(ids);
    }

    /**
     * 删除订单信息
     *
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteStarNftOrderById(Long id)
    {
        return starNftOrderMapper.deleteStarNftOrderById(id);
    }
}
