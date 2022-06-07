package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.repository.IOrderRepository;
import com.starnft.star.infrastructure.entity.order.StarNftOrder;
import com.starnft.star.infrastructure.mapper.order.StarNftOrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Objects;

@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private StarNftOrderMapper starNftOrderMapper;

    @Override
    public boolean createOrder(OrderVO orderVO) {
        StarNftOrder starNftOrder = new StarNftOrder();
        //todo 入参里生成
        starNftOrder.setOrderSn(StarConstants.OrderPrefix.PublishGoods.getPrefix().concat(orderVO.getOrderSn()));
        starNftOrder.setUserId(orderVO.getUserId());
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
        return starNftOrderMapper.insert(starNftOrder) == 1;
    }

    @Override
    public boolean updateOrder(Long uid, String orderSn, Integer modifiedStatus, String payNumber) {

        StarNftOrder starNftOrder = queryOrder(uid, orderSn);
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
        return BeanColverUtil.colver(starNftOrder, OrderVO.class);
    }

    private StarNftOrder queryOrder(Long uid, String orderSn) {
        //找到对应订单
        return starNftOrderMapper.selectOne(
                new LambdaQueryWrapper<StarNftOrder>().eq(Objects.nonNull(uid), StarNftOrder::getUserId, uid)
                        .eq(StringUtils.isNotBlank(orderSn), StarNftOrder::getOrderSn, orderSn));
    }
}
