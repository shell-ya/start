package com.star.nft.test;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.identify.strategy.SwIdentifyStrategy;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.handler.ISandPayCloudPayHandler;
import com.starnft.star.domain.payment.model.req.*;
import com.starnft.star.domain.payment.model.res.*;
import com.starnft.star.domain.user.model.dto.UserInfoAddDTO;
import com.starnft.star.infrastructure.repository.UserRepository;
import com.starnft.star.interfaces.StarApplication;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;

@SpringBootTest(classes = {StarApplication.class})
public class CloudAccountTest {
    @Resource
    SwIdentifyStrategy swIdentifyStrategy;
    @Resource
    IPaymentService paymentService;
    @Resource
    UserRepository userRepository;
    @Resource
    ISandPayCloudPayHandler iSandPayCloudPayHandler;
    @Test
    public void repoTest() {
        //2022-05-21 17:04:10.372 +0800 [[TID: N/A] [main] INFO  c.s.s.d.i.s.SwIdentifyStrategy- 身份验证回调「{"tradeNo":"977617813713715200","chargeStatus":1,"message":"true","data":{"birthday":"xxxx","country":"饶平县","orderNo":"011653123850766333","handleTime":"2022-05-21 17:04:10","gender":"1","city":"潮州市","remark":"一致","result":"01","province":"广东省","age":"27"},"code":"200000"}
        System.out.println(swIdentifyStrategy.checkNameAndIdCard("黄坤煌", "445122199510295218"));
    }
    @Test
    //用户向平台付款
    public void testC2BPay() {
        HashMap<@Nullable String, @Nullable Object> maps = Maps.newHashMap();
        maps.put("nickName","dasdsad");
        maps.put("userId","1");

        //2022-05-21 17:04:10.372 +0800 [[TID: N/A] [main] INFO  c.s.s.d.i.s.SwIdentifyStrategy- 身份验证回调「{"tradeNo":"977617813713715200","chargeStatus":1,"message":"true","data":{"birthday":"xxxx","country":"饶平县","orderNo":"011653123850766333","handleTime":"2022-05-21 17:04:10","gender":"1","city":"潮州市","remark":"一致","result":"01","province":"广东省","age":"27"},"code":"200000"}
        PaymentRich req = PaymentRich.builder()
                .totalMoney(new BigDecimal("0.21")).payChannel(StarConstants.PayChannel.CloudAccount.name())
                .frontUrl("https://www.circlemeta.cn").clientIp("192.168.1.1")
                .orderSn(IdUtil.getSnowflake(1, 1).nextIdStr()).userId(1L)
                .bankNo("6226220647732790")
                .payExtend(maps)
                .orderType(StarConstants.OrderType.PUBLISH_GOODS).build(); //发行商品购买
        PaymentRes union_pay = paymentService.pay(req);
        System.out.println(union_pay);

    }
    @Test
    //用户向用户付款
    public void testC2CPay() {
        HashMap<@Nullable String, @Nullable Object> maps = Maps.newHashMap();

        //{
//                "payeeList": [{
//                "recvUserId":"sd23882",
//                 "recvCustomerOrderNo":"123456",
//                  "recvAmt":"0.01",
//                 "remark":"备注"
//            }
//],
//                "payer":{
//                "payUserId":"1",
//                "accountType":"01",
//                 "remark":"备注"
//            }，
//              "orderDesc":"订单描述",
//               "accountingMode":"1",
//               "payeeBizUserNo":"123"
//            }

   //     "{\"payeeList\":[{\"recvUserId\":\"4\",\"recvCustomerOrderNo\":\"123456\",\"recvAmt\":\"100\",\"remark\":\"备注\"},{\"recvUserId\":\"6888800012098\",\"recvCustomerOrderNo\":\"123457\",\"recvAmt\":\"15\",\"remark\":\"备注\"}],\"payer\":{\"payUserId\":\"abcd13\",\"accountType\":\"01\",\"remark\":\"备注\"},\"orderDesc\":\"订单描述\",\"accountingMode\":\"1\",\"payeeBizUserNo\":\"4\"}"
        maps.put("cost","0.07");
        maps.put("remark","某某某编号#xx");
        maps.put("accUserId","2");
        //2022-05-21 17:04:10.372 +0800 [[TID: N/A] [main] INFO  c.s.s.d.i.s.SwIdentifyStrategy- 身份验证回调「{"tradeNo":"977617813713715200","chargeStatus":1,"message":"true","data":{"birthday":"xxxx","country":"饶平县","orderNo":"011653123850766333","handleTime":"2022-05-21 17:04:10","gender":"1","city":"潮州市","remark":"一致","result":"01","province":"广东省","age":"27"},"code":"200000"}
        PaymentRich req = PaymentRich.builder()
                .totalMoney(new BigDecimal("1")).payChannel(StarConstants.PayChannel.CloudAccount.name())
                .frontUrl("https://www.circlemeta.cn").clientIp("192.168.1.1")
                .orderSn(IdUtil.getSnowflake(1, 1).nextIdStr()).userId(1L)
                .payExtend(maps)
                .orderType(StarConstants.OrderType.MARKET_GOODS).build();//市场商品
        PaymentRes union_pay = paymentService.pay(req);
        System.out.println(union_pay);

    }



