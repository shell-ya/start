package com.star.nft.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.notification.vo.NotificationVO;
import com.starnft.star.application.process.wallet.req.PayRecordReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.template.FreeMakerTemplateHelper;
import com.starnft.star.domain.payment.config.container.PayConf;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.router.IPaymentRouter;
import com.starnft.star.domain.sms.adapter.MessageAdapter;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.ChannelConf;
import com.starnft.star.domain.support.process.config.TempConf;
import com.starnft.star.domain.wallet.model.req.CalculateReq;
import com.starnft.star.domain.wallet.model.res.CalculateResult;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.interfaces.StarApplication;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;


@SpringBootTest(classes = {StarApplication.class})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpringTest {

    final IMessageSender messageSender;

    final ChannelConf channelConf;
    final FreeMakerTemplateHelper templateHelper;
    final IPaymentRouter paymentRouter;
    final PayConf payConf;
    final MessageAdapter messageAdapter;
    final WalletService walletService;

    @Test
    public void payment() {
        CalculateResult calculateResult = walletService.withdrawMoneyCalculate(
                new CalculateReq(new BigDecimal("56.81"), StarConstants.PayChannel.BankCard.name()));
        System.out.println(calculateResult);

    }

    @Test
    public void rechargeCallback() {

        String rechargeCallbackProcessTopic = String.format(TopicConstants.WALLET_RECHARGE_DESTINATION.getFormat(),
                TopicConstants.WALLET_RECHARGE_DESTINATION.getTag());
        PayCheckRes payCheckRes = new PayCheckRes();
        payCheckRes.setStatus(0);
        payCheckRes.setOrderSn("RC985866872677367808");
        payCheckRes.setTotalAmount(new BigDecimal("10.00"));
        payCheckRes.setUid("985174743233269760");
        payCheckRes.setTransSn("0000000000000000000000001");
        payCheckRes.setMessage("good");
        payCheckRes.setPayChannel(StarConstants.PayChannel.BankCard.name());

        messageSender.send(rechargeCallbackProcessTopic, Optional.of(payCheckRes));


    }

    /**
     * D短信测试用例
     */
    @Test
    public void sms() {
        boolean b = messageAdapter.getDistributor().checkCodeMessage("17603219247", "123456");
        System.out.println(b);

    }

    @Test
    @SneakyThrows
    public void tempconf() {
        log.info(JSON.toJSONString(channelConf.getTempConfs()));

        List<TempConf> tempConfs = channelConf.getTempConfs();

        TempConf temp = null;
        for (TempConf tempConf : tempConfs) {
            if (tempConf.getTrade().equals(TradeType.Union_SandPay.name())) {
                temp = tempConf;
            }
        }

        String reqTempPath = temp.getReqTempPath();

        HashMap<@Nullable String, @Nullable String> map =
                Maps.newHashMap();
        map.put("a", "123");
        map.put("b", "234");
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("request", map);

        String process = templateHelper.process(reqTempPath, dataModel);

        System.out.println(process);


    }

    @Test
    public void repoTest() {
//        System.out.println(ConfigurationHolder.getPayConfig().getChannel());
          System.out.println(String.format(TopicConstants.NOTICE_UNREAD_DESTINATION.getFormat(), TopicConstants.NOTICE_UNREAD_DESTINATION.getTag()));
    }

    @Test
    public void test() {
        PayRecordReq payRecordReq = new PayRecordReq();
//        starNftDict.setDictDesc("1");
//        String s = JSON.toJSONString(starNftDict);
//        System.out.println(s);
        JSONObject result = JSON.parseObject(JSON.toJSONString(payRecordReq,
        SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero));
        System.out.println(result.toJSONString());
    }

    @Test
    public void mqTest() {
        messageSender.send("STAR-NOTICE:unread", Optional.ofNullable(NotificationVO.builder()
                .toUid("10001").title("标题")
                .intro("简介").sendTime(new Date()).content("内容").build()));
    }
}
