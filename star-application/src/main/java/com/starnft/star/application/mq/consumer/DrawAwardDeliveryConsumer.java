package com.starnft.star.application.mq.consumer;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.starnft.star.application.process.coupon.CouponCore;
import com.starnft.star.application.process.draw.vo.DrawConsumeVO;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.CouponGetType;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.coupon.model.dto.CouponHistoryAdd;
import com.starnft.star.domain.draw.model.vo.DrawAwardVO;
import com.starnft.star.domain.draw.service.draw.IDrawExec;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RocketMQMessageListener(topic = "${consumer.topic.drawaward}", consumerGroup = "${consumer.group.drawaward}", selectorExpression = "delivery")
public class DrawAwardDeliveryConsumer implements RocketMQListener<DrawConsumeVO> {

    final INumberService numberService;

    final ThemeService themeService;

    final IScopeCore scopeCore;

    final IDrawExec drawExec;

    final RedisUtil redisUtil;

    final CouponCore couponCore;

    final RedisLockUtils redisLockUtils;


    @Override
    public void onMessage(DrawConsumeVO drawConsumeVO) {

        //奖品id
        String awardId = drawConsumeVO.getDrawAwardVO().getAwardId();
        String uId = drawConsumeVO.getDrawAwardVO().getuId();
        Integer product = drawConsumeVO.getProduct();

        // TODO: 2022/8/19 引入规则引擎drools 针对不同情况执行对应的规则

        // 藏品
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 1) {
            synchronized (this) {
                numberService.handover(buildHandOverReq(drawConsumeVO));
                drawExec.updateUserAwardState(uId, drawConsumeVO.getDrawOrderVO().getOrderId(), awardId, StarConstants.GrantState.COMPLETE.getCode());
            }
        }

        // 元石
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 6) {
            scopeCore.userScopeManageAdd(buildScopeReq(drawConsumeVO));
            drawExec.updateUserAwardState(uId, drawConsumeVO.getDrawOrderVO().getOrderId(), awardId, StarConstants.GrantState.COMPLETE.getCode());
        }

        // 实物
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 4) {

        }

        // 优惠券
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 3) {
            couponCore.addCouponHistory(initCouponReq(uId, drawConsumeVO.getDrawAwardVO().getAwardCategoryId()));
            drawExec.updateUserAwardState(uId, drawConsumeVO.getDrawOrderVO().getOrderId(), awardId, StarConstants.GrantState.COMPLETE.getCode());
        }

        //buff
        if (drawConsumeVO.getDrawAwardVO().getAwardType() == 7) {
            String buffKey = String.format(RedisKey.AWARD_BUFF_KEY.getKey(), awardId, uId);
            redisUtil.incr(buffKey, 1);
        }

    }

    private CouponHistoryAdd initCouponReq(String uId, String awardCategoryId) {
        CouponHistoryAdd couponHistoryAdd = new CouponHistoryAdd();
        couponHistoryAdd.setGetType(CouponGetType.ACTIVITY.getType());
        couponHistoryAdd.setUserId(Long.parseLong(uId));
        couponHistoryAdd.setCouponId(awardCategoryId);

        return couponHistoryAdd;
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

        Long awardCategoryId = Long.parseLong(drawConsumeVO.getDrawAwardVO().getAwardCategoryId());
        ThemeDetailVO themeDetailVO = themeService.queryThemeDetail(awardCategoryId);
        List<NumberVO> numberVOS = numberService.loadNotSellNumberCollection(awardCategoryId);

        if (CollectionUtil.isEmpty(numberVOS)) {
            throw new RuntimeException("藏品余量不足！");
        }
        Long numberId = randomNumberId(numberVOS);
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
        handoverReq.setNumberId(numberId);
        handoverReq.setItemStatus(1);

        return handoverReq;
    }

    private Long randomNumberId(List<NumberVO> numberVOS){
        Long numberId = numberVOS.get(RandomUtil.randomInt(numberVOS.size())).getId();
        String lockKey = String.format(RedisKey.DRAW_AWARD_NUMBER_LOCK.getKey(), numberId);
        Boolean lock = redisLockUtils.lock(lockKey, RedisKey.DRAW_AWARD_NUMBER_LOCK.getTimeUnit().toSeconds(RedisKey.DRAW_AWARD_NUMBER_LOCK.getTime()));
        if(!lock) throw new StarException(StarError.RANDOM_NUMBER_ERROR);
        return numberId;
    }

}
