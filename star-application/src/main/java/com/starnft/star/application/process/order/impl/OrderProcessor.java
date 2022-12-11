package com.starnft.star.application.process.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.activity.ActivityEventProducer;
import com.starnft.star.application.mq.producer.order.OrderProducer;
import com.starnft.star.application.mq.producer.wallet.WalletProducer;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.model.BuyActivityEventReq;
import com.starnft.star.application.process.event.model.EventReqAssembly;
import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.application.process.order.white.rule.IWhiteRule;
import com.starnft.star.application.process.order.white.rule.WhiteRuleContext;
import com.starnft.star.application.process.rebates.model.RebatesMessage;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.constant.YesOrNoStatusEnum;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.common.utils.secure.AESUtil;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.number.model.dto.NumberDTO;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.model.req.OrderListReq;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.domain.order.service.stateflow.impl.OrderStateHandler;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.handler.ISandPayCloudPayHandler;
import com.starnft.star.domain.payment.model.req.CloudAccountOPenReq;
import com.starnft.star.domain.payment.model.req.CloudAccountStatusReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.CloudAccountOPenRes;
import com.starnft.star.domain.payment.model.res.CloudAccountStatusRes;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.service.ThemeService;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.WhiteListConfigVO;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.TransReq;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.req.WalletPayRequest;
import com.starnft.star.domain.wallet.model.res.WalletPayResult;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.service.WalletConfig;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderProcessor implements IOrderProcessor {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

    private static final String SCRIPI_ROOT = "/redis_lua_script/";
    private static final String keySecret = "lywc-22-ck";
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
    private final WalletProducer walletProducer;
    private final ActivityEventProducer activityProducer;
    //    private final RebatesProducer rebatesProducer;
    private final IRankService rankService;
    private final WhiteRuleContext whiteRuleContext;

    private final IPaymentService paymentService;

    private final ISandPayCloudPayHandler iSandPayCloudPayHandler;

    private final IActivitiesService activitiesService;

    @Value("${C2CTransNotify}")
    private String C2CTransNotify;

    @Value("${C2BTransNotify}")
    private String C2BTransNotify;

    @Value("${sandCashierPayNotify}")
    private String sandCashierPayNotify;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> map;

    public static AtomicInteger uniqueId = new AtomicInteger(1);

    @Override
    public OrderGrabRes createOrder(OrderGrabReq orderGrabReq) {
        //1、待支付订单判断
        if (havingOrder(orderGrabReq.getUserId())) {
            throw new StarException(StarError.ORDER_DONT_PAY_ERROR);
        }

        //是否可购买验证
        List<Serializable> notOnSellList = redisUtil.lGet(RedisKey.SECKILL_GOODS_NOT_ONSELL.getKey(), 0, -1);
        for (Serializable serializable : notOnSellList) {
            Object themeId = serializable;
            if (orderGrabReq.getThemeId().equals(Long.parseLong(themeId.toString()))) {
                throw new StarException(StarError.ORDER_DONT_SELL_ERROR);
            }
        }

        // 恶意下单校验
        Object record = redisUtil.get(String.format(RedisKey.ORDER_BREAK_RECORD.getKey(), orderGrabReq.getUserId()));
        if (record != null) {
            log.error("恶意下单校验 uid: {}， record: {}", orderGrabReq.getUserId(), record);
            throw new StarException(StarError.ORDER_CANCEL_TIMES_OVERFLOW);
        }

        Integer breakTimes = (Integer) redisUtil.get(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), orderGrabReq.getUserId()));
        if (Objects.nonNull(breakTimes) && breakTimes >= 3) {
            redisUtil.set(String.format(RedisKey.ORDER_BREAK_RECORD.getKey(), orderGrabReq.getUserId()), orderGrabReq.getUserId(), RedisKey.ORDER_BREAK_RECORD.getTime());
            log.error("恶意下单校验 uid: {}， breakTimes: {}", orderGrabReq.getUserId(), breakTimes);
            throw new StarException(StarError.ORDER_CANCEL_TIMES_OVERFLOW);
        }

        //查询抢购商品信息
        // SecKillGoods goods = themeService.obtainGoodsCache(orderGrabReq.getThemeId(), orderGrabReq.getTime());
        SecKillGoods goods = activitiesService.getActivityByThemeId(orderGrabReq.getThemeId());
        if (goods == null) {
            log.error("商品信息不存在 themeId:{}，Time:{}", orderGrabReq.getThemeId(), orderGrabReq.getTime());
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }

        //是否白名单
        Boolean isWhite = false;
        if (orderGrabReq.getIsPriority() == 1) {
            isWhite = whiteValidation(orderGrabReq.getUserId(), orderGrabReq.getThemeId());
            log.info("uid ：{}， themeId {}", orderGrabReq.getUserId(), orderGrabReq.getThemeId());
        }
        orderGrabReq.setIsPriority(isWhite ? 1 : 0);
        // 商品售卖时间验证
        if (!isWhite && DateUtil.date().before(goods.getStartTime())) {
            log.info("是白名单么：{}", isWhite);
            throw new StarException(StarError.GOODS_DO_NOT_START_ERROR);
        }

        // 库存校验
        int availableStock = goods.getGoodsNum() - goods.getFrozenStock() - goods.getSoldStock();
        if (availableStock <= 0) {
            log.error("库存不足,themeId:{},Time:{}, SecKillGoods:{}", orderGrabReq.getThemeId(), orderGrabReq.getTime(), goods);
            throw new StarException(StarError.STOCK_EMPTY_ERROR);
        }

        String key = String.format(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), orderGrabReq.getThemeId());
        String buyNumKey = String.format(RedisKey.SECKILL_BUY_GOODS_NUMBER.getKey(), orderGrabReq.getThemeId());

        //用户下单次数验证 防重复下单
        if (orderGrabReq.getThemeId().equals(1002285892654821376L)) {
            Long userOrderedCount = redisUtil.hincr(key, String.valueOf(orderGrabReq.getUserId()), 1L);
            if (userOrderedCount > 1) {
                log.error("防重复下单 uid: {}， themeId : {}， count : {}", orderGrabReq.getUserId(), orderGrabReq.getThemeId(), userOrderedCount);
                throw new StarException(StarError.ORDER_REPETITION);
            }
        }

        if (orderGrabReq.getThemeId().equals(1009469098485923840L)) {
            Long userOrderedCount = redisUtil.hincr(buyNumKey, String.valueOf(orderGrabReq.getUserId()), 1L);
            redisUtil.hdecr(buyNumKey, String.valueOf(orderGrabReq.getUserId()), 1L);
            if (userOrderedCount > 10) {
                log.error("防重复下单 uid: {}， themeId：{}，count，{}", orderGrabReq.getUserId(), orderGrabReq.getThemeId(), userOrderedCount);
                throw new StarException(StarError.ORDER_REPETITION);
            }
        }

        Object stockQueueId = filterNum(orderGrabReq.getUserId(), orderGrabReq.getThemeId(), orderGrabReq.getTime());
        if (Objects.isNull(stockQueueId)) {
            log.error("[filterNum] stockQueueId为空");
            throw new StarException(StarError.STOCK_QUEUE_NULL);
        }

        String goodsKey = String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), orderGrabReq.getTime());
        // 生成订单流水
        String orderSn = StarConstants.OrderPrefix.PublishGoods.getPrefix().concat(String.valueOf(map.get(StarConstants.Ids.SnowFlake).nextId()));

        //创建订单
        OrderVO preOrder = createPreOrder(orderSn, goods, orderGrabReq.getIsPriority(), (int) stockQueueId, orderGrabReq.getUserId(), orderGrabReq.getTime());

        // todo 冻结库存
        boolean modifySuccess = activitiesService.frozeStock(goods.getThemeId().intValue(), goods.getStock(), goods.getVersion());

        //发送延时mq 取消订单
        OrderGrabStatus orderGrabStatus = new OrderGrabStatus(orderGrabReq.getUserId(), 1, orderSn, orderGrabReq.getTime());
        orderProducer.secOrderRollback(orderGrabStatus);

        OrderGrabRes orderGrabRes = new OrderGrabRes(0, StarError.SUCCESS_000000.getErrorMessage());
        orderGrabRes.setOrder(preOrder);
        return orderGrabRes;
    }


    private OrderVO createPreOrder(String orderSn, SecKillGoods goods, Integer isPriority, int stockQueueId, Long userId, String time) {
        //查询对应藏品编号是否存在
        ThemeNumberVo themeNumberVo = numberService.queryNumberExist(stockQueueId, goods.getThemeId());

        if (themeNumberVo == null) {
            log.error("藏品可能未上架 themeId:[{}] , themeNumber:[{}]", goods.getThemeId(), stockQueueId);
            throw new RuntimeException("查询藏品失败！");
        }
        WhiteListConfigVO whiteListConfigVO = userService.obtainWhiteConfig(goods.getThemeId());
        OrderVO orderVO = OrderVO.builder()
                .id(SnowflakeWorker.generateId())
                .userId(userId)
                .orderSn(orderSn)
                .payAmount(goods.getSecCost())
                .seriesId(goods.getSeriesId())
                .seriesName(goods.getSeriesName())
                .seriesThemeInfoId(goods.getThemeId())
                .numberId(themeNumberVo.getNumberId())
                .seriesThemeId(themeNumberVo.getNumberId())
                .themeName(goods.getThemeName())
                .payAmount(goods.getSecCost())
                .themePic(goods.getThemePic())
                .themeType(goods.getThemeType())
                .totalAmount(goods.getSecCost())
                .themeNumber(stockQueueId)
                .publisherId(goods.getPublisherId())
                .status(0)
                .createdAt(new Date())
                .expire(180L)
                .remark(time) //暂时存储秒杀时间戳
                .orderType(StarConstants.OrderType.PUBLISH_GOODS.getName())
                .priorityBuy(isPriority)
                .whiteId(whiteListConfigVO == null ? null : whiteListConfigVO.getId())
                .build();
        //创建订单
        orderService.createOrder(orderVO);
        return orderVO;
        // if (isSuccess) {
        //     String userOrderMapping = String.format(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), goods.getThemeId());
        //     redisUtil.hset(userOrderMapping, String.valueOf(userId), JSONUtil.toJsonStr(orderVO));
        //     return true;
        // }
        // return false;
    }


    private synchronized Object filterNum(long userId, Long themeId, String time) {

        String poolKey = String.format(RedisKey.SECKILL_GOODS_STOCK_POOL.getKey(), themeId, time);
        //不存在库存池 生成并加载一百个库存 或 如果库存池大小小于10 扩容加100
        boolean exists = redisUtil.hasKey(poolKey);
        if (!exists || redisUtil.sGetSetSize(poolKey) <= 10) {
            supplyPool(themeId, poolKey, time);
        }

        Object spop = redisUtil.spop(poolKey);
        log.info("[{}] 用户：[{}] 获得库存编号 ： [{}]", this.getClass().getSimpleName(), userId, spop);
        return spop;
    }


    private void supplyPool(Long themeId, String poolKey, String time) {
        for (int i = 0; i < 100; i++) {
            Object number = redisUtil.rightPop(String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), themeId, time));
            if (number == null) {
                continue;
            }
            Integer num = (int) number;
            redisUtil.sSet(poolKey, num);
        }
    }

    @Override
    public OrderGrabRes orderGrab(OrderGrabReq orderGrabReq) {

        //待支付订单判断
        if (havingOrder(orderGrabReq.getUserId())) {
            throw new StarException(StarError.ORDER_DONT_PAY_ERROR);
        }

        //是否可购买验证
        List<Serializable> notOnSellList = redisUtil.lGet(RedisKey.SECKILL_GOODS_NOT_ONSELL.getKey(), 0, -1);
        for (Serializable serializable : notOnSellList) {
            Object themeId = serializable;
            if (orderGrabReq.getThemeId().equals(Long.parseLong(themeId.toString()))) {
                throw new StarException(StarError.ORDER_DONT_SELL_ERROR);
            }
        }

        // 恶意下单校验
        Object record = redisUtil.get(String.format(RedisKey.ORDER_BREAK_RECORD.getKey(), orderGrabReq.getUserId()));
        if (record != null) {
            log.error("恶意下单校验 uid: [{}] record: [{}]", orderGrabReq.getUserId(), record);
            throw new StarException(StarError.ORDER_CANCEL_TIMES_OVERFLOW);
        }

        Integer breakTimes = (Integer) redisUtil.get(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), orderGrabReq.getUserId()));
        if (Objects.nonNull(breakTimes) && breakTimes >= 3) {
            redisUtil.set(String.format(RedisKey.ORDER_BREAK_RECORD.getKey(), orderGrabReq.getUserId()), orderGrabReq.getUserId(), RedisKey.ORDER_BREAK_RECORD.getTime());
            log.error("恶意下单校验 uid: [{}] breakTimes: [{}]", orderGrabReq.getUserId(), breakTimes);
            throw new StarException(StarError.ORDER_CANCEL_TIMES_OVERFLOW);
        }

        //查询抢购商品信息
        SecKillGoods goods = themeService.obtainGoodsCache(orderGrabReq.getThemeId(), orderGrabReq.getTime());
        if (goods == null) {
            log.error("商品信息不存在 themeId: [{}] Time : [{}]", orderGrabReq.getThemeId(), orderGrabReq.getTime());
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }

        //是否白名单
        Boolean isWhite = false;
        if (orderGrabReq.getIsPriority() == 1) {
            isWhite = whiteValidation(orderGrabReq.getUserId(), orderGrabReq.getThemeId());
            log.info("uid [{}] themeId [{}]", orderGrabReq.getUserId(), orderGrabReq.getThemeId());
        }
        orderGrabReq.setIsPriority(isWhite ? 1 : 0);
        // 商品售卖时间验证
        if (!isWhite && DateUtil.date().before(goods.getStartTime())) {
            log.info("是白名单么[{}]", isWhite);
            throw new StarException(StarError.GOODS_DO_NOT_START_ERROR);
        }

