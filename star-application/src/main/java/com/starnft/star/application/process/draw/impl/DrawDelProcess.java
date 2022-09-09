package com.starnft.star.application.process.draw.impl;

import com.google.common.collect.Lists;
import com.starnft.star.application.process.draw.IDrawDelProcess;
import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.domain.activity.impl.ActivitiesService;
import com.starnft.star.domain.draw.model.vo.DrawAwardExportVO;
import com.starnft.star.domain.number.model.vo.ReNumberVo;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.repository.IOrderRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Date 2022/8/30 12:32 PM
 * @Author ： shellya
 */
@Slf4j
@Service
public class DrawDelProcess implements IDrawDelProcess {

    @Resource
    IOrderRepository orderRepository;
    @Resource
    ActivitiesService repository;
    @Resource
    IScopeCore iScopeCore;
    @Resource
    INumberService numberService;
    @Resource
    INumberCore iNumberCore;
    @Override
    public void delErrorDraw() {
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
//        int[] ids =  {264553132};
        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];

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
                    repository.delErrorExport(String.valueOf(id),export.getOrderId().toString());
                }else if (6 == awardType){
                    //元石奖励 减少元石
                    String num = export.getAwardName().substring(0, export.getAwardName().length() - 2);
                    ScoreDTO addScoreDTO = new ScoreDTO();
                    addScoreDTO.setTemplate("因非法操作减少%s元石");
                    addScoreDTO.setScopeType(0);
                    addScoreDTO.setUserId((long)id);
                    addScoreDTO.setIsSub(true);
                    addScoreDTO.setScope(new BigDecimal(num).negate());
                    iScopeCore.userScopeManageAdd(addScoreDTO);
                    repository.delErrorExport(String.valueOf(id),export.getOrderId().toString());
                }else if (4 == awardType){
                    //实物奖励 更新抽奖记录结果 改为非法操作取消奖励
                    repository.delErrorExport(String.valueOf(id),export.getOrderId().toString());
                }

            }

            //查询用户20号盲盒购买数量
            //查询20号开奖记录 开始删除多余盲盒数量的奖励
            //从第11个藏品开始回收
            // 藏品 删除user_theme表数据
            //元石  减去获得
        }

    }

    @Override
    public void reNumber() {
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
}
