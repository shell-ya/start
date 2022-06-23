package com.starnft.star.application.process.order.impl;

import com.starnft.star.application.mq.producer.order.OrderProducer;
import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.application.process.number.res.MarketOrderRes;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.domain.order.service.stateflow.impl.OrderStateHandler;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.service.ThemeService;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.WalletPayRequest;
import com.starnft.star.domain.wallet.model.res.WalletPayResult;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderProcessor implements IOrderProcessor {

    private final ThemeService themeService;
    private final RedisUtil redisUtil;
    private final OrderProducer orderProducer;
    private final WalletService walletService;
    private final IOrderService orderService;
    private final RedisLockUtils redisLockUtils;
    private final IUserService userService;
    private final INumberService numberService;
    private final OrderStateHandler orderStateHandler;
    private final Map<StarConstants.Ids, IIdGenerator> idsIIdGeneratorMap;

    private final TransactionTemplate template;

    @Override
    public OrderGrabRes orderGrab(OrderGrabReq orderGrabReq) {

        //查询抢购商品信息
        SecKillGoods goods = themeService.obtainGoodsCache(orderGrabReq.getThemeId(), orderGrabReq.getTime());
        if (goods == null) {
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }

        //用户下单次数验证 防重复下单
//        Long userOrderedCount = redisUtil.hincr(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), String.valueOf(orderGrabReq.getUserId()), 1L);
//        if (userOrderedCount > 1) {
//            throw new StarException(StarError.ORDER_REPETITION);
//        }

        //库存验证
        String stockKey = String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), orderGrabReq.getThemeId());
        long stock = redisUtil.lGetListSize(stockKey);
        if (stock <= 0) {
            redisUtil.hdel(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), String.valueOf(orderGrabReq.getUserId()));
            throw new StarException(StarError.STOCK_EMPTY_ERROR);
        }

        //校验余额
        walletService.balanceVerify(orderGrabReq.getUserId(), goods.getSecCost());

        try {
            //排队中状态
            redisUtil.hset(String.format(RedisKey.SECKILL_ORDER_USER_STATUS_MAPPING.getKey(), orderGrabReq.getThemeId()),
                    String.valueOf(orderGrabReq.getUserId()), new OrderGrabStatus(orderGrabReq.getUserId(), 0, null, orderGrabReq.getTime()));
            //mq 异步下单
            orderProducer.secKillOrder(new OrderMessageReq(orderGrabReq.getUserId(), orderGrabReq.getTime(), goods));

            return new OrderGrabRes(0, StarError.SUCCESS_000000.getErrorMessage());
        } catch (Exception e) {
            log.error("异步下单失败 uid:[{}] , themeId:[{}] ,time: [{}] , goods: [{}]", orderGrabReq.getUserId(), orderGrabReq.getThemeId(), orderGrabReq.getTime(), goods, e);
            throw new RuntimeException(e);
        } finally {
            walletService.threadClear();
        }
    }

    @Override
    public OrderListRes obtainSecKillOrder(OrderGrabReq orderGrabReq) {
        return orderService.obtainSecKillOrder(orderGrabReq.getUserId(), orderGrabReq.getThemeId());
    }

    @Override
    public OrderGrabStatus obtainSecKIllStatus(OrderGrabReq orderGrabReq) {

        Object status = redisUtil.hget(String.format(RedisKey.SECKILL_ORDER_USER_STATUS_MAPPING.getKey(), orderGrabReq.getThemeId()), String.valueOf(orderGrabReq.getUserId()));
        if (status != null) {
            return (OrderGrabStatus) status;
        }
        return null;
    }

    @Override
    public OrderPayDetailRes orderPay(OrderPayReq orderPayReq) {
        //验证订单

        //验证支付凭证
//        userService.assertPayPwdCheckSuccess(orderPayReq.getUserId(), orderPayReq.getPayToken());
        //规则验证
        walletService.balanceVerify(orderPayReq.getUserId(), new BigDecimal(orderPayReq.getPayAmount()));
        String lockKey = String.format(RedisKey.SECKILL_ORDER_TRANSACTION.getKey(), orderPayReq.getOrderSn());
        try {
            //锁住当前订单交易
            if (redisLockUtils.lock(lockKey, RedisKey.SECKILL_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.SECKILL_ORDER_TRANSACTION.getTime()))) {
                //余额支付
                WalletPayResult walletPayResult = walletService.doWalletPay(createWalletPayReq(orderPayReq));
                if (ResultCode.SUCCESS.getCode().equals(walletPayResult.getStatus())) {
                    Boolean isSuccess = template.execute(status -> {
                        //商品发放 // TODO: 2022/6/23  市场
                        boolean handover = numberService.handover(buildHandOverReq(orderPayReq));
                        //订单状态更新
                        Result result = orderStateHandler.payComplete(orderPayReq.getUserId(), orderPayReq.getOrderSn(), orderPayReq.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);
                        return ResultCode.SUCCESS.getCode().equals(result.getCode()) && handover;
                    });
                    if (isSuccess) {
                        return new OrderPayDetailRes(ResultCode.SUCCESS.getCode(), orderPayReq.getOrderSn());
                    }
                    throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "订单处理异常！");
                }
            }
        } finally {
            walletService.threadClear();
            redisLockUtils.unlock(lockKey);
        }
        throw new StarException(StarError.PAY_PROCESS_ERROR);
    }

    private HandoverReq buildHandOverReq(OrderPayReq orderPayReq) {
        HandoverReq handoverReq = new HandoverReq();
        handoverReq.setUid(orderPayReq.getUserId());
        handoverReq.setFromUid(orderPayReq.getFromUid());// TODO: 2022/6/23 publish id
        handoverReq.setToUid(orderPayReq.getToUid());
        handoverReq.setPreMoney(new BigDecimal(orderPayReq.getPayAmount()));
        handoverReq.setCurrMoney(new BigDecimal(orderPayReq.getPayAmount()));
        handoverReq.setItemStatus(NumberStatusEnum.SOLD.getCode());
        handoverReq.setThemeId(orderPayReq.getThemeId());
        handoverReq.setNumberId(orderPayReq.getNumberId());
        handoverReq.setCategoryType(orderPayReq.getCategoryType());
        handoverReq.setSeriesId(orderPayReq.getSeriesId());
        handoverReq.setType(NumberCirculationTypeEnum.PURCHASE.getCode());
        return handoverReq;
    }

    private WalletPayRequest createWalletPayReq(OrderPayReq orderPayReq) {
        WalletPayRequest walletPayRequest = BeanColverUtil.colver(orderPayReq, WalletPayRequest.class);
        walletPayRequest.setStatus(StarConstants.Pay_Status.PAY_SUCCESS.name());
        BigDecimal payAmount = new BigDecimal(orderPayReq.getPayAmount());
        walletPayRequest.setTotalPayAmount(new BigDecimal(orderPayReq.getTotalPayAmount()));
        walletPayRequest.setFee(new BigDecimal(orderPayReq.getFee()));
        walletPayRequest.setTotalPayAmount(new BigDecimal(orderPayReq.getTotalPayAmount()));
        walletPayRequest.setPayAmount(payAmount.signum() == -1 ? payAmount : payAmount.negate());
        return walletPayRequest;
    }

    @Override
    public OrderPlaceRes cancelSecOrder(OrderCancelReq orderGrabReq) {
        return orderService.orderCancel(orderGrabReq.getUid(), orderGrabReq.getOrderSn(), StarConstants.OrderType.PUBLISH_GOODS);
    }


    @Override
    public MarketOrderRes marketOrder(MarketOrderReq marketOrderReq) {

        ThemeNumberVo numberDetail = numberService.getConsignNumberDetail(marketOrderReq.getNumberId());
        //获取锁
        String isTransaction = String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), marketOrderReq.getNumberId());
        if (redisUtil.hasKey(isTransaction)) {
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }
        //钱包余额充足
        walletService.balanceVerify(marketOrderReq.getUserId(), numberDetail.getPrice());
        long lockTimes = RedisKey.MARKET_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.MARKET_ORDER_TRANSACTION.getTime());
        if (redisLockUtils.lock(isTransaction, lockTimes)) {
            try {
                //生成订单
                String orderSn = StarConstants.OrderPrefix.TransactionSn.getPrefix()
                        .concat(String.valueOf(idsIIdGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId()));
                if (createPreOrder(numberDetail, marketOrderReq.getUserId(), orderSn)) {
                    //发送延时队列
                    orderProducer.marketOrderRollback(new MarketOrderStatus(marketOrderReq.getUserId(), 0, orderSn));
                    return new MarketOrderRes(orderSn, 0, StarError.SUCCESS_000000.getErrorMessage(), lockTimes);
                }
            } catch (Exception e) {
                log.error("创建订单异常: userId: [{}] , themeNumberId: [{}] , context: [{}]", marketOrderReq.getUserId(), marketOrderReq.getNumberId(), numberDetail);
                throw new RuntimeException(e.getMessage());
            } finally {
                walletService.threadClear();
            }
        }
        throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
    }

    private boolean createPreOrder(ThemeNumberVo numberDetail, Long userId, String orderSn) {
        OrderVO orderVO = OrderVO.builder()
                .userId(userId)
                .orderSn(orderSn)
                .payAmount(numberDetail.getPrice())
                .seriesId(numberDetail.getSeriesId())
                .seriesName(numberDetail.getSeriesName())
                .seriesThemeInfoId(numberDetail.getThemeInfoId())
                .seriesThemeId(numberDetail.getNumberId())
                .themeName(numberDetail.getThemeName())
                //.payAmount(numberDetail.getPrice())
                .themePic(numberDetail.getThemePic())
                .themeType(numberDetail.getThemeType())
                .totalAmount(numberDetail.getPrice())
                .themeNumber(numberDetail.getThemeNumber())
                .themeType(numberDetail.getThemeType())
                .build();
        //创建订单
        return orderService.createOrder(orderVO);
    }
}
