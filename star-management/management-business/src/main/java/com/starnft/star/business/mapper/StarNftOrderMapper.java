package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftOrder;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author shellya
 * @date 2022-05-28
 */
public interface StarNftOrderMapper
{
    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    public StarNftOrder selectStarNftOrderById(Long id);

    /**
     * 查询订单列表
     *
     * @param starNftOrder 订单
     * @return 订单集合
     */
    public List<StarNftOrder> selectStarNftOrderList(StarNftOrder starNftOrder);

    /**
     * 新增订单
     *
     * @param starNftOrder 订单
     * @return 结果
     */
    public int insertStarNftOrder(StarNftOrder starNftOrder);

    /**
     * 修改订单
     *
     * @param starNftOrder 订单
     * @return 结果
     */
    public int updateStarNftOrder(StarNftOrder starNftOrder);

    /**
     * 删除订单
     *
     * @param id 订单主键
     * @return 结果
     */
    public int deleteStarNftOrderById(Long id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftOrderByIds(Long[] ids);

    public List<StarNftOrder>  dayOrder();

    public List<StarNftOrder>  toDayOrder();

    public List<StarNftOrder> queryOrderBuSn(String orderSn);

    List<StarNftOrder> queryStarNftOrder(String orderSn);
}
