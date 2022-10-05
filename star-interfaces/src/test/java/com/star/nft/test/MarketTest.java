package com.star.nft.test;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.order.impl.OrderProcessor;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.application.process.order.white.rule.WhiteRuleContext;
import com.starnft.star.application.process.task.activity.ActivitiesTask;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.notify.model.req.NotifyOrderReq;
import com.starnft.star.domain.notify.service.NotifyOrderService;
import com.starnft.star.domain.number.model.req.NumberConsignmentCancelRequest;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.domain.payment.model.res.NotifyRes;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.interfaces.StarApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

/**
 * @Date 2022/7/4 2:05 PM
 * @Author ： shellya
 */
@Slf4j
@SpringBootTest(classes = {StarApplication.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MarketTest {
    @Resource
    IMessageSender messageSender;
    final OrderProcessor orderProcessor;

    final INumberCore numberCore;

    final ActivitiesTask activitiesTask;

    final IUserService userService;

    final WhiteRuleContext whiteRuleContext;

    final RedisTemplate<String, Object> redisTemplate;
    @Resource
    NotifyOrderService notifyOrderService;

    @Test
    public void mqNotify(){

        PayCheckRes payCheckRes = new PayCheckRes();
        payCheckRes.setOrderSn("1026959833821249537");
        payCheckRes.setPayChannel("CloudAccount");
        payCheckRes.setTotalAmount(new BigDecimal("1"));
        payCheckRes.setTransSn("CEAS22100420380618300000148191");
        payCheckRes.setMessage("市场订单");
        payCheckRes.setUid("281850262");
        payCheckRes.setStatus(0);

        NotifyOrderReq req = NotifyOrderReq.builder()
                .orderSn(payCheckRes.getOrderSn())
                .payChannel(payCheckRes.getPayChannel())
                .createTime(new Date())
                .message(payCheckRes.getMessage())
                .payTime(new Date())
                .status(payCheckRes.getStatus())
                .totalAmount(payCheckRes.getTotalAmount())
                .transSn(payCheckRes.getTransSn())
                .uid(Long.parseLong(payCheckRes.getUid()))
                .build();

        NotifyRes transform = new NotifyRes();

        transform.setTopic(String.format(TopicConstants.WALLER_PAY_DESTINATION.getFormat(), TopicConstants.WALLER_PAY_DESTINATION.getTag()));
        transform.setPayCheckRes(payCheckRes);

        messageSender.asyncSend(transform.getTopic(), Optional.of(transform.getPayCheckRes()),
                sendResult -> notifyOrderService.sendStatus(payCheckRes.getOrderSn(), 1001L),
                () -> notifyOrderService.sendStatus(payCheckRes.getOrderSn(), 1002L));
    }
    @Test
    public void add(){
        Long add = redisTemplate.opsForSet().add(RedisKey.GIVEN_MANAGE_CONFIG.getKey(), "1009469098485923840");
//        redisTemplate.opsForValue().setBit(String.format(RedisKey.GIVEN_MANAGE_BIT_CONFIG.getKey(), 1009469098485923840L), 536952750L, true);

    }

    @Test
    public void whihte(){
        whiteRuleContext.loadRuleClass("1006594638578057216","worldCreationWhiteRule");
    }

    @Test
    public void consigment() {
        NumberConsignmentRequest numberConsignmentRequest = new NumberConsignmentRequest();
        numberConsignmentRequest.setUid(294592515L);
        numberConsignmentRequest.setNumberId(1006212009283489792L);
        numberConsignmentRequest.setPrice(BigDecimal.valueOf(123L));
        Boolean consignment = numberCore.consignment(numberConsignmentRequest);
        assert consignment;
    }

    @Test
    public void cancelConsigment(){
        NumberConsignmentCancelRequest numberConsignmentCancelRequest = new NumberConsignmentCancelRequest();
        numberConsignmentCancelRequest.setNumberId(994398100683149312L);
        numberConsignmentCancelRequest.setUid(409412742L);
        assert  numberCore.consignmentCancel(numberConsignmentCancelRequest);
    }

    @Test
    public void marketOrder() {
        MarketOrderReq marketOrderReq = new MarketOrderReq();
        marketOrderReq.setUserId(281850262L);
        marketOrderReq.setNumberId(1019309566286630912L);
        marketOrderReq.setOwnerId(536952750L);
        OrderListRes orderListRes = orderProcessor.marketOrder(marketOrderReq);
        log.info("orderList:{}", orderListRes.toString());
    }

    @Test
    public void cancelOrder() {
        OrderCancelReq orderCancelReq = new OrderCancelReq();
        orderCancelReq.setUid(248906830L);
        orderCancelReq.setOrderSn("TS993518961750605824");
        OrderPlaceRes orderPlaceRes = orderProcessor.cancelSecOrder(orderCancelReq);
        log.info(orderPlaceRes.toString());
    }

    @Test
    public void pay() {
        OrderPayReq orderPayReq = new OrderPayReq();
        orderPayReq.setOrderSn("TS1026806789070553088");
        orderPayReq.setPayAmount("1");
        orderPayReq.setUserId(281850262L);
        orderPayReq.setCategoryType(1);
        orderPayReq.setNumberId(1019309566286630912L);
        orderPayReq.setChannel("CloudAccount");
//        orderPayReq.setFromUid(0L);
        orderPayReq.setSeriesId(9L);
        orderPayReq.setFee("0");
        orderPayReq.setThemeId(1019307554112081920L);
        orderPayReq.setTotalPayAmount("1");
        orderPayReq.setType(3);
        orderPayReq.setOwnerId("536952750");
//        orderPayReq.setToUid(320266671L);
//        orderPayReq.setPayToken();
//        orderPayReq.setOutTradeNo();
        OrderPayDetailRes orderPayDetailRes = orderProcessor.orderPay(orderPayReq);
        System.out.println(orderPayDetailRes);
    }

    @Test
    public void task(){
        activitiesTask.loadActivities();
    }

    @Test
    public void userlogin(){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setLoginScenes(1);

        userLoginDTO.setPassword(   StarUtils.getSHA256Str("Zz1208084818"));
        userLoginDTO.setPhone("18332204521");
        userService.login(userLoginDTO);
    }

    @Test
    public void kill() throws InterruptedException {
        OrderGrabReq orderGrabReq = new OrderGrabReq();
        orderGrabReq.setUserId(320266671L);
        orderGrabReq.setThemeId(995367819184877568L);
        orderGrabReq.setTime("2022070920");
//        OrderGrabRes orderGrabRes = orderProcessor.orderGrab(orderGrabReq);
//        log.info("order:{}",orderGrabRes.toString());
//
//        sleep(15L);
        OrderListRes orderListRes = orderProcessor.obtainSecKillOrder(orderGrabReq);
        log.info("order:{}",orderListRes.toString());
    }

    @Test
    public void number(){

        numberCore.putNumber(1006594638578057216L,"2022081120","2022081121",200,48);
    }
}
