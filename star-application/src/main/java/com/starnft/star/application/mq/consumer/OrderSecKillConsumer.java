package com.starnft.star.application.mq.consumer;

import cn.hutool.json.JSONUtil;
import com.starnft.star.application.mq.producer.order.OrderProducer;
import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.activity.IActivitiesService;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.user.model.vo.WhiteListConfigVO;
import com.starnft.star.domain.user.service.IUserService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
@RocketMQMessageListener(topic = "${consumer.topic.seckill}", consumerGroup = "${consumer.group.seckill}", selectorExpression = "ordered")
public class OrderSecKillConsumer implements RocketMQListener<OrderMessageReq> {

    private static final Logger log = LoggerFactory.getLogger(OrderSecKillConsumer.class);
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IOrderService orderService;

    @Resource
    private OrderProducer orderProducer;

    @Resource
    private IActivitiesService activitiesService;

    @Resource
    private INumberService numberService;

    @Resource
    private IUserService userService;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> map;


    @Override
    public void onMessage(OrderMessageReq message) {

        long userId = message.getUserId();
        String time = message.getTime();
        Long themeId = message.getGoods().getThemeId();
        SecKillGoods goods = message.getGoods();
        Integer isPriority = message.getIsPriority();

        Object stockQueueId = filterNum(userId, themeId, time);

        if (stockQueueId == null) {
            log.error("队列轮空 uid: [{}] goods : [{}]", userId, goods);
            String key = String.format(RedisKey.SECKILL_ORDER_REPETITION_TIMES.getKey(), themeId);
            //清理排队信息
            redisUtil.hdel(key, String.valueOf(userId));
            //抢单失败
            redisUtil.hset(String.format(RedisKey.SECKILL_ORDER_USER_STATUS_MAPPING.getKey(), themeId),
                    String.valueOf(userId), JSONUtil.toJsonStr(new OrderGrabStatus(userId, -1, null, time)));
            return;
        }

        String goodsKey = String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), time);
        if (goodsKey != null) {
            try {
                //生成订单流水
                String orderSn = StarConstants.OrderPrefix.PublishGoods.getPrefix()
                        .concat(String.valueOf(map.get(StarConstants.Ids.SnowFlake).nextId()));
                //创建订单
                if (createPreOrder(orderSn, message, isPriority, (int) stockQueueId)) {
                    //减库存
                    stockSubtract(userId, time, themeId, goods);
                    //缓存写进订单状态
                    OrderGrabStatus orderGrabStatus = new OrderGrabStatus(userId, 1, orderSn, time);
                    String statusMap = String.format(RedisKey.SECKILL_ORDER_USER_STATUS_MAPPING.getKey(), themeId);
                    redisUtil.hset(statusMap, String.valueOf(userId), JSONUtil.toJsonStr(orderGrabStatus));
                    //发送延时mq 取消订单
                    orderProducer.secOrderRollback(orderGrabStatus);
                    return;
                }
                throw new RuntimeException("创建订单异常!");
            } catch (RuntimeException e) {
                log.error("创建订单异常: userId: [{}] , themeId: [{}] , context: [{}]", userId, themeId, goods.toString(), e);
                throw new RuntimeException(e.getMessage());
            }
        }

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

    private void stockSubtract(long userId, String time, Long themeId, SecKillGoods goods) {
        Long currStock = redisUtil.hincr(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.format("%s-time-%s", themeId, time), -1L);
        log.info("商品id:[{}] , 消费该库存user为: [{}], 当前库存为:[{}]", themeId, userId, currStock);

        goods.setStock(currStock.intValue());
        if (currStock <= 0) {
            //同步库存到mysql
            boolean modifySuccess = activitiesService.modifyStock(goods.getThemeId().intValue(), goods.getStock());
            if (!modifySuccess) {
                log.error("修改库存失败： themeId:[{}] , stock:[{}] ", goods.getThemeId(), goods.getStock());
                throw new RuntimeException("修改库存失败");
            }
        } else {
            //刷新商品库存
            redisUtil.hset(String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), time), String.valueOf(themeId), JSONUtil.toJsonStr(goods));
        }
    }

    private boolean createPreOrder(String orderSn, OrderMessageReq message, Integer isPriority, int stockQueueId) {
        //查询对应藏品编号是否存在
        ThemeNumberVo themeNumberVo = numberService.queryNumberExist(stockQueueId, message.getGoods().getThemeId());

        if (themeNumberVo == null) {
            log.error("藏品可能未上架 themeId:[{}] , themeNumber:[{}]", message.getGoods().getThemeId(), stockQueueId);
            throw new RuntimeException("查询藏品失败！");
        }
        WhiteListConfigVO whiteListConfigVO = userService.obtainWhiteConfig(message.getGoods().getThemeId());
        OrderVO orderVO = OrderVO.builder()
                .id(SnowflakeWorker.generateId())
                .userId(message.getUserId())
                .orderSn(orderSn)
                .payAmount(message.getGoods().getSecCost())
                .seriesId(message.getGoods().getSeriesId())
                .seriesName(message.getGoods().getSeriesName())
                .seriesThemeInfoId(message.getGoods().getThemeId())
                .numberId(themeNumberVo.getNumberId())
                .seriesThemeId(themeNumberVo.getNumberId())
                .themeName(message.getGoods().getThemeName())
                .payAmount(message.getGoods().getSecCost())
                .themePic(message.getGoods().getThemePic())
                .themeType(message.getGoods().getThemeType())
                .totalAmount(message.getGoods().getSecCost())
                .themeNumber(stockQueueId)
                .publisherId(message.getGoods().getPublisherId())
                .status(0)
                .createdAt(new Date())
                .expire(180L)
                .remark(message.getTime()) //暂时存储秒杀时间戳
                .orderType(StarConstants.OrderType.PUBLISH_GOODS.getName())
                .priorityBuy(isPriority)
                .whiteId(whiteListConfigVO.getId())
                .build();
        //创建订单
        boolean isSuccess = orderService.createOrder(orderVO);
        if (isSuccess) {
            String userOrderMapping = String.format(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), message.getGoods().getThemeId());
            redisUtil.hset(userOrderMapping, String.valueOf(message.getUserId()), JSONUtil.toJsonStr(orderVO));
            return true;
        }
        return false;
    }

}
