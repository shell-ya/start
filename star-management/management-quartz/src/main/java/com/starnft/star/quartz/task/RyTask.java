package com.starnft.star.quartz.task;

import com.google.common.collect.Lists;
import com.starnft.star.business.support.rank.core.IRankService;
import com.starnft.star.business.support.rank.model.RankItemMetaData;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{

    public static int[] number = new int[] {3,5,7,4,6,12,9,8,11,1};

    @Resource
    IRankService rankService;
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

    public void mockRank(String params){
        System.out.println("执行随机排行榜方法：" + params);
        String[] split = params.split(",");
        for (int i = 0; i < split.length; i++) {
            String trim = split[i].trim();
            int index = RandomUtil.randomInt(10);
            int num = number[index];
            for (int j = 0; j <= num; j++) {
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






}
