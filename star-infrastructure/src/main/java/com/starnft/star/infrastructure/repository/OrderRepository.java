package com.starnft.star.infrastructure.repository;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.repository.IOrderRepository;
import com.starnft.star.infrastructure.entity.order.StarNftOrder;
import com.starnft.star.infrastructure.mapper.order.StarNftOrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private StarNftOrderMapper starNftOrderMapper;

    @Resource
    private RedisUtil redisUtill;

    @Override
    public boolean createOrder(OrderVO orderVO) {
        StarNftOrder starNftOrder = new StarNftOrder();
        starNftOrder.setId(orderVO.getId());
        starNftOrder.setOrderSn(orderVO.getOrderSn());
        starNftOrder.setUserId(orderVO.getUserId());
        starNftOrder.setCreatedBy(orderVO.getUserId());
        starNftOrder.setCreatedAt(new Date());
        starNftOrder.setPayAmount(orderVO.getPayAmount());
        starNftOrder.setTotalAmount(orderVO.getTotalAmount());
        starNftOrder.setPayNumber("0"); //初始化为0
        starNftOrder.setSeriesId(orderVO.getSeriesId());
        starNftOrder.setSeriesName(orderVO.getSeriesName());
        starNftOrder.setSeriesThemeId(orderVO.getSeriesThemeId());
        starNftOrder.setSeriesThemeInfoId(orderVO.getSeriesThemeInfoId());
        starNftOrder.setStatus(0);
        starNftOrder.setThemeName(orderVO.getThemeName());
        starNftOrder.setThemePic(orderVO.getThemePic());
        starNftOrder.setThemeType(orderVO.getThemeType());
        starNftOrder.setThemeNumber(orderVO.getThemeNumber());
        starNftOrder.setIsDeleted(false);
        starNftOrder.setRemark(orderVO.getRemark());
        return starNftOrderMapper.insert(starNftOrder) == 1;
    }

    @Override
    public boolean updateOrder(Long uid, String orderSn, Integer modifiedStatus, String payNumber) {

        StarNftOrder starNftOrder = new StarNftOrder();
        //更新状态
        starNftOrder.setStatus(modifiedStatus);
        //会写支付单号
        if (!StringUtils.isNotBlank(payNumber)) {
            starNftOrder.setPayNumber(payNumber);
        }

        LambdaQueryWrapper<StarNftOrder> eq = new LambdaQueryWrapper<StarNftOrder>().eq(StarNftOrder::getUserId, uid)
                .eq(StarNftOrder::getOrderSn, orderSn);
        //更新
        return starNftOrderMapper.update(starNftOrder, eq) == 1;
    }

    public OrderVO queryOrderByCondition(Long uid, String orderSn) {
        StarNftOrder starNftOrder = queryOrder(uid, orderSn);
        return orderTransfer(starNftOrder);
    }

    private OrderVO orderTransfer(StarNftOrder starNftOrder) {
        return OrderVO.builder().orderSn(starNftOrder.getOrderSn()).status(starNftOrder.getStatus()).seriesThemeId(starNftOrder.getSeriesThemeId()).seriesId(starNftOrder.getSeriesId())
                .remark(starNftOrder.getRemark()).createdAt(starNftOrder.getCreatedAt()).seriesThemeInfoId(starNftOrder.getSeriesThemeInfoId()).seriesName(starNftOrder.getSeriesName())
                .themeName(starNftOrder.getThemeName()).themeType(starNftOrder.getThemeType()).themePic(starNftOrder.getThemePic()).themeNumber(starNftOrder.getThemeNumber())
                .payAmount(starNftOrder.getPayAmount()).totalAmount(starNftOrder.getTotalAmount()).userId(starNftOrder.getUserId()).build();
    }

    @Override
    public ResponsePageResult<OrderVO> queryOrders(Long uid, Integer status, int page, int size) {
        PageInfo<StarNftOrder> orders = PageHelper.startPage(page, size).doSelectPageInfo(
                () -> starNftOrderMapper.selectList(new LambdaQueryWrapper<StarNftOrder>()
                        .eq(Objects.nonNull(uid), StarNftOrder::getUserId, uid)
                        .eq(status != null, StarNftOrder::getStatus, status)));

        List<OrderVO> orderVOS = populateValues(orders.getList());

        return new ResponsePageResult<OrderVO>(orderVOS.stream().sorted(Comparator.comparing(OrderVO::getCreatedAt).reversed()).collect(Collectors.toList()), page, size, orders.getTotal());
    }

    private List<OrderVO> populateValues(List<StarNftOrder> list) {
        List<OrderVO> orderVOList = Lists.newArrayList();
        for (StarNftOrder starNftOrder : list) {
            orderVOList.add(populate(starNftOrder));
        }
        return orderVOList;
    }

    private OrderVO populate(StarNftOrder starNftOrder) {
        return OrderVO.builder()
                .id(starNftOrder.getId())
                .userId(starNftOrder.getUserId())
                .themeNumber(starNftOrder.getThemeNumber())
                .totalAmount(starNftOrder.getTotalAmount())
                .themeType(starNftOrder.getThemeType())
                .themePic(starNftOrder.getThemePic())
                .payAmount(starNftOrder.getPayAmount())
                .themeName(starNftOrder.getThemeName())
                .seriesName(starNftOrder.getSeriesName())
                .seriesThemeInfoId(starNftOrder.getSeriesThemeInfoId())
                .seriesId(starNftOrder.getSeriesId())
                .orderSn(starNftOrder.getOrderSn())
                .createdAt(starNftOrder.getCreatedAt())
                .remark(starNftOrder.getRemark())
                .seriesThemeId(starNftOrder.getSeriesThemeId())
                .status(starNftOrder.getStatus())
                .expire(180L)
                .orderType(starNftOrder.getOrderSn().startsWith("PG") ? StarConstants.OrderType.PUBLISH_GOODS.getName() : StarConstants.OrderType.MARKET_GOODS.getName())
                .build();
    }

    @Override
    public OrderVO queryOrderDetails(Long id) {
        StarNftOrder starNftOrder = starNftOrderMapper.selectById(id);
        return populate(starNftOrder);
    }

    @Override
    public OrderVO obtainSecKillOrder(Long uid, Long themeId) {
        Object order = redisUtill.hget(String.format(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), themeId), String.valueOf(uid));
        if (order == null) {
            return null;
        }
        return JSONUtil.toBean(order.toString(), OrderVO.class);
    }

    private StarNftOrder queryOrder(Long uid, String orderSn) {
        //找到对应订单
        return starNftOrderMapper.selectOne(
                new LambdaQueryWrapper<StarNftOrder>().eq(Objects.nonNull(uid), StarNftOrder::getUserId, uid)
                        .eq(StringUtils.isNotBlank(orderSn), StarNftOrder::getOrderSn, orderSn));
    }


    private List<StarNftOrder> queryOrdersUsers(Long uid) {
        return starNftOrderMapper.selectList(new LambdaQueryWrapper<StarNftOrder>()
                .eq(Objects.nonNull(uid), StarNftOrder::getUserId, uid));
    }
}
