package com.starnft.star.application.mq.consumer;

import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@RocketMQMessageListener(topic = "STAR-SEC-KILL", consumerGroup = "star-consumer-seckill-group", selectorExpression = "ordered")
public class OrderSecKillConsumer implements RocketMQListener<OrderMessageReq> {

    private static final Logger log = LoggerFactory.getLogger(OrderSecKillConsumer.class);
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IOrderService orderService;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> map;


    @Override
    public void onMessage(OrderMessageReq message) {

        long userId = message.getUserId();
        String time = message.getTime();
        Long themeId = message.getGoods().getThemeId();
        SecKillGoods goods = message.getGoods();

        //商品库存队列
        Object stockQueueId = redisUtil.rightPop(String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), themeId));

        if (stockQueueId == null) {
            //清理排队信息
            redisUtil.hdel(RedisKey.SECKILL_ORDER_REPETITION_TIMES.name(), userId);
            return;
        }

        String goodsKey = String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), time);
        if (goodsKey != null) {

            //创建订单
            if (createPreOrder(message)) {
                //减库存
                stockSubtract(userId, time, themeId, goods);
                //缓存写进订单状态

                //发送延时mq 取消订单

                return;
            }
            log.error("创建订单异常: userId: [{}] , themeId: [{}] , context: [{}]", userId, themeId, goods.toString());
            throw new RuntimeException("创建订单异常!");
        }

    }

    private void stockSubtract(long userId, String time, Long themeId, SecKillGoods goods) {
        Long currStock = redisUtil.hincr(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.valueOf(themeId), -1L);
        log.info("商品id:[{}] , 消费该库存user为: [{}], 当前库存为:[{}]", themeId, userId, currStock);

        goods.setStock(currStock.intValue());
        if (currStock <= 0) {
            //同步库存到mysql
        } else {
            //刷新商品库存
            redisUtil.hset(String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), time), String.valueOf(themeId), goods);
        }
    }

    private boolean createPreOrder(OrderMessageReq message) {
        //生成订单流水
        String orderSn = StarConstants.OrderPrefix.PublishGoods.getPrefix()
                .concat(String.valueOf(map.get(StarConstants.Ids.SnowFlake).nextId()));

        OrderVO orderVO = OrderVO.builder()
                .userId(message.getUserId())
                .orderSn(orderSn)
                .payAmount(message.getGoods().getSecCost())
                .seriesId(message.getGoods().getSeriesId())
                .seriesName(message.getGoods().getSeriesName())
                .seriesThemeInfoId(message.getGoods().getThemeId())
                .themeName(message.getGoods().getThemeName())
                .payAmount(message.getGoods().getSecCost())
                .themePic(message.getGoods().getThemePic())
                .themeType(message.getGoods().getThemeType())
                .totalAmount(message.getGoods().getSecCost())
                //todo 排队号
                .themeNumber(1)
                .build();
        //创建订单
        boolean isSuccess = orderService.createOrder(orderVO);
        if (isSuccess) {
            redisUtil.hset(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), String.valueOf(message.getUserId()), orderVO);
            return true;
        }
        return false;
    }

}
