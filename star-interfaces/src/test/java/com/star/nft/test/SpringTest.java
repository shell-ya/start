package com.star.nft.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.notification.vo.NotificationVO;
import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.order.model.req.OrderPayReq;
import com.starnft.star.application.process.order.model.res.OrderPayDetailRes;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.application.process.task.activity.RaisingTask;
import com.starnft.star.application.process.theme.ThemeCore;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.template.FreeMakerTemplateHelper;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.draw.model.req.DrawAwardExportsReq;
import com.starnft.star.domain.draw.model.vo.DrawAwardExportVO;
import com.starnft.star.domain.draw.service.draw.IDrawExec;
import com.starnft.star.domain.number.model.vo.ReNumberVo;
import com.starnft.star.domain.number.serivce.INumberService;
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
import com.starnft.star.domain.theme.service.ThemeService;
import com.starnft.star.domain.wallet.model.req.CalculateReq;
import com.starnft.star.domain.wallet.model.res.CalculateResult;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.infrastructure.repository.ActivityRepository;
import com.starnft.star.interfaces.StarApplication;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
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
    final RaisingTask task;
    final ChannelConf channelConf;
    final FreeMakerTemplateHelper templateHelper;
    final IPaymentRouter paymentRouter;
    final PayConf payConf;
    final MessageAdapter messageAdapter;
    final WalletService walletService;
    final ThemeCore themeCore;
    final IScopeCore iScopeCore;
    final INumberCore iNumberCore;
    final IOrderRepository orderRepository;
    final OrderService orderService;
    final INumberService numberService;
    final RedisUtil redisUtil;
    final IOrderProcessor orderProcessor;
    final IDrawExec drawExec;
    final ActivityRepository repository;
    final ThemeService themeService;
    //先删除开多次盲盒的藏品记录 与开奖记录按时间顺序对出的开奖记录直接删掉并删掉得到的藏品
    //查询8月20号盲盒购买次数与开奖次数 开奖次数大于盲盒购买次数 删除抽奖记录 回收对应藏品
    //按
    @Test
    public void query(){
        DrawAwardExportsReq drawAwardExportsReq = new DrawAwardExportsReq();
        drawAwardExportsReq.setPage(1);
        drawAwardExportsReq.setSize(10);

        List<Long> ids = new ArrayList<>();
        List<BuyNum> buyNums = orderRepository.queryBuyNum();
        for (BuyNum num :
                buyNums) {

            Integer integer = repository.queryUserExportNum(num.getUserId());
            log.info("用户[{}]，购买盲盒[{}],开奖[{}]",num.getUserId(),num.getCount(),integer);
            if (integer > num.getCount()){
                ids.add(num.getUserId());
            }
        }
        log.info("脚本用户数量[{}],ids:[{}]",ids.size(),ids);


    }

    @Test
    public void falg(){
        task.raising();
    }




    @Test
    public void repay(){
        //查询出所有重复编号的系列藏品记录
        ArrayList<Long> longs = Lists.newArrayList(1010649211820904448L,
                1010649972045631488L,
                1010650590885621760L,
                1010650857499136000L,
                1010652527359995904L,
                1010653238006226944L,
                1010653417656455168L,
                1010653593949822976L,
                1010653815531855872L,
                1010653959489531904L,
                1010654524279754752L,
                1010655002526818304L,
                1009930908833669120L,
                1009930352590131200L);

        for (Long themeId:
             longs) {
            List<ReNumberVo> reNumberVos = numberService.queryReNumberList(themeId);
            for (ReNumberVo numberVo :
                    reNumberVos) {
                //查询出拥有重复编号的用户id
                List<Long> ids = numberService.queryHasReNumberUser(numberVo.getSeriesThemeId());
                if (numberVo.getNums() != ids.size()) log.info("重复数量不一致：[{}],主题编号：【{}】 ",numberVo.getSeriesThemeId(),numberVo.getSeriesThemeInfoId());
                boolean b = iNumberCore.reNumber(numberVo, ids);
                if (!b) log.error("重新分配失败：{},主题编号：【{}】",numberVo.getSeriesThemeId(),numberVo.getSeriesThemeInfoId());

            }
        }



    }


    @Test
    public void docache(){
        String key = String.format(RedisKey.DRAW_AWARD_STOCK_MAPPING.getKey(), "10000003");
        redisUtil.hincr(key,"2001",200L);
        redisUtil.hincr(key,"2002",300L);
        redisUtil.hincr(key,"2003",38L);
        redisUtil.hincr(key,"2004",38L);
        redisUtil.hincr(key,"2005",38L);
        redisUtil.hincr(key,"2006",38L);
        redisUtil.hincr(key,"2007",38L);
        redisUtil.hincr(key,"2008",38L);
        redisUtil.hincr(key,"2009",38L);
        redisUtil.hincr(key,"2010",38L);
        redisUtil.hincr(key,"2011",38L);
        redisUtil.hincr(key,"2012",38L);
        redisUtil.hincr(key,"2013",38L);
        redisUtil.hincr(key,"2014",38L);
        redisUtil.hincr(key,"2015",3L);
        redisUtil.hincr(key,"2016",10L);
        redisUtil.hincr(key,"2017",3L);
    }


    @Test
    public void remove(){

        HashMap<String,Long> awardMap = new HashMap<>();
        awardMap.put("白羊座徽章",1010649211820904448L);
        awardMap.put("金牛座徽章",1010649972045631488L);
        awardMap.put("处女座徽章",1010650590885621760L);
        awardMap.put("巨蟹座徽章",1010650857499136000L);
        awardMap.put("双子座徽章",1010652527359995904L);
        awardMap.put("射手座徽章",1010653238006226944L);
        awardMap.put("双鱼座徽章",1010653417656455168L);
        awardMap.put("天秤座徽章",1010653593949822976L);
        awardMap.put("狮子座徽章",1010653815531855872L);
        awardMap.put("摩羯座徽章",1010653959489531904L);
        awardMap.put("天蝎座徽章",1010654524279754752L);
        awardMap.put("水瓶座徽章",1010655002526818304L);
        awardMap.put("射手座",1009930352590131200L);
        awardMap.put("双子座",1009930908833669120L);


       int[] ids =  {102025334, 109789191, 110590970, 130421155, 131351562, 136588095, 137179375, 147933371, 151558792, 159338544, 165730990, 173392058, 173543241, 188568867, 189879367, 192070716, 194229777, 197343772, 205287254, 227997530, 231645207, 240352440, 243852831, 244984004, 248632405, 264553132, 268553031, 269918750, 272886885, 273646337, 275480810, 295180868, 296695875, 299803628, 309503360, 310400976, 318260057, 321897373, 330650744, 333575456, 345123301, 350029811, 360399380, 373511906, 374113509, 376625276, 401709661, 405600307, 412888074, 435955980, 439208285, 462642714, 483426529, 511395049, 515199889, 517929188, 518513082, 526954747, 535457663, 543537322, 570380261, 570956444, 583729301, 585329980, 590692550, 595524909, 595954627, 617007749, 625460473, 655346203, 661271244, 668389562, 676544784, 678936686, 683945961, 687215123, 691338771, 739510298, 742277095, 754458519, 775113923, 780067762, 782042095, 784193543, 795024600, 804871655, 805839615, 807087506, 812176224, 812558589, 816310945, 833928006, 847539291, 847830079, 874853139, 878313704, 884363953, 884927709, 887469469, 919311189, 936805191, 937627387, 960188634, 967388706, 980509151, 989020768};

        for (int i = 0; i < ids.length; i++) {
            int id = ids[0];

            Integer count = orderRepository.queryUserBuyBox(String.valueOf(id));

            List<DrawAwardExportVO> drawAwardExportVOS = repository.queryUserExportList((long) id);

            List<DrawAwardExportVO> deleteDraw = drawAwardExportVOS.subList(count, drawAwardExportVOS.size());

            for (DrawAwardExportVO export :
                    deleteDraw) {
                Integer awardType = export.getAwardType();

                if (1 == awardType){
                    //藏品奖励 删除藏品
                    Long seriesThemeInfoId = awardMap.get(export.getAwardName());
                    Long seriesThemeId = numberService.queryUserFirstNumberId((long)id, seriesThemeInfoId);
                    //删除user_thmee中命中主题第一个藏品
                    //恢复theme_number当前用户命中主题第一个藏品
                    numberService.deleteNumber((long)id,seriesThemeId);
//                    repository.deleteExport(export.getOrderId().toString());
                }else if (6 == awardType){
                    //元石奖励 减少元石
                    String num = export.getAwardName().substring(0, export.getAwardName().length() - 2);
                    ScoreDTO addScoreDTO = new ScoreDTO();
                    addScoreDTO.setTemplate("因非法操作减少%s元石");
                    addScoreDTO.setScopeType(0);
                    addScoreDTO.setUserId((long)id);
                    addScoreDTO.setScope(new BigDecimal(num).negate());
                    iScopeCore.userScopeManageAdd(addScoreDTO);
//                    repository.deleteExport(export.getOrderId().toString());
                }else if (4 == awardType){
                    //实物奖励 更新抽奖记录结果 改为非法操作取消奖励
//                    repository.deleteExport(export.getOrderId().toString());
                }

        }

        //查询用户20号盲盒购买数量
        //查询20号开奖记录 开始删除多余盲盒数量的奖励
        //从第11个藏品开始回收
        // 藏品 删除user_theme表数据
        //元石  减去获得
        }

    }

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
