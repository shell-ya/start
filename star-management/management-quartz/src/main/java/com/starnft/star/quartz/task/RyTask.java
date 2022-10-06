package com.starnft.star.quartz.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.starnft.star.business.domain.StarNftOrder;
import com.starnft.star.business.domain.WhiteListDetail;
import com.starnft.star.business.domain.vo.ErrorOrder;
import com.starnft.star.business.domain.vo.JinUserInfo;
import com.starnft.star.business.domain.vo.UserInfo;
import com.starnft.star.business.service.IStarNftOrderService;
import com.starnft.star.business.service.IStarNftUserThemeService;
import com.starnft.star.business.service.IWhiteService;
import com.starnft.star.business.support.rank.core.IRankService;
import com.starnft.star.business.support.rank.model.RankItemMetaData;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.common.utils.StringUtils;
import com.starnft.star.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Slf4j
@Component("ryTask")
public class RyTask
{

    public static int[] number = new int[] {3,5,7,4,6,12,9,8,11,1};

    @Resource
    IRankService rankService;

    @Resource
    IStarNftUserThemeService userThemeService;

    @Resource
    IStarNftOrderService orderService;

    @Resource
    IWhiteService whiteListDetailService;

    /*
    1: 榜前3， 占据2位
    2: 榜4-10， 占据3位
    3: 榜11 - 50 ， 占据20
     */

    public void bang(String params){
        System.out.println("执行有参方法：" + params);
        String[] split = params.split(",");
        for (int num = 1; num < split.length; num++) {
            String userId = split[num].trim();
            int i = RandomUtil.randomInt(1,Integer.parseInt(split[0]));
            for (int j = 0; j < i; j++) {
                RankItemMetaData rankItemMetaData = new RankItemMetaData();
                rankItemMetaData.setMobile(RandomUtil.randomPhone());
                rankItemMetaData.setNickName(RandomUtil.randomName());
                rankItemMetaData.setChildrenId(RandomUtil.randomLong(9));
                rankItemMetaData.setInvitationTime(new Date());
                rankService.put("m_launch_rank", userId,1,rankItemMetaData);
                rankService.validPut("m_launch_rank",userId,1,rankItemMetaData);
            }
        }
    }


    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
        String[] split = params.split(",");
        for (int num = 1; num < split.length; num++) {

            String trim = split[num].trim();
            int i = RandomUtil.randomInt(1,Integer.parseInt(split[0]));
            for (int j = 0; j < i; j++) {
                    RankItemMetaData rankItemMetaData = new RankItemMetaData();
                    rankItemMetaData.setMobile(RandomUtil.randomPhone());
                    rankItemMetaData.setNickName(RandomUtil.randomName());
                    rankItemMetaData.setChildrenId(RandomUtil.randomLong(9));
                    rankItemMetaData.setInvitationTime(new Date());
                    rankService.put("launch_rank", trim,1,rankItemMetaData);
                    rankService.validPut("launch_rank",trim,1,rankItemMetaData);
            }
        }
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    //设置排行榜id

