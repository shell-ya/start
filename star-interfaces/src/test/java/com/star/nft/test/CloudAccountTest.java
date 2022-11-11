package com.star.nft.test;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.identify.strategy.SwIdentifyStrategy;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.handler.ISandPayCloudPayHandler;
import com.starnft.star.domain.payment.helper.SdKeysHelper;
import com.starnft.star.domain.payment.model.req.*;
import com.starnft.star.domain.payment.model.res.*;
import com.starnft.star.domain.user.model.dto.UserInfoAddDTO;
import com.starnft.star.infrastructure.repository.UserRepository;
import com.starnft.star.interfaces.StarApplication;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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

    @Resource
    SdKeysHelper sdKeysHelper;
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

    @SneakyThrows
    @Test
    public void verifySign(){

        String data = "{\"amount\":1,\"feeAmt\":0,\"mid\":\"6888806044846\",\"orderNo\":\"1026959833821249537\",\"orderStatus\":\"00\",\"payeeInfo\":{\"payeeAccName\":\"薛\n" +
                "明阳\",\"payeeAccNo\":\"200229000020004\",\"payeeMemID\":\"536952750\"},\"payerInfo\":{\"payerAccName\":\"张泽\",\"payerAccNo\":\"200229000040003\",\"payerMemID\":\"281850262\"},\"postscript\":\"市场订单\",\"respCode\":\"00000\",\"respMsg\":\"成功\",\"respTime\":\"20221004205550\",\"sandSerialNo\":\"CEAS22100420380618300000148168\",\"transType\":\"C2C_TRANSFER\",\"userFeeAmt\":0.06}";
        String sign = "j4T39ReBgVBKHi3YdzC/MDljkG0Q9qymVnTOk8G1KtCOG+tMS0/jr4zk+cDl2GIw1PD2lSenRPex+4hgZKCUY/8+0W7Nuk7PRqcGgJ0U3DCefVTsjHv7w5H3aFLJyH7cuyWmjtfEAf6+C4f0I4apV0/mJvujHVB6BPwNDtjguKXR3q3xXwJhLjYWqzWRyr0t3cR3DxAanRB1nrqxpSGyIfeJ2U2ZBBQJfKUoMhj4aRelwKs+/tE3Eig3AwmBwFpucXWsZyiG8yqis+qEv+4Hevm2Hp06Wk86D7S6zwSdcPazxUeAwBkf/ioZ++Gs7Mbr+AVf5is1VxozQXOHptPT9w==";
        boolean valid = sdKeysHelper.verifyDigitalSign(
                data.getBytes(StandardCharsets.UTF_8),
                Base64.decodeBase64(sign),
                sdKeysHelper.getProKey(),
                "SHA1WithRSA");
    }



    @Test
    public void testAccountStatus() {
        CloudAccountStatusReq cloudAccountStatusReq = new CloudAccountStatusReq();
        // cloudAccountStatusReq.setIdCard("420683199209100516");
        // cloudAccountStatusReq.setRealName("沈凡");
        // cloudAccountStatusReq.setUserId("977431137");
        cloudAccountStatusReq.setIdCard("410727198903044938");
        cloudAccountStatusReq.setRealName("薛明阳");
        cloudAccountStatusReq.setUserId("536952750");
        CloudAccountStatusRes cloudAccountStatusRes = iSandPayCloudPayHandler.accountStatus(cloudAccountStatusReq);
        System.out.println(JSONUtil.toJsonStr(cloudAccountStatusRes));
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
