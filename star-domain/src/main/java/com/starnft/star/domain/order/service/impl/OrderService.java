package com.starnft.star.domain.order.service.impl;

import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.repository.IOrderRepository;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.domain.order.service.stateflow.IOrderStateHandler;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Resource
    IOrderRepository orderRepository;

    @Resource
    RedisUtil redisUtil;

    @Resource
    IOrderStateHandler orderStateHandler;

    @Override
    public boolean createOrder(OrderVO orderVO) {
        if (null == orderVO) {
            return false;
        }
        return this.orderRepository.createOrder(orderVO);
    }

    @Override
    public ResponsePageResult<OrderListRes> orderList(OrderListReq orderListReq) {
        ResponsePageResult<OrderVO> queryOrders = this.orderRepository.queryOrders(
                orderListReq.getUserId(), orderListReq.getStatus(), orderListReq.getPage(), orderListReq.getSize());
        return ResponsePageResult.listReplace(queryOrders, BeanColverUtil.colverList(queryOrders.getList(), OrderListRes.class));
    }

    @Override
    public OrderListRes orderDetails(OrderListReq orderListReq) {
        Assert.notBlank(orderListReq.getOrderSn(), () -> {
            throw new StarException("订单号不能为空");
        });
        OrderVO orderVO = this.orderRepository.queryOrderDetails(orderListReq.getUserId(), orderListReq.getOrderSn());
        return BeanColverUtil.colver(orderVO, OrderListRes.class);
    }

    @Override
    public OrderListRes obtainSecKillOrder(Long uid, Long themeId) {
        OrderVO orderVO = orderRepository.obtainSecKillOrder(uid, themeId);
        if (orderVO == null) return null;

        return BeanColverUtil.colver(orderVO, OrderListRes.class);
    }

    @Override
    public OrderPlaceRes orderCancel(Long uid, String orderSn, StarConstants.OrderType orderType) {
        //查询是否为有效订单
        OrderVO orderVO = orderRepository.queryOrderByCondition(uid, orderSn);
        if (orderVO == null) {
            throw new StarException(StarError.ORDER_DO_NOT_EXIST);
        }
        if (!orderVO.getStatus().equals(StarConstants.ORDER_STATE.WAIT_PAY.getCode())) {
            throw new StarException(StarError.ORDER_STATUS_ERROR, "该订单无法取消");
        }
        //库存编号是否异常
        Integer themeNumber = orderVO.getThemeNumber();
        if (Objects.isNull(themeNumber) || themeNumber == 0) {
            throw new StarException(StarError.ORDER_STATUS_ERROR);
        }
        //更新订单状态
        Result result = orderStateHandler.payCancel(uid, orderSn, StarConstants.ORDER_STATE.WAIT_PAY);
        if (result.getCode().equals(ResultCode.SUCCESS.getCode())) {
            if (orderType.equals(StarConstants.OrderType.PUBLISH_GOODS)) {
                //库存重新加到队列
                redisUtil.addToListLeft(String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), orderVO.getSeriesThemeInfoId()),
                        -1L, RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTimeUnit(), themeNumber);
                //回滚库存
                redisUtil.hashIncr(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.valueOf(orderVO.getSeriesThemeInfoId()), 1);
                //清理用户订单缓存
                redisUtil.hdel(String.format(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), orderVO.getSeriesThemeInfoId()), String.valueOf(uid));
                //清理排队信息
                redisUtil.hdel(RedisKey.SECKILL_ORDER_REPETITION_TIMES.name(), String.valueOf(uid));
            }
            return new OrderPlaceRes(StarConstants.ORDER_STATE.PAY_CANCEL.getCode(), orderSn);
        }

        throw new StarException(StarError.ORDER_CANCEL_ERROR);
    }
}
