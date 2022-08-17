package com.star.nft.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.notification.vo.NotificationVO;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.application.process.theme.ThemeCore;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.template.FreeMakerTemplateHelper;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.order.repository.BuyNum;
import com.starnft.star.domain.order.repository.IOrderRepository;
import com.starnft.star.domain.order.service.impl.OrderService;
import com.starnft.star.domain.payment.config.container.PayConf;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.router.IPaymentRouter;
import com.starnft.star.domain.sms.adapter.MessageAdapter;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.ChannelConf;
import com.starnft.star.domain.support.process.config.TempConf;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.wallet.model.req.CalculateReq;
import com.starnft.star.domain.wallet.model.res.CalculateResult;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.interfaces.StarApplication;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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
    final ThemeCore themeCore;

    final IOrderRepository orderRepository;
    final OrderService orderService;

    final RedisUtil redisUtil;
    final IOrderProcessor orderProcessor;

    @Test
    public void add(){
        String buyNumKey = String.format(RedisKey.SECKILL_BUY_GOODS_NUMBER.getKey(),1009469098485923840L);
        List<BuyNum> buyNums = orderRepository.queryBuyNum();
        for (BuyNum num :
                buyNums) {

            redisUtil.hincr(buyNumKey, String.valueOf(num.getUserId()), num.getCount());
        }
//        redisUtil.hincr(buyNumKey, String.valueOf(294592515L), 9L);
//        redisUtil.hincr(buyNumKey, String.valueOf(861596178L), 9L);
    }

    @Test
    void redisTest() {
        Map<Object, Object> before = redisUtil.hmget(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey());
        Map<Object, Object> after = redisUtil.hmget("star-service.seckill:priority:shoufa2");

        for (Map.Entry<Object, Object> objectObjectEntry : before.entrySet()) {
            after.entrySet().forEach(entry -> {
                if (objectObjectEntry.getKey().equals(entry.getKey()) && !objectObjectEntry.getValue().equals(entry.getValue())) {
                    log.info("before uid:[{}] times : [{}], after uid:[{}] times :[{}]",
                            objectObjectEntry.getKey(), objectObjectEntry.getValue(), entry.getKey(), entry.getValue());
                }
            });
        }
    }

    @Test
    void dataCheckTest() {
        Boolean aBoolean = orderProcessor.dataCheck(998977713737334784L, "lywc-22-ck");
        System.out.println(aBoolean);
    }

    @Test
    void orderPay() {
        OrderPayReq orderPayReq = new OrderPayReq();
        orderPayReq.setUserId(248906830L);
        orderPayReq.setOrderSn("TS992560592751558656");
        orderPayReq.setPayAmount("33.00");
        orderPayReq.setChannel(StarConstants.PayChannel.Balance.name());
        orderPayReq.setFee("0.00");
//        orderPayReq.setFromUid(409412742L);
//        orderPayReq.setToUid(248906830L);
        orderPayReq.setTotalPayAmount("33.00");
        orderPayReq.setNumberId(991131539320320000L);
        orderPayReq.setThemeId(991131478355697664L);
        orderPayReq.setSeriesId(4L);
        orderPayReq.setType(3);
        orderPayReq.setCategoryType(1);
        OrderPayDetailRes orderPayDetailRes = orderProcessor.orderPay(orderPayReq);

        System.out.println(JSON.toJSONString(orderPayDetailRes));
    }

    @Test
    public void goods() {
        Set<SecKillGoods> secKillGoods = themeCore.querySecKillThemes();
        secKillGoods.forEach(good -> System.out.println(JSON.toJSONString(good)));

    }

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
        payCheckRes.setOrderSn("RC988226768479584256");
        payCheckRes.setTotalAmount(new BigDecimal("1.00"));
        payCheckRes.setUid("985174743233269760");
        payCheckRes.setTransSn("0000000000000000000000002");
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
        ;
//        starNftDict.setDictDesc("1");
//        String s = JSON.toJSONString(starNftDict);
//        System.out.println(s);
        JSONObject result = JSON.parseObject(JSON.toJSONString(new OrderPayReq(),
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero));
        System.out.println(result.toJSONString());
    }

    @Test
    public void mqTest() {
        messageSender.send("STAR-NOTICE:unread", Optional.ofNullable(NotificationVO.builder()
                .toUid("10001").title("标题")
                .intro("简介").sendTime(new Date()).content("内容").build()));
    }

    @Test
    public void componse(){
        ArrayList<Long> longs = Lists.newArrayList(
                915512099L,
                294592515L,
                336673887L,
                177704908L,
                747556896L,
                551997115L,
                654232977L,
                696700174L,
                189879367L,
                166745224L,
                884363953L,
                673887355L,
                574112667L
        );
        for (Long id :
                longs) {
            redisUtil.sSet(String.format(RedisKey.GOLD_COMPOSE.getKey(),"2"),id);
        }

    }
}
