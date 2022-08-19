package com.starnft.star.application.mq.consumer;

import cn.hutool.core.collection.CollectionUtil;
import com.starnft.star.application.process.draw.vo.DrawConsumeVO;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.domain.draw.service.draw.IDrawExec;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.drawaward}", consumerGroup = "${consumer.group.drawaward}", selectorExpression = "delivery")
public class DrawAwardDeliveryConsumer implements RocketMQListener<DrawConsumeVO> {

    final INumberService numberService;

    final ThemeService themeService;

    final IScopeCore scopeCore;

    final IDrawExec drawExec;


    @Override
    public void onMessage(DrawConsumeVO drawConsumeVO) {

        //奖品id
        String awardId = drawConsumeVO.getDrawAwardVO().getAwardId();
        String uId = drawConsumeVO.getDrawAwardVO().getuId();

        // TODO: 2022/8/19 引入规则引擎drools 针对不同情况执行对应的规则

        // 藏品
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 1) {
            numberService.handover(buildHandOverReq(drawConsumeVO));
            drawExec.updateUserAwardState(uId, drawConsumeVO.getDrawOrderVO().getOrderId(), awardId, StarConstants.GrantState.COMPLETE.getCode());
        }

        // 元石
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 6) {
            scopeCore.userScopeManageAdd(buildScopeReq(drawConsumeVO));
            drawExec.updateUserAwardState(uId, drawConsumeVO.getDrawOrderVO().getOrderId(), awardId, StarConstants.GrantState.COMPLETE.getCode());
        }

        // 实物
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 4) {

        }

    }

    private ScoreDTO buildScopeReq(DrawConsumeVO drawConsumeVO) {
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setUserId(Long.parseLong(drawConsumeVO.getDrawAwardVO().getuId()));
        scoreDTO.setScope(new BigDecimal(drawConsumeVO.getDrawAwardVO().getAwardCount()));
        scoreDTO.setScopeType(0);
        scoreDTO.setTemplate("开启星徽盲盒获得%s元石");
        return scoreDTO;
    }

    private HandoverReq buildHandOverReq(DrawConsumeVO drawConsumeVO) {

        Long awardCategoryId = drawConsumeVO.getDrawAwardVO().getAwardCategoryId();
        ThemeDetailVO themeDetailVO = themeService.queryThemeDetail(awardCategoryId);
        List<NumberVO> numberVOS = numberService.loadNotSellNumberCollection(awardCategoryId);

        if (CollectionUtil.isEmpty(numberVOS)) {
            throw new RuntimeException("藏品余量不足！");
        }
        HandoverReq handoverReq = new HandoverReq();
        handoverReq.setUid(Long.parseLong(drawConsumeVO.getDrawAwardVO().getuId()));
        handoverReq.setSeriesId(themeDetailVO.getSeriesId());
        handoverReq.setType(NumberCirculationTypeEnum.CASTING.getCode());
        handoverReq.setCategoryType(1);
        handoverReq.setCurrMoney(BigDecimal.ZERO);
        handoverReq.setPreMoney(BigDecimal.ZERO);
        handoverReq.setFromUid(0L);
        handoverReq.setToUid(Long.parseLong(drawConsumeVO.getDrawAwardVO().getuId()));
        handoverReq.setThemeId(awardCategoryId);
        handoverReq.setOrderType(StarConstants.OrderType.PUBLISH_GOODS);
        handoverReq.setNumberId(numberVOS.get(0).getId());
        handoverReq.setItemStatus(1);

        return handoverReq;
    }
}
