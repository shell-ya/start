package com.star.nft.test;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.UserGatheringInfoReq;
import com.starnft.star.application.process.user.res.UserGatheringInfoRes;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.template.TemplateHelper;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.domain.rank.core.rank.model.RankItemMetaData;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistoryItem;
import com.starnft.star.domain.rank.core.rank.model.res.RankingsItem;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.interfaces.StarApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.web3j.utils.Strings;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Slf4j
@SpringBootTest(classes = {StarApplication.class})
public class RankTest {
    @Resource
    TemplateHelper templateHelper;
    @Resource
    IRankService rankService;

    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    IUserRepository userRepository;


    @Resource
    UserCore userCore;

    @Test
    public void getBuy(){
        Long m_launch_rank = rankService.getBuyNum("m_launch_rank", Long.parseLong("571629646"));
    }


    @Test
    public void getRank(){
        List<RankingsItem> m_launch_rnak = rankService.getRankDatasByPage("m_launch_rank", 0, 300);
        for (RankingsItem item :
                m_launch_rnak) {
            if (Strings.isEmpty(item.getPhone())) {
                    UserGatheringInfoRes userGatheringInfoRes = userCore.ObtainUserGatheringInfo(new UserGatheringInfoReq(item.getAccount()));
                    rankService.setUserPhoneMapping("m_launch_rank",item.getAccount().toString(),userGatheringInfoRes.getPhone());
                }
            }
    }

    @Test
    public void putBuyNum(){
        rankService.putBuyNum("m_launch",787521075L);
    }

    @Test
    public void user(){
        int num = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            Long userId = RandomUtil.randomLong(9);
            String phone = RandomUtil.randomPhone();
            Boolean register = userCore.isRegister(phone);
            if (register) continue;
            log.info("account:{},phone:{}",userId,phone);
            rankService.setUserPhoneMapping("launch_rank",userId.toString(),phone);
            sb.append(userId.toString()).append(",");
            num++;
        }
        log.info("userIds:{}",sb.toString());
        log.info("{num:{}",num);


    }
    @Test
    public void  setSms() throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("account","123455");
        dataMap.put("password","121212");
        dataMap.put("types","121212");
        List<String> mobiles=new ArrayList<>();
        mobiles.add("17603264064");
        dataMap.put("mobiles",mobiles);
        dataMap.put("snCode","1233444");
        dataMap.put("times","时间");
        dataMap.put("content","内容");
        String process = templateHelper.process("sms/sms_shenzhou_req.ftl", dataMap);
        System.out.println(process);
        HttpResponse result = HttpUtil.createPost("http://userinterface.vcomcn.com/Opration.aspx").contentType("text/xml").body(process).execute();
        System.out.println(result);

    }

    @Test
    public void setValidUser(){
        for (int i = 0; i < 15; i++) {
            RankItemMetaData rankItemMetaData = new RankItemMetaData();
            rankItemMetaData.setMobile(RandomUtil.randomPhone());
            rankItemMetaData.setNickName(RandomUtil.randomName());
            rankItemMetaData.setChildrenId(RandomUtil.randomLong(9));
            rankItemMetaData.setInvitationTime(new Date());
            rankService.put("launch_rank", "294592515",1,rankItemMetaData);
            rankService.validPut("launch_rank","294592515",1,rankItemMetaData);
        }

    }
    //查询订单表成功新人徽章订单 如果购买人有邀请人 添加邀请人元石记录 元石备注 拉新活动-邀请获得300元石

    @Test
    public void setTime(){
        //取排行榜所有id
        List<RankingsItem> launch_rank = rankService.getRankDatasByPage("launch_rank", 0, 100);
        for (RankingsItem item :
                launch_rank) {

            List<InvitationHistoryItem> historyItems = rankService.getRankInvitation("launch_rank", item.getAccount(), 1, 100);

            for (InvitationHistoryItem value :historyItems ) {

                if (Objects.isNull(value.getInvitationTime())){
                    Date date = userRepository.userCreateTime(Long.valueOf(value.getAccount()));
                    if (Objects.isNull(date)) continue;;
                    RankItemMetaData rankItemMetaData = new RankItemMetaData();
                    rankItemMetaData.setInvitationTime(date);
                    rankItemMetaData.setChildrenId(Long.valueOf(value.getAccount()));
                    rankItemMetaData.setNickName(value.getNickName());
                    rankItemMetaData.setMobile(value.getMobile());
//                    redisTemplate.opsForHash().delete(String.format(RedisKey.RANK_TOTAL_USER.getKey(), "launch_rank", item.getAccount()),rankItemMetaData.getChildrenId().toString());
//                    redisTemplate.opsForHash().put(String.format(RedisKey.RANK_TOTAL_USER.getKey(), "launch_rank", item.getAccount()), rankItemMetaData.getChildrenId().toString(), JSONUtil.toJsonStr(rankItemMetaData));
                    rankService.putTime("launch_rank",item.getAccount().toString(),rankItemMetaData);
                }
            }
            //遍历集合取总邀请
//            Cursor<Map.Entry<Object,Object>> total = redisTemplate.opsForHash().scan(
//                    String.format(RedisKey.RANK_TOTAL_USER.getKey(), "launch_rank",item.getAccount()), ScanOptions.NONE);
//            while (total.hasNext()){
//                Map.Entry<Object, Object> totalEntry = total.next();
//                RankItemMetaData val = JSONUtil.toBean(totalEntry.getValue().toString(), RankItemMetaData.class);
//                //邀请人是否有时间 有跳过
//                if (Objects.isNull(val.getInvitationTime())){
//                    //数据库查出注册时间 加载到缓存
//                    Date date = userRepository.userCreateTime(Long.valueOf(val.getChildrenId()));
//                    if (Objects.isNull(date)) continue;
//                    val.setInvitationTime(date);
//
//                    redisTemplate.opsForHash().put(String.format(RedisKey.RANK_TOTAL_USER.getKey(), "launch_rank", item.getAccount()), val.getChildrenId().toString(), JSONUtil.toJsonStr(val));
//                }
//            }
//            try {
//                total.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }

    }
}
