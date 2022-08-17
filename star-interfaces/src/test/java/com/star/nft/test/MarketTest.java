package com.star.nft.test;

import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.order.impl.OrderProcessor;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderGrabReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderGrabRes;
import com.starnft.star.application.process.order.white.rule.WhiteRuleContext;
import com.starnft.star.application.process.task.activity.ActivitiesTask;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.domain.number.model.req.NumberConsignmentCancelRequest;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.service.model.res.OrderPlaceRes;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.interfaces.StarApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static java.lang.Thread.sleep;

/**
 * @Date 2022/7/4 2:05 PM
 * @Author ： shellya
 */
@Slf4j
@SpringBootTest(classes = {StarApplication.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MarketTest {

    final OrderProcessor orderProcessor;

    final INumberCore numberCore;

    final ActivitiesTask activitiesTask;

    final IUserService userService;

    final WhiteRuleContext whiteRuleContext;

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
        marketOrderReq.setNumberId(1006212009283489792L);
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
        orderPayReq.setOrderSn("TS1006694650746142720");
        orderPayReq.setPayAmount("123");
        orderPayReq.setUserId(281850262L);
        orderPayReq.setCategoryType(1);
        orderPayReq.setNumberId(1006212009283489792L);
        orderPayReq.setChannel("Balance");
//        orderPayReq.setFromUid(0L);
        orderPayReq.setSeriesId(4L);
        orderPayReq.setFee("0");
        orderPayReq.setThemeId(1002285892654821376L);
        orderPayReq.setTotalPayAmount("123");
        orderPayReq.setType(3);
//        orderPayReq.setOwnerId(294592515L);
//        orderPayReq.setToUid(320266671L);
//        orderPayReq.setPayToken();
//        orderPayReq.setOutTradeNo();
        orderProcessor.orderPay(orderPayReq);
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