//        //白名单购买时间
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(goods.getStartTime());
//        instance.add(Calendar.HOUR, -1);
//        Date whiteTime = instance.getTime();
//        if (DateUtil.date().before(whiteTime)) {
//            throw new StarException(StarError.GOODS_DO_NOT_START_ERROR);
//        }

        //库存验证
        String stockKey = String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), orderGrabReq.getThemeId(), orderGrabReq.getTime());
        String poolKey = String.format(RedisKey.SECKILL_GOODS_STOCK_POOL.getKey(), orderGrabReq.getThemeId(), orderGrabReq.getTime());
        long stock = redisUtil.lGetListSize(stockKey);
        long poolStock = redisUtil.sGetSetSize(poolKey);
        String key = String.format(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), orderGrabReq.getThemeId());
        String buyNumKey = String.format(RedisKey.SECKILL_BUY_GOODS_NUMBER.getKey(), orderGrabReq.getThemeId());
        if ((stock + poolStock) <= 0) {
            redisUtil.hdel(key, String.valueOf(orderGrabReq.getUserId()));
            log.error("库存不足 themeId: [{}] Time : [{}] stock : [{}]", orderGrabReq.getThemeId(), orderGrabReq.getTime(), stock);
            throw new StarException(StarError.STOCK_EMPTY_ERROR);
        }

        String purchaseKey = String.format(RedisKey.SECKILL_GOODS_PURCHASE_LOCK.getKey(), orderGrabReq.getThemeId(), orderGrabReq.getUserId());
        Boolean lock = redisLockUtils.lock(purchaseKey, 10);
        Assert.isTrue(lock, () -> new StarException(StarError.ORDER_REPETITION, "您的下单频率太快了,休息一下吧！"));
        if (lock) {
            try {
                //校验余额  --- 去掉校验余额逻辑
                // walletService.balanceVerify(orderGrabReq.getUserId(), goods.getSecCost());
                //用户下单次数验证 防重复下单
                if (orderGrabReq.getThemeId().equals(1002285892654821376L)) {
                    Long userOrderedCount = redisUtil.hincr(key, String.valueOf(orderGrabReq.getUserId()), 1L);
                    if (userOrderedCount > 1) {
                        log.error("防重复下单 uid: [{}] themeId : [{}] count : [{}]", orderGrabReq.getUserId(), orderGrabReq.getThemeId(), userOrderedCount);
                        throw new StarException(StarError.ORDER_REPETITION);
                    }
                }

                if (orderGrabReq.getThemeId().equals(1009469098485923840L)) {

                    Long userOrderedCount = redisUtil.hincr(buyNumKey, String.valueOf(orderGrabReq.getUserId()), 1L);
                    redisUtil.hdecr(buyNumKey, String.valueOf(orderGrabReq.getUserId()), 1L);
                    if (userOrderedCount > 10) {
                        log.error("防重复下单 uid: [{}] themeId : [{}] count : [{}]", orderGrabReq.getUserId(), orderGrabReq.getThemeId(), userOrderedCount);
                        throw new StarException(StarError.ORDER_REPETITION);
                    }
                }

                //排队中状态
                redisUtil.hset(String.format(RedisKey.SECKILL_ORDER_USER_STATUS_MAPPING.getKey(), orderGrabReq.getThemeId()),
                        String.valueOf(orderGrabReq.getUserId()), JSONUtil.toJsonStr(new OrderGrabStatus(orderGrabReq.getUserId(), 0, null, orderGrabReq.getTime())));
                //mq 异步下单
                orderProducer.secKillOrder(new OrderMessageReq(orderGrabReq.getUserId(), orderGrabReq.getTime(), orderGrabReq.getIsPriority(), goods));

                return new OrderGrabRes(0, StarError.SUCCESS_000000.getErrorMessage());
            } catch (Exception e) {
                redisLockUtils.unlock(purchaseKey);
                log.error("异步下单失败 uid:[{}] , themeId:[{}] ,time: [{}] , goods: [{}]", orderGrabReq.getUserId(), orderGrabReq.getThemeId(), orderGrabReq.getTime(), goods, e);
                throw new RuntimeException(e);
            } finally {
                walletService.threadClear();
            }
        }
        throw new StarException(StarError.SYSTEM_ERROR);
    }

    private Boolean whiteValidation(Long userId, Long themeId) {
        IWhiteRule iWhiteRule = whiteRuleContext.obtainWhiteRule(String.valueOf(themeId));
        if (iWhiteRule == null) {
            return false;
        }
        return iWhiteRule.verifyRule(userId, themeId);
    }

    @Override
    public OrderListRes obtainSecKillOrder(OrderGrabReq orderGrabReq) {
        return orderService.obtainSecKillOrder(orderGrabReq.getUserId(), orderGrabReq.getThemeId());
    }

    @Override
    public OrderGrabStatus obtainSecKIllStatus(OrderGrabReq orderGrabReq) {

        Object status = redisUtil.hget(String.format(RedisKey.SECKILL_ORDER_USER_STATUS_MAPPING.getKey(),
                orderGrabReq.getThemeId()), String.valueOf(orderGrabReq.getUserId()));
        if (status != null) {
            return JSONUtil.toBean(status.toString(), OrderGrabStatus.class);
        }
        return null;
    }

    @Override
    public OrderPayDetailRes orderPay(@Validated OrderPayReq orderPayReq) {

        log.info("用户发起支付：{}", orderPayReq.toString());

        verifyOwnerBy(orderPayReq);

        //市场订单参数手续费计算
        if (orderPayReq.getOrderSn().startsWith(StarConstants.OrderPrefix.TransactionSn.getPrefix())) {
            calculateFee(orderPayReq);
        }
        OrderVO orderVO = orderService.queryOrder(orderPayReq.getOrderSn());
        orderPayReq.setOrderId(orderVO.getId());

        //验证支付凭证
        log.info("ready to assertPayPwdCheckSuccess....begin");
        userService.assertPayPwdCheckSuccess(orderPayReq.getUserId(), orderPayReq.getPayToken());
        log.info("ready to assertPayPwdCheckSuccess....end");

        WalletResult walletResult = this.walletService.queryWalletInfo(new WalletInfoReq(orderPayReq.getUserId()));

        if (walletResult.getBalance().compareTo(orderVO.getPayAmount()) >= 0) {
            log.info("余额充足 -> 扣减余额支付:{}....begin", orderPayReq.getOrderSn());
            // 1、余额充足 -> 扣减余额支付

            String lockKey = String.format(RedisKey.SECKILL_ORDER_TRANSACTION.getKey(), orderPayReq.getOrderSn());
            Boolean lock = redisLockUtils.lock(lockKey, RedisKey.SECKILL_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.SECKILL_ORDER_TRANSACTION.getTime()));
            Assert.isTrue(lock, () -> new StarException(StarError.PAY_PROCESS_ERROR));
            try {
                Boolean isSuccess = template.execute(status -> {
                    //余额支付
                    WalletPayResult walletPayResult = walletService.doWalletPay(createWalletPayReq(orderPayReq));
                    //商品发放 // TODO: 2022/6/23  市场 如果支付接口响应过慢 该操作可异步化 最终一致性即可
                    boolean handover = numberService.handover(buildHandOverReq(orderPayReq));
                    //订单状态更新
                    Result result = orderStateHandler.payComplete(orderPayReq.getUserId(), orderPayReq.getOrderSn(), orderPayReq.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);
                    return ResultCode.SUCCESS.getCode().equals(walletPayResult.getStatus()) && ResultCode.SUCCESS.getCode().equals(result.getCode()) && handover;
                });
                if (Boolean.TRUE.equals(isSuccess)) {
                    redisUtil.hincr(String.format(RedisKey.SECKILL_BUY_GOODS_NUMBER.getKey(), orderPayReq.getThemeId()), String.valueOf(orderPayReq.getUserId()), 1L);

                    //只有首发订单增加积分
                    if (!orderPayReq.getOrderSn().startsWith(StarConstants.OrderPrefix.TransactionSn.getPrefix())) {
                        OrderListRes orderListRes = orderService.obtainSecKillOrder(orderPayReq.getUserId(), orderPayReq.getThemeId());
                        if (orderListRes.getPriorityBuy() == 1) subPTimes(orderPayReq.getUserId(), orderListRes);
                        activityProducer.sendScopeMessage(createEventReq(orderPayReq));
                    }
//                    rebatesProducer.sendRebatesMessage(createRebates(orderPayReq));
                    return new OrderPayDetailRes(ResultCode.SUCCESS.getCode(), orderPayReq.getOrderSn(), null);
                }
                throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "订单处理异常！");
            } catch (TransactionException | StarException e) {
                log.error("钱包支付异常，异常信息:{}", e.getMessage(), e);
                throw new RuntimeException(e);
            } finally {
                redisLockUtils.unlock(lockKey);
                walletService.threadClear();
            }
        } else {
            // 2、余额不足 -> 跳转快捷收银台支付
            log.info("余额不足 -> 跳转快捷收银台支付:{}....begin", orderPayReq.getOrderSn());
            return this.sandCashierPay(orderPayReq);
        }
    }

    private PaymentRich buildPayReq(WalletPayRequest walletPayRequest) {
        String encryptOrderSn = AESUtil.encrypt(walletPayRequest.getOrderSn());
        HashMap<String, Object> valueMap = Maps.newHashMap();
//        valueMap.put("cost", walletPayRequest.getFee().intValue() == 0 ? new BigDecimal("0.01").toString() : walletPayRequest.getFee().abs().toString());
//        valueMap.put("remark", walletPayRequest.getThemeId().toString().concat("#").concat(walletPayRequest.getNumberId().toString()));
//        valueMap.put("accUserId", walletPayRequest.getUserId().toString());
        if (walletPayRequest.getOrderSn().startsWith(StarConstants.OrderPrefix.PublishGoods.getPrefix())) {
            valueMap.put("userId", walletPayRequest.getUserId().toString());
            UserInfoVO userInfoVO = userService.queryUserInfo(walletPayRequest.getUserId());
            valueMap.put("nickName", userInfoVO.getNickName());
            PaymentRich build = PaymentRich.builder()
                    .totalMoney(walletPayRequest.getPayAmount().abs()).payChannel(StarConstants.PayChannel.CloudAccount.name())
                    .frontUrl("https://www.circlemeta.cn/order/payed/" + walletPayRequest.getOrderSn()).clientIp("192.168.1.1")
                    .orderSn(String.valueOf(walletPayRequest.getOrderId())).userId(walletPayRequest.getUserId())
                    .payExtend(valueMap)
                    .multicastTopic(String.format(TopicConstants.WALLER_PAY_DESTINATION.getFormat(), TopicConstants.WALLER_PAY_DESTINATION.getTag()))
                    .orderType(StarConstants.OrderType.PUBLISH_GOODS).build();
            System.out.println(build);
            return build;
        } else if (walletPayRequest.getOrderSn().startsWith(StarConstants.OrderPrefix.TransactionSn.getPrefix())) {
            valueMap.put("cost", "0");//手续费
            valueMap.put("remark", "市场订单");//备注
            valueMap.put("accUserId", "536952750");//收款账号
            PaymentRich build = PaymentRich.builder()
                    .totalMoney(walletPayRequest.getPayAmount().abs()).payChannel(StarConstants.PayChannel.CloudAccount.name())
                    .frontUrl("https://www.circlemeta.cn/order/payed/" + walletPayRequest.getOrderSn()).clientIp("192.168.1.1")
                    .orderSn(String.valueOf(walletPayRequest.getOrderId())).userId(walletPayRequest.getUserId())
                    .payExtend(valueMap)
                    .multicastTopic(String.format(TopicConstants.WALLER_PAY_DESTINATION.getFormat(), TopicConstants.WALLER_PAY_DESTINATION.getTag()))
                    .orderType(StarConstants.OrderType.MARKET_GOODS).build();//市场商品
            System.out.println(build);
            return build;
        }
        throw new StarException(StarError.CLOUD_BUILD_PAY);
    }

    private void subPTimes(Long userId, OrderListRes orderListRes) {
        Boolean isSuccess = userService.whiteTimeConsume(userId, orderListRes.getWhiteId());
        if (!isSuccess) userService.whiteTimeConsume(userId, 1L);
    }

    private void verifyOwnerBy(OrderPayReq orderPayReq) {
        if (orderPayReq.getOrderSn().startsWith(StarConstants.OrderPrefix.TransactionSn.getPrefix())) {
            //查询当前藏品拥有人与下订单时拥有人一致
            if ((Objects.isNull(orderPayReq.getOwnerId()) ||
                    0L == Long.parseLong(orderPayReq.getOwnerId())) ||
                    !numberService.isOwner(Long.parseLong(orderPayReq.getOwnerId()), orderPayReq.getThemeId(), orderPayReq.getNumberId())
            )
                throw new StarException(StarError.ORDER_STATUS_ERROR, "请确认藏品拥有者正确性");
        }
    }

    private RebatesMessage createRebates(OrderPayReq orderPayReq) {
        RebatesMessage rebatesMessage = new RebatesMessage();
        rebatesMessage.setPayMoney(new BigDecimal(orderPayReq.getPayAmount()));
        rebatesMessage.setOrderType(orderPayReq.getOrderSn().startsWith(StarConstants.OrderPrefix.PublishGoods.getPrefix(), 0) ?
                StarConstants.OrderType.PUBLISH_GOODS : StarConstants.OrderType.MARKET_GOODS);
        rebatesMessage.setUserId(orderPayReq.getUserId());
        return rebatesMessage;
    }

    private void calculateFee(OrderPayReq orderPayReq) {
        //todo 计算
        walletService.verifyParam(orderPayReq.getChannel());
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf(orderPayReq.getChannel()));
        BigDecimal payMoney = new BigDecimal(orderPayReq.getPayAmount());
        BigDecimal transRate = payMoney.multiply(config.getServiceRate());
        BigDecimal copyrightRate = payMoney.multiply(config.getCopyrightRate());
        BigDecimal fee = transRate.add(copyrightRate);
        orderPayReq.setFee(fee.toString());