    @Test
    public void testAccountStatus() {
        CloudAccountStatusReq cloudAccountStatusReq = new CloudAccountStatusReq();
        cloudAccountStatusReq.setIdCard("445122199510295218");
        cloudAccountStatusReq.setRealName("黄坤煌");
        cloudAccountStatusReq.setUserId("1");
        CloudAccountStatusRes cloudAccountStatusRes = iSandPayCloudPayHandler.accountStatus(cloudAccountStatusReq);

    }
    @Test
    public void testAccountOPen() {
        CloudAccountOPenReq cloudAccountOPenReq = new CloudAccountOPenReq();
        cloudAccountOPenReq.setUserId("1");
//        cloudAccountOPenReq.setIdCard("410727198903044938");
//        cloudAccountOPenReq.setRealName("薛明阳");
        cloudAccountOPenReq.setIdCard("445122199510295218");
        cloudAccountOPenReq.setRealName("黄坤煌");
        cloudAccountOPenReq.setReturnUri("https://www.baidu.com");
        CloudAccountOPenRes cloudAccountOPenRes = iSandPayCloudPayHandler.openAccount(cloudAccountOPenReq);
        System.out.println(cloudAccountOPenRes);
    }
    @Test
    public void testAccountBalance() {
        CloudAccountBalanceReq cloudAccountBalanceReq = new CloudAccountBalanceReq();
        cloudAccountBalanceReq.setUserId("1");
        CloudAccountBalanceRes cloudAccountBalanceRes = iSandPayCloudPayHandler.queryBalance(cloudAccountBalanceReq);
        System.out.println(cloudAccountBalanceRes);
    }
    @Test
    public void testRefund() {
        RefundReq build = RefundReq.builder()
                .refundOrderSn(IdUtil.getSnowflake(1, 1).nextIdStr())
                .orderSn("RC988813119503597568")
                .composeCallback("11")
                .reason("退款")
                .payChannel(StarConstants.PayChannel.BankCard.name())
                .totalMoney(new BigDecimal("0.15"))
                .build();
        RefundRes refund = paymentService.refund(build);
    }
    @Test
    public void testOrder() {
        PayCheckReq paymentRich = PayCheckReq.builder().orderSn("RC998594608017571840").payChannel(StarConstants.PayChannel.CheckPay.name()).build();
        PayCheckRes payCheckRes = paymentService.orderCheck(paymentRich);
        System.out.println(payCheckRes);


    }
    @Test
    public void testSaveOrder() {
        UserInfoAddDTO is = UserInfoAddDTO.builder().userId(1000L).nickName("XCSSD").phone("18712923565").createBy(12L).build();
        userRepository.addUserInfo(is);


    }
}
