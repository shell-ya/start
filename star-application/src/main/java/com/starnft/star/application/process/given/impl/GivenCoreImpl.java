package com.starnft.star.application.process.given.impl;

import com.starnft.star.application.process.article.IUserArticleCore;
import com.starnft.star.application.process.given.IGivenCore;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.given.model.req.GivenMangeReq;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;
import com.starnft.star.domain.number.repository.INumberRepository;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.service.IUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
public class GivenCoreImpl implements IGivenCore {
    @Resource
    IUserService iUserService;
    @Resource
    UserThemeService userThemeService;
    @Resource
    INumberService iNumberService;
    @Resource
    INumberRepository numberRepository;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    TransactionTemplate template;
    @Override
    public Boolean giving(Long userId, GivenMangeReq givenMangeReq) {
        Boolean isGiving = redisTemplate.opsForSet().isMember(RedisKey.GIVEN_MANAGE_CONFIG.getKey(), givenMangeReq.getThemeId());
        Boolean giving = redisTemplate.opsForValue().getBit(String.format(RedisKey.GIVEN_MANAGE_BIT_CONFIG.getKey(), givenMangeReq.getThemeId()), userId);
        Assert.isFalse(giving, () -> new StarException("转赠次数已用完"));
        Assert.isTrue(isGiving, () -> new StarException("藏品不可转赠"));
        UserInfo userInfo = iUserService.queryUserByMobile(givenMangeReq.getMobile());
        Assert.notNull(userInfo, () -> new StarException("用户不存在"));
        UserNumbersVO userNumbersVO = userThemeService.queryUserNumberInfo(userId, givenMangeReq.getNumberId(), UserNumberStatusEnum.PURCHASED);
        Assert.notNull(userNumbersVO, () -> new StarException("藏品不存在，请选择正确的藏品信息"));
        Boolean owner = iNumberService.isOwner(userId, givenMangeReq.getThemeId(), givenMangeReq.getNumberId());
        Assert.isTrue(owner, () -> new StarException("藏品不存在，请选择正确的藏品信息"));
        //修改持有状态
        UserThemeMappingVO userThemeMappingVO = getUserThemeMappingVO(userId, givenMangeReq, userInfo, userNumbersVO);
        Boolean isSuccess = template.execute(status -> {
            boolean modify = userThemeService.modifyUserNumberStatus(userId, givenMangeReq.getNumberId(), BigDecimal.ZERO, UserNumberStatusEnum.PURCHASED, UserNumberStatusEnum.GIVEND);
            boolean created = this.numberRepository.createUserNumberMapping(userThemeMappingVO);
            boolean update = iNumberService.modifyNumberOwnerBy(givenMangeReq.getNumberId(), userInfo.getId(), NumberStatusEnum.SOLD.getCode());
            return modify && created && update;
        });
      Assert.isTrue(Boolean.TRUE.equals(isSuccess),()->new StarException("转赠失败，请稍后再试"));
      return isSuccess;
    }

    private UserThemeMappingVO getUserThemeMappingVO(Long userId, GivenMangeReq givenMangeReq, UserInfo userInfo, UserNumbersVO userNumbersVO) {
        UserThemeMappingVO userThemeMappingVO = new UserThemeMappingVO();
        userThemeMappingVO.setBeforeUserId(userId.toString());
        userThemeMappingVO.setUserId(userInfo.getId().toString());
        userThemeMappingVO.setSeriesId(givenMangeReq.getSeriesId());
        userThemeMappingVO.setSeriesThemeInfoId(userNumbersVO.getThemeId());
        userThemeMappingVO.setSeriesThemeId(userNumbersVO.getNumberId());
        userThemeMappingVO.setStatus(UserNumberStatusEnum.PURCHASED.getCode());
        userThemeMappingVO.setBuyPrice(BigDecimal.ZERO);
        return userThemeMappingVO;
    }
}
