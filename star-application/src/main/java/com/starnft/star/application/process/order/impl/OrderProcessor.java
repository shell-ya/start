package com.starnft.star.application.process.order.impl;

import com.starnft.star.application.mq.producer.order.OrderProducer;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.service.ThemeService;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.WatchService;
import java.util.List;

@Service
public class OrderProcessor implements IOrderProcessor {

    @Resource
    private ThemeService themeService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private OrderProducer orderProducer;

    @Resource
    private WalletService walletService;

    @Override
    public OrderGrabRes orderGrab(OrderGrabReq orderGrabReq) {

        //查询抢购商品信息
        SecKillGoods goods = themeService.obtainGoodsCache(orderGrabReq.getThemeId(), orderGrabReq.getTime());
        if (goods == null) {
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }

        //用户下单次数验证 防重复下单
        Long userOrderedCount = redisUtil.hincr(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), String.valueOf(orderGrabReq.getUserId()), 1L);
        if (userOrderedCount > 1) {
            throw new StarException(StarError.ORDER_REPETITION);
        }

        //库存验证
        String stockKey = String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), orderGrabReq.getThemeId());
        long stock = redisUtil.lGetListSize(stockKey);
        if (stock <= 0) {
            redisUtil.hdel(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), String.valueOf(orderGrabReq.getUserId()));
            throw new StarException(StarError.STOCK_EMPTY_ERROR);
        }

        //校验余额
        WalletResult walletResult = walletService.queryWalletInfo(new WalletInfoReq(orderGrabReq.getUserId()));
        if (walletResult.getBalance().compareTo(goods.getSecCost()) < 1) {
            throw new StarException(StarError.BALANCE_NOT_ENOUGH);
        }

        //mq 异步下单
        orderProducer.secKillOrder(new OrderMessageReq(orderGrabReq.getUserId(), orderGrabReq.getTime(), goods));


        return new OrderGrabRes(0, StarError.SUCCESS_000000.getErrorMessage());
    }
}
