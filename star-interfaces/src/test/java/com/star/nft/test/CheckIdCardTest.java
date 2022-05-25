package com.star.nft.test;

import cn.hutool.core.util.IdUtil;
import com.starnft.star.domain.identify.strategy.SwIdentifyStrategy;
import com.starnft.star.domain.payment.handler.impl.SandPayPaymentHandler;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {StarApplication.class})
public class CheckIdCardTest {
    @Resource
    SwIdentifyStrategy swIdentifyStrategy;
    @Resource
    SandPayPaymentHandler sandPayPaymentHandler;

    @Test
    public void repoTest() {

        //2022-05-21 17:04:10.372 +0800 [[TID: N/A] [main] INFO  c.s.s.d.i.s.SwIdentifyStrategy- 身份验证回调「{"tradeNo":"977617813713715200","chargeStatus":1,"message":"true","data":{"birthday":"xxxx","country":"饶平县","orderNo":"011653123850766333","handleTime":"2022-05-21 17:04:10","gender":"1","city":"潮州市","remark":"一致","result":"01","province":"广东省","age":"27"},"code":"200000"}
        System.out.println(swIdentifyStrategy.checkNameAndIdCard("黄坤煌", "445122199510295218"));

    }
    @Test
    public void testPay() {

        //2022-05-21 17:04:10.372 +0800 [[TID: N/A] [main] INFO  c.s.s.d.i.s.SwIdentifyStrategy- 身份验证回调「{"tradeNo":"977617813713715200","chargeStatus":1,"message":"true","data":{"birthday":"xxxx","country":"饶平县","orderNo":"011653123850766333","handleTime":"2022-05-21 17:04:10","gender":"1","city":"潮州市","remark":"一致","result":"01","province":"广东省","age":"27"},"code":"200000"}
        PaymentRes union_pay = sandPayPaymentHandler.pay(PaymentRich.builder().totalMoney("12.0").payChannel("UNION_PAY").frontUrl("https://mp.lsnft.cn").clientIp("192.168.1.1").orderSn(IdUtil.getSnowflake(1,1).nextIdStr()).tradeSn("12121212234").userId(1L).build());
        System.out.println(union_pay);

    }
}