    public void rankData(String params){
        System.out.println("执行排行榜方法：" + params);
        String[] split = params.split(",");

        for (int num = 2; num < split.length; num++) {
            String trim = split[num].trim();
            int i = RandomUtil.randomInt(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
            for (int j = 0; j < i; j++) {
                RankItemMetaData rankItemMetaData = new RankItemMetaData();
                rankItemMetaData.setMobile(RandomUtil.randomPhone());
                rankItemMetaData.setNickName(RandomUtil.randomName());
                rankItemMetaData.setChildrenId(RandomUtil.randomLong(9));
                rankItemMetaData.setInvitationTime(new Date());
                rankService.put("launch_rank", trim,1,rankItemMetaData);
                rankService.validPut("launch_rank",trim,1,rankItemMetaData);
            }
        }
    }
    public void kuai(String params){
        String[] split = params.split(",");

        List<UserInfo> userInfos = userThemeService.selectHasThemeUser(Long.valueOf(split[0]));

        ConcurrentMap<String, JinUserInfo> map = Maps.newConcurrentMap();
        for (UserInfo user :
                userInfos) {
            JinUserInfo userInfo = new JinUserInfo();
            userInfo.setUserId(user.getUserId());
            userInfo.setUserName(user.getUserName());
            userInfo.setNickName(user.getNickName());
            userInfo.setPhone(user.getPhone());
            userInfo.setPan(user.getNums());
            map.put(user.getUserId(),userInfo);
        }
        List<UserInfo> niu = userThemeService.selectHasThemeUser(Long.valueOf(split[1]));
        for (UserInfo user :
             niu) {
            if (map.containsKey(user.getUserId())){
                JinUserInfo userInfo = map.get(user.getUserId());
                userInfo.setUr(user.getNums());
            }else {
                JinUserInfo userInfo = new JinUserInfo();
                userInfo.setUserId(user.getUserId());
                userInfo.setUserName(user.getUserName());
                userInfo.setNickName(user.getNickName());
                userInfo.setPhone(user.getPhone());
                userInfo.setUr(user.getNums());
                map.put(user.getUserId(),userInfo);
            }
        }
        List<UserInfo> yang = userThemeService.selectHasThemeUser(Long.valueOf(split[2]));
        for (UserInfo user :
                yang) {
            if (map.containsKey(user.getUserId())){
                JinUserInfo userInfo = map.get(user.getUserId());
                userInfo.setXj(user.getNums());

            }else {
                JinUserInfo userInfo = new JinUserInfo();
                userInfo.setUserId(user.getUserId());
                userInfo.setUserName(user.getUserName());
                userInfo.setNickName(user.getNickName());
                userInfo.setPhone(user.getPhone());
                userInfo.setXj(user.getNums());

                map.put(user.getUserId(),userInfo);
            }
        }
        ArrayList<JinUserInfo> jinUserInfos = new ArrayList<>(map.values());

        ExcelUtil<JinUserInfo> util = new ExcelUtil<JinUserInfo>(JinUserInfo.class);
        util.exportExcel(jinUserInfos, "10.06持仓快照", "10.06持仓快照");


    }


    public void errorOrder(){
        //查通用优先购数量
        List<WhiteListDetail> whiteListDetails = whiteListDetailService.queryCommon(2L);
        ArrayList<ErrorOrder> errorOrders = Lists.newArrayList();
        //查指定优先购数量
        for (WhiteListDetail detail   :whiteListDetails
             ) {
            ErrorOrder error = new ErrorOrder();
            error.setUserId(detail.getUid().longValue());
            error.setCommonYou(detail.getSurplusTimes() + detail.getVersion());

            WhiteListDetail zhiding = whiteListDetailService.queryWhite(3L, detail.getUid().longValue());

            if (zhiding != null){
                error.setZhidingYou(zhiding.getSurplusTimes() + zhiding.getVersion());
                error.setMaxOrder(error.getCommonYou() + error.getZhidingYou());
            }else {
                error.setMaxOrder(error.getCommonYou());
            }

            StarNftOrder order = new StarNftOrder();
            order.setSeriesThemeInfoId(1020025455294062592L);
            order.setStatus(1);
            order.setUserId(String.valueOf(detail.getUid()));
            List<StarNftOrder> starNftOrders = orderService.selectStarNftOrderList(order);
            error.setOrderNum(starNftOrders.size());
            List<String> collect = starNftOrders.stream().map(StarNftOrder::getOrderSn).collect(Collectors.toList());
            error.setOrderSnList(collect);

            errorOrders.add(error);

        }
        //查用户订单数量

        log.info("开始生成错误数据excel");
        ExcelUtil<ErrorOrder> util = new ExcelUtil<ErrorOrder>(ErrorOrder.class);
        util.exportExcel(errorOrders, "错误数据");

        log.info("错误数据excel生成完毕");
    }

    public void mockRank(String params){

        System.out.println("执行有参方法：" + params);
        String[] split = params.split(",");
        for (int num = 1; num < split.length; num++) {
            String userId = split[num].trim();
            int i = RandomUtil.randomInt(1,Integer.parseInt(split[0]));
            for (int j = 0; j < i; j++) {
                RankItemMetaData rankItemMetaData = new RankItemMetaData();
                rankItemMetaData.setMobile(RandomUtil.randomPhone());
                rankItemMetaData.setNickName(RandomUtil.randomName());
                rankItemMetaData.setChildrenId(RandomUtil.randomLong(9));
                rankItemMetaData.setInvitationTime(new Date());
                rankService.put("m_launch_rank", userId,1,rankItemMetaData);
                rankService.validPut("m_launch_rank",userId,1,rankItemMetaData);
            }
        }

//        System.out.println("执行随机排行榜方法：" + params);
//        String[] split = params.split(",");
//        for (int i = 0; i < split.length; i++) {
//            String trim = split[i].trim();
//            int index = RandomUtil.randomInt(10);
//            int num = number[index];
//            for (int j = 0; j <= num; j++) {
//                RankItemMetaData rankItemMetaData = new RankItemMetaData();
//                rankItemMetaData.setMobile(RandomUtil.randomPhone());
//                rankItemMetaData.setNickName(RandomUtil.randomName());
//                rankItemMetaData.setChildrenId(RandomUtil.randomLong(9));
//                rankItemMetaData.setInvitationTime(new Date());
//                rankService.put("launch_rank", trim,1,rankItemMetaData);
//                rankService.validPut("launch_rank",trim,1,rankItemMetaData);
//            }
//        }
    }






}
