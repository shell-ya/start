package com.star.nft.test;

import cn.hutool.core.util.IdUtil;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.identify.strategy.SwIdentifyStrategy;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.req.RefundReq;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.payment.model.res.RefundRes;
import com.starnft.star.domain.user.model.dto.UserInfoAddDTO;
import com.starnft.star.infrastructure.repository.UserRepository;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest(classes = {StarApplication.class})
public class CheckIdCardTest {
    @Resource
    SwIdentifyStrategy swIdentifyStrategy;
    @Resource
    IPaymentService paymentService;
    @Resource
    UserRepository userRepository;
    @Test
    public void repoTest() {
        //2022-05-21 17:04:10.372 +0800 [[TID: N/A] [main] INFO  c.s.s.d.i.s.SwIdentifyStrategy- 身份验证回调「{"tradeNo":"977617813713715200","chargeStatus":1,"message":"true","data":{"birthday":"xxxx","country":"饶平县","orderNo":"011653123850766333","handleTime":"2022-05-21 17:04:10","gender":"1","city":"潮州市","remark":"一致","result":"01","province":"广东省","age":"27"},"code":"200000"}
        System.out.println(swIdentifyStrategy.checkNameAndIdCard("黄坤煌", "445122199510295218"));
    }
    @Test
    public void testPay() {
        //2022-05-21 17:04:10.372 +0800 [[TID: N/A] [main] INFO  c.s.s.d.i.s.SwIdentifyStrategy- 身份验证回调「{"tradeNo":"977617813713715200","chargeStatus":1,"message":"true","data":{"birthday":"xxxx","country":"饶平县","orderNo":"011653123850766333","handleTime":"2022-05-21 17:04:10","gender":"1","city":"潮州市","remark":"一致","result":"01","province":"广东省","age":"27"},"code":"200000"}
        PaymentRich req = PaymentRich.builder()
                .totalMoney(new BigDecimal("0.21")).payChannel(StarConstants.PayChannel.BankCard.name())
                .frontUrl("https://www.baidu.com?q=1").clientIp("192.168.1.1")
                .orderSn(IdUtil.getSnowflake(1, 1).nextIdStr()).userId(1L)
                .bankNo("6226220647732790")
                .orderType(StarConstants.OrderType.RECHARGE).build();
//        System.out.println(req.composeCallback());

        PaymentRes union_pay = paymentService.pay(req);
        System.out.println(union_pay);
//        Document document = Jsoup.parse(union_pay.getThirdPage());
//        Element pay_form = document.getElementById("pay_form");
//        String action = pay_form.attr("action");
//        Elements inputs = pay_form.getElementsByTag("input");
////        String action = pay_form.attr("action");
//        HashMap<String, Object> maps = new HashMap<>();
//        for (Element input : inputs) {
//            String key = input.attr("name");
//            String value= input.attr("value");
//            maps.put(key,value);
//        }
//        HttpResponse execute = HttpUtil.createPost(action).form(maps).execute();
//        String location = execute.header("Location");
//        String s = HttpUtil.get("https://cashier.sandpay.com.cn/gateway/api/order/".concat(location));
//
//
////
//        System.out.println(s);

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
        PayCheckReq paymentRich = PayCheckReq.builder().orderSn("983508486673530880").payChannel(StarConstants.PayChannel.BankCard.name()).build();
        PayCheckRes payCheckRes = paymentService.orderCheck(paymentRich);
        System.out.println(payCheckRes);


    }
    @Test
    public void testSaveOrder() {
        UserInfoAddDTO is = UserInfoAddDTO.builder().userId(1000L).nickName("XCSSD").phone("18712923565").createBy(12L).build();
        userRepository.addUserInfo(is);


    }
}