//        return;
    }

    private ActivityEventReq createEventReq(OrderPayReq orderPayReq) {
        BuyActivityEventReq buyActivityEventReq = new BuyActivityEventReq();
        buyActivityEventReq.setEventSign(StarConstants.EventSign.Buy.getSign());
        buyActivityEventReq.setUserId(orderPayReq.getUserId());
        buyActivityEventReq.setReqTime(new Date());
        buyActivityEventReq.setSeriesId(orderPayReq.getSeriesId());
        buyActivityEventReq.setThemeId(orderPayReq.getThemeId());
        buyActivityEventReq.setMoney(new BigDecimal(orderPayReq.getPayAmount()));
        return EventReqAssembly.assembly(buyActivityEventReq);
    }

    private WalletPayRequest createTranReq(OrderPayReq orderPayReq) {
        //收款方是寄售用户
//        TransReq transReq = new TransReq();
//        transReq.setUid(orderPayReq.getOwnerId());
//        transReq.setTsType(StarConstants.Transaction_Type.Sell.getCode());
//        transReq.setPayChannel(orderPayReq.getChannel());
//        transReq.setOrderSn(orderPayReq.getOrderSn());
//        transReq.setTotalAmount(new BigDecimal(orderPayReq.getTotalPayAmount()));
//        BigDecimal payAmount = new BigDecimal(orderPayReq.getPayAmount());
//        transReq.setPayAmount(payAmount.signum() >= 0 ? payAmount : payAmount.negate());
        //实际收款金额是挂失金额减去手续费
        WalletPayRequest walletPayRequest = BeanColverUtil.colver(orderPayReq, WalletPayRequest.class);
        walletPayRequest.setStatus(StarConstants.Pay_Status.PAY_SUCCESS.name());
        BigDecimal payAmount = new BigDecimal(orderPayReq.getPayAmount());
        walletPayRequest.setTotalPayAmount(new BigDecimal(orderPayReq.getTotalPayAmount()));
//        walletPayRequest.setFee(new BigDecimal(orderPayReq.getFee()));
//        walletPayRequest.setTotalPayAmount(new BigDecimal(orderPayReq.getTotalPayAmount()));
        walletPayRequest.setPayAmount(payAmount.signum() == -1 ? payAmount.negate() : payAmount);
        walletPayRequest.setFromUid(orderPayReq.getUserId());
        walletPayRequest.setToUid(Long.parseLong(orderPayReq.getOwnerId()));
        walletPayRequest.setUserId(Long.parseLong(orderPayReq.getOwnerId()));
        walletPayRequest.setType(StarConstants.Transaction_Type.Sell.getCode());

        return walletPayRequest;
    }

    private HandoverReq buildHandOverReq(OrderPayReq orderPayReq) {
        HandoverReq handoverReq = new HandoverReq();
        handoverReq.setUid(orderPayReq.getUserId());
        handoverReq.setFromUid(Long.parseLong(orderPayReq.getOwnerId()));// TODO: 2022/6/23 publish id
        handoverReq.setToUid(orderPayReq.getUserId());
        handoverReq.setPreMoney(new BigDecimal(orderPayReq.getPayAmount()));
        handoverReq.setCurrMoney(new BigDecimal(orderPayReq.getPayAmount()));
        handoverReq.setItemStatus(NumberStatusEnum.SOLD.getCode());
        handoverReq.setThemeId(orderPayReq.getThemeId());
        handoverReq.setNumberId(orderPayReq.getNumberId());
        handoverReq.setCategoryType(orderPayReq.getCategoryType());
        handoverReq.setSeriesId(orderPayReq.getSeriesId());
        handoverReq.setType(NumberCirculationTypeEnum.PURCHASE.getCode());
        handoverReq.setOrderType(orderPayReq.getOrderSn().startsWith(StarConstants.OrderPrefix.PublishGoods.getPrefix(), 0) ?
                StarConstants.OrderType.PUBLISH_GOODS : StarConstants.OrderType.MARKET_GOODS);
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
        walletPayRequest.setFromUid(orderPayReq.getUserId());
        walletPayRequest.setToUid(StringUtils.isEmpty(orderPayReq.getOwnerId()) ? 0L : Long.parseLong(orderPayReq.getOwnerId()));
        walletPayRequest.setUserId(orderPayReq.getUserId());
        return walletPayRequest;
    }

    @Override
    public OrderPlaceRes cancelSecOrder(OrderCancelReq orderGrabReq) {
        String lockKey = String.format(RedisKey.SECKILL_ORDER_TRANSACTION.getKey(), orderGrabReq.getOrderSn());
        //锁住当前订单交易
        Boolean lock = redisLockUtils.lock(lockKey, RedisKey.SECKILL_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.SECKILL_ORDER_TRANSACTION.getTime()));
        Assert.isTrue(lock, () -> new RuntimeException("用户 [" + orderGrabReq.getUid() + "] orderSn: [ " + orderGrabReq.getOrderSn() + "] 正在交易！"));
        try {
            OrderPlaceRes orderPlaceRes = orderService.orderCancel(orderGrabReq.getUid(), orderGrabReq.getOrderSn(),
                    orderGrabReq.getOrderSn().startsWith(StarConstants.OrderPrefix.PublishGoods.getPrefix()) ?
                            StarConstants.OrderType.PUBLISH_GOODS : StarConstants.OrderType.MARKET_GOODS);
            return orderPlaceRes;
        } finally {
            redisLockUtils.unlock(lockKey);
        }
    }

    @Override
    public OrderListRes marketOrder(MarketOrderReq marketOrderReq) {


        ArrayList<Long> objects = Lists.newArrayList();
        objects.add(799041013L);
        objects.add(214502860L);
        objects.add(336673887L);
        objects.add(899131914L);
        objects.add(915512099L);
        objects.add(546998827L);
        objects.add(306868603L);
        objects.add(788013220L);
        objects.add(536952750L);
        if (!objects.contains(marketOrderReq.getUserId())) {
            // 恶意下单校验
            Object record = redisUtil.get(String.format(RedisKey.ORDER_BREAK_RECORD.getKey(), marketOrderReq.getUserId()));
            if (record != null) {
                log.error("恶意下单校验 uid: [{}] record: [{}]", marketOrderReq.getUserId(), record);
                throw new StarException(StarError.ORDER_CANCEL_TIMES_OVERFLOW);
            }

            Integer breakTimes = (Integer) redisUtil.get(String.format(RedisKey.ORDER_BREAK_COUNT.getKey(), marketOrderReq.getUserId()));
            if (Objects.nonNull(breakTimes) && breakTimes >= 3) {
                redisUtil.set(String.format(RedisKey.ORDER_BREAK_RECORD.getKey(), marketOrderReq.getUserId()), marketOrderReq.getUserId(), RedisKey.ORDER_BREAK_RECORD.getTime());
                log.error("恶意下单校验 uid: [{}] breakTimes: [{}]", marketOrderReq.getUserId(), breakTimes);
                throw new StarException(StarError.ORDER_CANCEL_TIMES_OVERFLOW);
            }
        }

        //待支付订单判断
//        if (havingOrder(marketOrderReq.getUserId())) {
//            throw new StarException(StarError.ORDER_DONT_PAY_ERROR);
//        }


        //获取锁
        String isTransaction = String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), marketOrderReq.getNumberId());
        if (redisUtil.hasKey(RedisLockUtils.REDIS_LOCK_PREFIX + isTransaction)) {
            throw new StarException(StarError.GOODS_IS_TRANSACTION);
        }

        //多重检查
        ThemeNumberVo numberDetail = verifyAgain(marketOrderReq);
        //钱包余额充足 -- 去掉余额校验
        // walletService.balanceVerify(marketOrderReq.getUserId(), numberDetail.getPrice());
        long lockTimes = RedisKey.MARKET_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.MARKET_ORDER_TRANSACTION.getTime());
        Boolean lock = redisLockUtils.lock(isTransaction, lockTimes);
        Assert.isTrue(lock, () -> new RuntimeException("用户 [" + marketOrderReq.getUserId() + "] numberId: [ " + marketOrderReq.getNumberId() + "] 正在交易！"));
        try {
            //生成订单
            String orderSn = StarConstants.OrderPrefix.TransactionSn.getPrefix()
                    .concat(String.valueOf(idsIIdGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId()));
            long id = idsIIdGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId();
            if (createPreOrder(numberDetail, marketOrderReq.getUserId(), orderSn, id)) {
                //发送延时队列
                orderProducer.marketOrderRollback(new MarketOrderStatus(marketOrderReq.getUserId(), 0, orderSn));
//                    return new OrderListRes(id,orderSn, 0, StarError.SUCCESS_000000.getErrorMessage(), lockTimes);
                return buildOrderResp(lockTimes, numberDetail, marketOrderReq.getUserId(), orderSn, id);
            }
        } catch (Exception e) {
            redisLockUtils.unlock(isTransaction);
            log.error("创建订单异常: userId: [{}] , themeNumberId: [{}] , context: [{}]", marketOrderReq.getUserId(), marketOrderReq.getNumberId(), numberDetail);
            throw new RuntimeException(e.getMessage());
        } finally {
            walletService.threadClear();
        }
        throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
    }

    private ThemeNumberVo verifyAgain(MarketOrderReq marketOrderReq) {
        ThemeNumberVo numberDetail = numberService.getConsignNumberDetail(new NumberDTO(marketOrderReq.getNumberId(), marketOrderReq.getOwnerId()));
        //藏品所属人改变
        if (Objects.isNull(numberDetail)) {
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }
        //禁止购买自己售出商品
        if (marketOrderReq.getUserId().equals(numberDetail.getOwnerBy()))
            throw new StarException(StarError.GOODS_SELF_ERROR);

        return numberDetail;
    }

    @Override
    public OrderListRes orderDetails(OrderListReq req) {
        OrderListRes orderListRes = this.orderService.orderDetails(req);
        NumberDetailVO numberDetail = this.numberService.getNumberDetail(orderListRes.getSeriesThemeId());
        orderListRes.setOwnerBy(numberDetail.getOwnerBy());
        return orderListRes;
    }

    @Override
    public Boolean dataCheck(Long themeId, String inputKey) {
        if (!keySecret.equals(inputKey)) {
            throw new StarException(StarError.SYSTEM_ERROR, "Key error");
        }
        String redisKey = String.format(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), themeId);

        Set<Object> objects = redisUtil.hashKeys(redisKey);

        for (Object object : objects) {
            try {
                String suid = (String) object;
                Long uid = Long.parseLong(suid);
//                DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
//                String script = "return redis.call('HGET',\"" + redisKey + "\"," +
//                        ("\"\\\\xac\\\\xed\\\\x00\\\\x05t\\\\x00\\\\x09" + suid)
//                        + "\")";
//                System.out.println(script);
//                defaultRedisScript.setScriptText(script);
//                defaultRedisScript.setResultType(Integer.class);
//                List<String> keyList = new ArrayList<>();
//                keyList.add("abc");
//                Integer times = (Integer) redisUtil.getTemplate().execute(defaultRedisScript,new JdkSerializationRedisSerializer(), RedisSerializer.string(), keyList, "abc");
                Long hincr = redisUtil.hincr(redisKey, suid, 1L);
                Long times = redisUtil.hdecr(redisKey, suid, 1L);
                List<OrderVO> orders = orderService.queryOrderByUidNSpu(uid, themeId);
                if (times > 1 && CollectionUtil.isEmpty(orders)) {
                    log.info("[{}] 取消订单清除购买限制 uid = [{}]", this.getClass().getSimpleName(), uid);

                    redisUtil.hdel(redisKey, String.valueOf(uid));
                }

                List<OrderVO> collect = orders.stream()
                        .filter(order -> Objects.equals(order.getStatus(), StarConstants.ORDER_STATE.COMPLETED.getCode()))
                        .collect(Collectors.toList());

                if (times > 1 && CollectionUtil.isEmpty(collect)) {
                    log.info("[{}] 取消订单清除购买限制 uid = [{}]", this.getClass().getSimpleName(), uid);
                    redisUtil.hdel(redisKey, String.valueOf(uid));
                }
            } catch (Exception e) {
                log.error("class： [{}],method: [{}] 异常", this.getClass().getSimpleName(), "dataCheck", e);
                throw new StarException(StarError.SYSTEM_ERROR, "数据清理异常");
            }

        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean havingOrder(Long userId) {
        return orderService.queryToPayOrder(userId).size() > 0;
    }

    @Override
    public Integer priorityTimes(Long userId) {

        Long times = 0L;
        synchronized (this) {
            redisUtil.hincr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(), String.valueOf(userId), 1L);
            times = redisUtil.hdecr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(), String.valueOf(userId), 1L);
        }

        return times.intValue();
    }

    @Override
    public OrderListRes payDetails(String orderSn) {
        String decryptOrderSn = AESUtil.decrypt(orderSn);
        while (orderService.queryOrder(decryptOrderSn).getStatus().equals(StarConstants.ORDER_STATE.COMPLETED.getCode())) {
            OrderVO orderVO = orderService.queryOrder(decryptOrderSn);
            OrderListRes orderListRes = BeanColverUtil.colver(orderVO, OrderListRes.class);
            NumberDetailVO numberDetail = this.numberService.getNumberDetail(orderListRes.getSeriesThemeId());
            orderListRes.setOwnerBy(numberDetail.getOwnerBy());
            return orderListRes;
        }
        throw new StarException(StarError.SYSTEM_ERROR, "请求超时");
    }

    @Override
    public Boolean marketC2COrder(PayCheckRes payCheckRes) {
        OrderVO orderVO = null;

        orderVO = orderService.queryOrder(payCheckRes.getOrderSn());
        if (orderVO == null) {
            log.error("uid :[{}] orderId : [{}] 不存在！", payCheckRes.getUid(), payCheckRes.getOrderSn());
            throw new RuntimeException("订单不存在！");
        }

        if (!payCheckRes.getStatus().equals(ResultCode.SUCCESS.getCode())) {
            try {
                Result result = orderStateHandler.payCancel(orderVO.getUserId(), orderVO.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);
                return ResultCode.SUCCESS.getCode().equals(result.getCode());
            } catch (Exception e) {
                throw new RuntimeException("订单处理异常！", e);
            }
        }

        try {
            OrderVO finalOrderVO = orderVO;
            Boolean isSuccess = template.execute(status -> {
                //商品发放
                boolean handover = numberService.handover(buildHandOverReq(finalOrderVO));

                //订单状态更新
                Result result = orderStateHandler.payComplete(finalOrderVO.getUserId(), finalOrderVO.getOrderSn(), finalOrderVO.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);

                return ResultCode.SUCCESS.getCode().equals(result.getCode()) && handover;
            });
            return isSuccess;
        } catch (TransactionException | StarException e) {
            throw new RuntimeException("订单处理异常！", e);
        }
    }

    @Override
    public Boolean marketC2BOrder(PayCheckRes payCheckRes) {
        OrderVO orderVO = null;


        orderVO = orderService.queryOrder(payCheckRes.getOrderSn());

        NumberDetailVO numberDetail = numberService.getNumberDetail(orderVO.getSeriesThemeId());

        if (orderVO == null) {
            log.error("uid :[{}] orderId : [{}] 不存在！", payCheckRes.getUid(), payCheckRes.getOrderSn());
            throw new RuntimeException("订单不存在！");
        }

        if (!payCheckRes.getStatus().equals(ResultCode.SUCCESS.getCode())) {
            try {
                Result result = orderStateHandler.payCancel(orderVO.getUserId(), orderVO.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);
                return ResultCode.SUCCESS.getCode().equals(result.getCode());
            } catch (Exception e) {
                throw new RuntimeException("订单处理异常！", e);
            }
        }

        try {
            OrderVO finalOrderVO = orderVO;
            Boolean isSuccess = template.execute(status -> {

                //商品发放
                boolean handover = numberService.handover(buildHandOverReq(finalOrderVO));

                //订单状态更新
                Result result = orderStateHandler.payComplete(finalOrderVO.getUserId(), finalOrderVO.getOrderSn(), finalOrderVO.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);

                // 给卖家钱包加钱
                String fee = calFee(finalOrderVO.getPayAmount().toString());
                BigDecimal feeBig = new BigDecimal(fee);
                log.info("[marketC2BOrder]给卖家钱包加钱，参数：orderSn-{}, payAmount-{},fee-{}, realAddAmount-{}", finalOrderVO.getOrderSn(), finalOrderVO.getPayAmount(), fee, finalOrderVO.getPayAmount().subtract(feeBig));

                TransReq transReq = createMarketTransReq(finalOrderVO.getOrderSn(), payCheckRes.getSandSerialNo(), finalOrderVO.getPayAmount().subtract(feeBig), Long.valueOf(numberDetail.getOwnerBy()));
                walletService.doC2BTransaction(transReq);

                return ResultCode.SUCCESS.getCode().equals(result.getCode()) && handover;
            });
            return isSuccess;
        } catch (TransactionException | StarException e) {
            throw new RuntimeException("订单处理异常！", e);
        }
    }

    @Override
    public Boolean marketCashierOrder(PayCheckRes payCheckRes) {
        OrderVO orderVO = null;

        orderVO = orderService.queryOrder(payCheckRes.getOrderSn());

        NumberDetailVO numberDetail = numberService.getNumberDetail(orderVO.getSeriesThemeId());

        if (orderVO == null) {
            log.error("uid :[{}] orderId : [{}] 不存在！", payCheckRes.getUid(), payCheckRes.getOrderSn());
            throw new RuntimeException("订单不存在！");
        }

        if (!payCheckRes.getStatus().equals(ResultCode.SUCCESS.getCode())) {
            try {
                Result result = orderStateHandler.payCancel(orderVO.getUserId(), orderVO.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);
                return ResultCode.SUCCESS.getCode().equals(result.getCode());
            } catch (Exception e) {
                throw new RuntimeException("订单处理异常！", e);
            }
        }

        try {
            OrderVO finalOrderVO = orderVO;
            Boolean isSuccess = template.execute(status -> {

                //商品发放
                boolean handover = numberService.handover(buildHandOverReq(finalOrderVO));

                //订单状态更新
                Result result = orderStateHandler.payComplete(finalOrderVO.getUserId(), finalOrderVO.getOrderSn(), finalOrderVO.getOrderSn(), StarConstants.ORDER_STATE.WAIT_PAY);

                // 给卖家钱包加钱
                String fee = calFee(finalOrderVO.getPayAmount().toString());
                BigDecimal feeBig = new BigDecimal(fee);
                log.info("[marketCashierOrder]给卖家钱包加钱，参数：orderSn-{}, payAmount-{},fee-{}, realAddAmount-{}", finalOrderVO.getOrderSn(), finalOrderVO.getPayAmount(), fee, finalOrderVO.getPayAmount().subtract(feeBig));

                TransReq transReq = createCashierMarketTransReq(finalOrderVO.getOrderSn(), payCheckRes.getSandSerialNo(), finalOrderVO.getPayAmount().subtract(feeBig), Long.valueOf(numberDetail.getOwnerBy()));
                walletService.doC2BTransaction(transReq);

                return ResultCode.SUCCESS.getCode().equals(result.getCode()) && handover;
            });
            return isSuccess;
        } catch (TransactionException | StarException e) {
            throw new RuntimeException("订单处理异常！", e);
        }
    }


    private TransReq createCashierMarketTransReq(String orderSn, String outTradeNo, BigDecimal payAmount, Long uId) {
        // walletPayRequest.setPayAmount(walletPayRequest.getPayAmount().signum() == -1 ? walletPayRequest.getPayAmount().negate() : walletPayRequest.getPayAmount());
        // walletPayRequest.setPayAmount(walletPayRequest.getPayAmount().subtract(walletPayRequest.getFee()));
        // walletPayRequest.setTotalPayAmount(walletPayRequest.getTotalPayAmount().subtract(walletPayRequest.getFee()));
        TransReq transReq = new TransReq();
        transReq.setOrderSn(orderSn);
        transReq.setUid(uId);
        transReq.setOutTradeNo(outTradeNo);
        transReq.setPayAmount(payAmount);
        transReq.setPayChannel("Balance");
        transReq.setTotalAmount(payAmount);
        transReq.setTsType(StarConstants.Transaction_Type.Sell.getCode());
        return transReq;
    }

    private TransReq createMarketTransReq(String orderSn, String outTradeNo, BigDecimal payAmount, Long uId) {
        // walletPayRequest.setPayAmount(walletPayRequest.getPayAmount().signum() == -1 ? walletPayRequest.getPayAmount().negate() : walletPayRequest.getPayAmount());
        // walletPayRequest.setPayAmount(walletPayRequest.getPayAmount().subtract(walletPayRequest.getFee()));
        // walletPayRequest.setTotalPayAmount(walletPayRequest.getTotalPayAmount().subtract(walletPayRequest.getFee()));
        TransReq transReq = new TransReq();
        transReq.setOrderSn(orderSn);
        transReq.setUid(uId);
        transReq.setOutTradeNo(outTradeNo);
        transReq.setPayAmount(payAmount);
        transReq.setPayChannel("CloudAccount");
        transReq.setTotalAmount(payAmount);
        transReq.setTsType(StarConstants.Transaction_Type.Sell.getCode());
        return transReq;
    }

    private HandoverReq buildHandOverReq(OrderVO orderPayReq) {
        HandoverReq handoverReq = new HandoverReq();
        handoverReq.setUid(orderPayReq.getUserId());
        NumberDetailVO numberDetail = numberService.getNumberDetail(orderPayReq.getSeriesThemeId());
        handoverReq.setFromUid(null == numberDetail.getOwnerBy() ? 0L : Long.parseLong(numberDetail.getOwnerBy()));
        handoverReq.setToUid(orderPayReq.getUserId());
        handoverReq.setPreMoney(orderPayReq.getPayAmount());
        handoverReq.setCurrMoney(orderPayReq.getPayAmount());
        handoverReq.setItemStatus(NumberStatusEnum.SOLD.getCode());
        handoverReq.setThemeId(orderPayReq.getSeriesThemeInfoId());
        handoverReq.setNumberId(orderPayReq.getSeriesThemeId());
        handoverReq.setCategoryType(orderPayReq.getThemeType());
        handoverReq.setSeriesId(orderPayReq.getSeriesId());
        handoverReq.setType(NumberCirculationTypeEnum.PURCHASE.getCode());
        handoverReq.setOrderType(orderPayReq.getOrderSn().startsWith(StarConstants.OrderPrefix.PublishGoods.getPrefix(), 0) ?
                StarConstants.OrderType.PUBLISH_GOODS : StarConstants.OrderType.MARKET_GOODS);
        return handoverReq;
    }

    private ActivityEventReq createEventReq(OrderVO orderPayReq) {
        BuyActivityEventReq buyActivityEventReq = new BuyActivityEventReq();
        buyActivityEventReq.setEventSign(StarConstants.EventSign.Buy.getSign());
        buyActivityEventReq.setUserId(orderPayReq.getUserId());
        buyActivityEventReq.setReqTime(new Date());
        buyActivityEventReq.setSeriesId(orderPayReq.getSeriesId());
        buyActivityEventReq.setThemeId(orderPayReq.getSeriesThemeInfoId());
        buyActivityEventReq.setMoney(orderPayReq.getPayAmount());
        return EventReqAssembly.assembly(buyActivityEventReq);
    }


    private OrderListRes buildOrderResp(Long lockTime, ThemeNumberVo numberDetail, Long userId, String orderSn, Long id) {
        OrderListRes res = new OrderListRes();
        res.setId(id);
        res.setOrderSn(orderSn);
        res.setPayAmount(numberDetail.getPrice());
        res.setSeriesId(numberDetail.getSeriesId());
        res.setSeriesThemeInfoId(numberDetail.getThemeInfoId());
        res.setNumberId(numberDetail.getNumberId());
        res.setSeriesThemeId(numberDetail.getNumberId());
        res.setThemeName(numberDetail.getThemeName());
        res.setThemePic(numberDetail.getThemePic());
        res.setThemeType(numberDetail.getThemeType());
        res.setTotalAmount(numberDetail.getPrice());
        res.setThemeNumber(numberDetail.getThemeNumber());
        res.setStatus(0);
        res.setCreatedAt(new Date());
        res.setExpire(lockTime);
        res.setOrderType(StarConstants.OrderType.MARKET_GOODS.getName());
        return res;
    }


    private boolean createPreOrder(ThemeNumberVo numberDetail, Long userId, String orderSn, Long id) {
        OrderVO orderVO = OrderVO.builder()
                .id(id)
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

    // public static void main(String[] args) {
    //     String s = "0.2";
    //     BigDecimal payMoney = new BigDecimal(s);
    //     BigDecimal serviceRate = new BigDecimal("0.03");
    //     BigDecimal transRate = payMoney.multiply(serviceRate);
    //     BigDecimal copyrightRate = payMoney.multiply(serviceRate);
    //     BigDecimal fee = transRate.add(copyrightRate).setScale(2, RoundingMode.HALF_UP);
    //     System.out.println(transRate);
    //     System.out.println(copyrightRate);
    //     System.out.println(fee);
    // }

    /**
     * 计算手续费
     *
     * @param payAmount
     * @return
     */
    public static String calFee(String payAmount) {
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf("CloudAccount"));
        BigDecimal payMoney = new BigDecimal(payAmount);
        BigDecimal transRate = payMoney.multiply(config.getServiceRate());
        BigDecimal copyrightRate = payMoney.multiply(config.getCopyrightRate());
        BigDecimal fee = transRate.add(copyrightRate).setScale(2, RoundingMode.HALF_UP);
        return fee.toString();
    }

    @Override
    public OrderPayDetailRes cloudAccountPay(OrderPayReq req) {

        OrderVO orderVO = orderService.queryOrder(req.getOrderSn());

        // 幂等判断订单状态是否带支付 -> 抛出异常
        if (!StarConstants.ORDER_STATE.WAIT_PAY.getCode().equals(orderVO.getStatus())) {
            throw new StarException(StarError.ORDER_STATUS_REFRESH);
        }

        OrderPayDetailRes res = new OrderPayDetailRes();
        PaymentRes paymentRes = new PaymentRes();
        res.setResults(paymentRes);

        // 0、计算手续费
        String fee = calFee(orderVO.getPayAmount().toString());
        req.setFee(fee);

        // 1、判断买家是否开通云账户，未开通 -> 构建开户链接
        String openCloudAccountUrl = checkBuyerIsOpenCloudAccount(req);
        if (StrUtil.isNotBlank(openCloudAccountUrl)) {
            paymentRes.setJumpUrl(openCloudAccountUrl);
            return res;
        }

        // 2、判断卖家是否开通云账户
        boolean checkSeller = checkSellerIsOpenCloudAccount(req);

        // 这里拼接orderSn + uniqueId
        String orderSn = orderVO.getOrderSn() + "_" + uniqueId.getAndIncrement();
        if (checkSeller) {
            // 3、卖家已开通 -> 用户转账（C2C），构建链接
            C2CTransParam param = new C2CTransParam();
            param.setRecvUserId(req.getOwnerId());
            param.setPayUserId(String.valueOf(req.getUserId()));
            param.setMer_order_no(orderSn);
            param.setOrder_amt(String.valueOf(orderVO.getPayAmount()));
            param.setNotify_url(this.C2CTransNotify);
            param.setReturn_url(req.getReturnUri());
            param.setUserFeeAmt(String.valueOf(req.getFee()));
            String c2cTransUrl = SandC2CTrans.buildTransUrl(param);
            paymentRes.setJumpUrl(c2cTransUrl);
            return res;
        } else {
            // 4、卖家未开通 -> 用户消费（C2B），构建链接
            C2BTransParam param = new C2BTransParam();
            param.setPayUserId(String.valueOf(req.getUserId()));
            param.setMer_order_no(orderSn);
            param.setOrder_amt(String.valueOf(orderVO.getPayAmount()));
            param.setNotify_url(this.C2BTransNotify);
            param.setReturn_url(req.getReturnUri());
            String c2bTransUrl = SandC2BTrans.buildTransUrl(param);
            paymentRes.setJumpUrl(c2bTransUrl);
            return res;
        }
    }

    @Override
    public OrderPayDetailRes sandCashierPay(OrderPayReq req) {

        // 0、是否实名认证
        UserInfo userInfo = userService.queryUserInfoAll(req.getUserId());
        if (userInfo.getRealPersonFlag() == 0) {
            throw new StarException(StarError.IS_REAL_NAME_AUTHENTICATION);
        }

        // 1、获取订单
        OrderVO orderVO = orderService.queryOrder(req.getOrderSn());

        // 2、幂等判断订单状态是否带支付 -> 抛出异常
        if (!StarConstants.ORDER_STATE.WAIT_PAY.getCode().equals(orderVO.getStatus())) {
            throw new StarException(StarError.ORDER_STATUS_REFRESH);
        }

        // 3、构建支付链接
        OrderPayDetailRes res = new OrderPayDetailRes();
        PaymentRes paymentRes = new PaymentRes();
        res.setResults(paymentRes);
        String orderSn = orderVO.getOrderSn() + "_" + uniqueId.getAndIncrement();
        SandCashierPayParam param = new SandCashierPayParam();
        param.setMer_order_no(orderSn);
        param.setOrder_amt(String.valueOf(orderVO.getPayAmount()));
        param.setNotify_url(this.sandCashierPayNotify);
        param.setReturn_url(req.getReturnUri());
        param.setUserId(String.valueOf(userInfo.getAccount()));
        param.setIdCard(userInfo.getIdNumber());
        param.setUserName(userInfo.getFullName());
        String c2cTransUrl = SandCashierPay.buildCashierPayUrl(param);
        paymentRes.setJumpUrl(c2cTransUrl);
        return res;

    }

    /**
     * 校验卖家是否开通云账户
     *
     * @param req
     * @return
     */
    private boolean checkSellerIsOpenCloudAccount(OrderPayReq req) {
        UserInfo userInfo = userService.queryUserInfoAll(Long.valueOf(req.getOwnerId()));
        if (userInfo.getRealPersonFlag() == 0) {
            return false;
        }

        CloudAccountStatusReq cloudAccountStatusReq = new CloudAccountStatusReq();
        cloudAccountStatusReq.setIdCard(userInfo.getIdNumber());
        cloudAccountStatusReq.setRealName(userInfo.getFullName());
        cloudAccountStatusReq.setUserId(String.valueOf(req.getOwnerId()));
        log.info("[checkSellerIsOpenCloudAccount]cloudAccountStatusRes 入参:{}", JSONUtil.toJsonStr(cloudAccountStatusReq));
        CloudAccountStatusRes cloudAccountStatusRes = iSandPayCloudPayHandler.accountStatus(cloudAccountStatusReq);
        log.info("[checkSellerIsOpenCloudAccount]cloudAccountStatusRes 返回值:{}", JSONUtil.toJsonStr(cloudAccountStatusRes));
        return cloudAccountStatusRes.getStatus();
    }


    /**
     * 是否开通云账户
     *
     * @param req
     * @return
     */
    private String checkBuyerIsOpenCloudAccount(OrderPayReq req) {
        UserInfo userInfo = userService.queryUserInfoAll(req.getUserId());
        Optional.ofNullable(userInfo.getRealPersonFlag())
                .filter(a -> Objects.equals(YesOrNoStatusEnum.YES.getCode(), a))
                .orElseThrow(() -> new StarException(StarError.NOT_AUTHENTICATION));

        CloudAccountStatusReq cloudAccountStatusReq = new CloudAccountStatusReq();
        cloudAccountStatusReq.setIdCard(userInfo.getIdNumber());
        cloudAccountStatusReq.setRealName(userInfo.getFullName());
        cloudAccountStatusReq.setUserId(String.valueOf(req.getUserId()));
        CloudAccountStatusRes cloudAccountStatusRes = iSandPayCloudPayHandler.accountStatus(cloudAccountStatusReq);
        log.info("[checkBuyerIsOpenCloudAccount]cloudAccountStatusRes:{}", JSONUtil.toJsonStr(cloudAccountStatusRes));
        if (cloudAccountStatusRes.getStatus()) {
            return null;
        }

        CloudAccountOPenReq cloudAccountOPenReq = new CloudAccountOPenReq();
        cloudAccountOPenReq.setUserId(String.valueOf(req.getUserId()));
        cloudAccountOPenReq.setIdCard(userInfo.getIdNumber());
        cloudAccountOPenReq.setRealName(userInfo.getFullName());
        cloudAccountOPenReq.setReturnUri(req.getReturnUri());
        CloudAccountOPenRes cloudAccountOPenRes = iSandPayCloudPayHandler.openAccount(cloudAccountOPenReq);
        log.info("[checkBuyerIsOpenCloudAccount]cloudAccountOPenRes:{}", JSONUtil.toJsonStr(cloudAccountOPenRes));
        return cloudAccountOPenRes.getUri();
    }
}
