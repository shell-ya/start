package com.starnft.star.domain.order.service.impl;

import cn.hutool.json.JSONUtil;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.BuyBoxNum;
import com.starnft.star.domain.order.model.vo.MarketCancelOrderVo;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.repository.BuyNum;
import com.starnft.star.domain.order.repository.IOrderRepository;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.domain.order.service.stateflow.IOrderStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Resource
    IOrderRepository orderRepository;

    @Resource
    RedisUtil redisUtil;

    @Resource
    IOrderStateHandler orderStateHandler;

    @Resource
    RedisLockUtils redisLockUtils;

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
        Assert.notNull(orderListReq.getOrderId(), () -> {
            throw new StarException("订单id不能为空");
        });
        OrderVO orderVO = this.orderRepository.queryOrderDetails(orderListReq.getOrderId());
        return BeanColverUtil.colver(orderVO, OrderListRes.class);
    }

    @Override
    public OrderListRes obtainSecKillOrder(Long uid, Long themeId) {
        OrderVO orderVO = orderRepository.obtainSecKillOrder(uid, themeId);
        if (orderVO == null) return null;
        //todo builder类型无法使用bean Copy
        return BeanColverUtil.colver(orderVO, OrderListRes.class);
    }

    @Override
    public OrderPlaceRes orderCancel(Long uid, String orderSn, StarConstants.OrderType orderType) {
        //查询是否为有效订单
        OrderVO orderVO = orderRepository.queryOrderByCondition(uid, orderSn);
        if (orderVO == null) {
            throw new StarException(StarError.ORDER_DO_NOT_EXIST);
        }
        if (orderVO.getStatus().equals(StarConstants.ORDER_STATE.PAY_CANCEL.getCode())) {
            log.info("订单已被取消！ orderSn：[{}]", orderSn);
            return null;
        }
        if (!orderVO.getStatus().equals(StarConstants.ORDER_STATE.WAIT_PAY.getCode())) {
            return null;
        }
        //库存编号是否异常
        Integer themeNumber = orderVO.getThemeNumber();
        if (Objects.isNull(themeNumber) || themeNumber == 0) {
            throw new StarException(StarError.ORDER_STATUS_ERROR);
        }
        //更新订单状态
        Result result = orderStateHandler.payCancel(uid, orderSn, StarConstants.ORDER_STATE.WAIT_PAY);

        String userOrderMapping = String.format(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), orderVO.getSeriesThemeInfoId());
        String orderInfo = (String) redisUtil.hget(userOrderMapping, String.valueOf(uid));
        OrderVO orderCache = JSONUtil.toBean(orderInfo, OrderVO.class);

        if (result.getCode().equals(ResultCode.SUCCESS.getCode())) {
            //记录取消订单次数
            logTimes(uid);
            if (orderType.equals(StarConstants.OrderType.PUBLISH_GOODS)) {
                //库存重新加到队列
                redisUtil.addToListLeft(String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), orderVO.getSeriesThemeInfoId(), orderVO.getRemark()),
                        -1L, RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTimeUnit(), themeNumber);
                //回滚库存
                redisUtil.hashIncr(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.format("%s-time-%s", orderVO.getSeriesThemeInfoId(), orderCache.getRemark()), 1);
                //清理用户订单缓存
                redisUtil.hdel(String.format(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), orderVO.getSeriesThemeInfoId()), String.valueOf(uid));
                //清理排队信息
                log.info("[{}] 取消订单清除购买限制 uid = [{}]", this.getClass().getSimpleName(), uid);
                String key = String.format(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), orderVO.getSeriesThemeInfoId());
                redisUtil.hdel(key, String.valueOf(uid));
            }
//            else if (orderType.equals(StarConstants.OrderType.MARKET_GOODS)) {
//                redisLockUtils.unlock(String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), orderVO.getSeriesThemeId()));
//            }
            return new OrderPlaceRes(StarConstants.ORDER_STATE.PAY_CANCEL.getCode(), orderSn);
        }

        throw new StarException(StarError.ORDER_CANCEL_ERROR);
    }

    private void logTimes(Long uid) {
        //记录取消订单次数 达到上限禁止下单12小时
        if (redisUtil.hasKey(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), uid))) {
            Long ttl = redisUtil.getTtl(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), uid), TimeUnit.SECONDS);
            redisUtil.incr(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), uid), 1);
            redisUtil.expire(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), uid), ttl);
            return;
        }
        //过期时间30min
        redisUtil.incr(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), uid), 1);
        redisUtil.expire(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), uid), RedisKey.ORDER_BREAK_COUNT.getTime());
    }

    public boolean cancelOrder(MarketCancelOrderVo cancelOrderVo) {
        return this.orderRepository.updateOrder(cancelOrderVo.getUid(), cancelOrderVo.getOrderSn(),
                StarConstants.ORDER_STATE.PAY_CANCEL.getCode(), null);
    }

    @Override
    public List<OrderVO> queryOrderByUidNSpu(Long uid, Long spu) {
        return orderRepository.queryOrdersByUidNSpu(uid, spu);
    }

    @Override
    public List<OrderVO> queryToPayOrder(Long userId) {
        return this.orderRepository.queryToPayOrder(userId);
    }

    @Override
    public List<OrderVO> queryAllSuccessOrder() {
        return orderRepository.queryAllSuccessByTheme("998977713737334784");
//        return BeanColverUtil.colverList(orderVOS, OrderListRes.class);
    }

    @Override
    public List<BuyNum> queryUserBuyBoxNumber() {
        return orderRepository.queryBuyNum();
    }


}
