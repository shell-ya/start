package com.starnft.star.domain.order.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.order.model.vo.OrderVO;

import java.util.List;

public interface IOrderRepository {

    //下单
    boolean createOrder(OrderVO orderVO);

    //更新订单状态 若支付单号不为空则一并修改
    boolean updateOrder(Long uid, String orderSn, Integer modifiedStatus, String payNumber);

    //查询指定订单
    OrderVO queryOrderByCondition(Long uid, String orderSn);

    //查询用户下订单 可按状态查询 查全部 状态传null
    ResponsePageResult<OrderVO> queryOrders(Long uid, Integer status, int page, int size);

    OrderVO queryOrderDetails(Long id);

    //查询秒杀缓存订单
    OrderVO obtainSecKillOrder(Long uid, Long themeId);

    List<OrderVO> queryOrdersByUidNSpu(Long uid, Long themeId);

    List<OrderVO> queryAllSuccessByTheme(String themeInfoId);

    List<OrderVO> queryToPayOrder(Long userId);

    List<BuyNum> queryBuyNum();

    Integer queryUserBuyBox(String uid);
}
