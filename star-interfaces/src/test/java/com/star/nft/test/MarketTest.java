package com.star.nft.test;

import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.order.impl.OrderProcessor;
import com.starnft.star.application.process.order.model.req.OrderCancelReq;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.task.activity.ActivitiesTask;
import com.starnft.star.common.utils.StarUtils;
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

    @Test
    public void consigment() {
        NumberConsignmentRequest numberConsignmentRequest = new NumberConsignmentRequest();
        numberConsignmentRequest.setUid(409412742L);
        numberConsignmentRequest.setNumberId(991131543080087552L);
        numberConsignmentRequest.setPrice(BigDecimal.valueOf(55.8));
        Boolean consignment = numberCore.consignment(numberConsignmentRequest);
        assert consignment;
    }

    @Test
    public void marketOrder() {
        MarketOrderReq marketOrderReq = new MarketOrderReq();
        marketOrderReq.setUserId(409412742L);
        marketOrderReq.setNumberId(991131540524027904L);
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
        orderPayReq.setOrderSn("TS993651079927463936");
        orderPayReq.setPayAmount("22.00");
        orderPayReq.setUserId(409412742L);
        orderPayReq.setCategoryType(1);
        orderPayReq.setNumberId(991131543080087552L);
        orderPayReq.setChannel("Market");
        orderPayReq.setFromUid(248906830L);
        orderPayReq.setSeriesId(4L);
        orderPayReq.setFee("0.0");
        orderPayReq.setThemeId(991131478355697664L);
        orderPayReq.setTotalPayAmount("22.00");
        orderPayReq.setType(3);
        orderPayReq.setToUid(409412742L);
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
}
