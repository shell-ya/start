package com.starnft.star.application.process.given.impl;

import com.starnft.star.application.process.article.IUserArticleCore;
import com.starnft.star.application.process.given.IGivenCore;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.given.model.req.GivenMangeReq;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;
import com.starnft.star.domain.number.repository.INumberRepository;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
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
    @Resource
    RedisLockUtils redisLockUtils;

    @Override
    public Boolean giving(Long userId, GivenMangeReq givenMangeReq) {
        Boolean lock = redisLockUtils.lock(String.format(RedisKey.GIVEN_MANAGE_LOCK.getKey(), givenMangeReq.getNumberId()), RedisKey.GIVEN_MANAGE_LOCK.getTime());
        Assert.isTrue(lock, () -> new StarException("请不要重复操作"));
        try {
            Boolean isGiving = redisTemplate.opsForSet().isMember(RedisKey.GIVEN_MANAGE_CONFIG.getKey(), givenMangeReq.getThemeId());
            Boolean giving = redisTemplate.opsForValue().getBit(String.format(RedisKey.GIVEN_MANAGE_BIT_CONFIG.getKey(), givenMangeReq.getThemeId()),  givenMangeReq.getNumberId());
            Assert.isTrue(isGiving, () -> new StarException("藏品不可转赠"));
            Assert.isFalse(giving, () -> new StarException("此藏品转赠次数已用完"));
            UserInfo userInfo = iUserService.queryUserByMobile(givenMangeReq.getMobile());
            UserInfoVO userInfoVO = iUserService.queryUserInfo(userId);
            Assert.notNull(userInfo, () -> new StarException("用户不存在"));
            Assert.isFalse(userInfo.getId().equals(userId), () -> new StarException("禁止赠送自己本人"));
            String payPwd = StarUtils.getSHA256Str(givenMangeReq.getPayPassword());
            Assert.isTrue(userInfoVO.getPlyPassword().equals(payPwd), () -> new StarException("支付密码错误"));
            UserNumbersVO userNumbersVO = userThemeService.queryUserNumberInfo(userId, givenMangeReq.getNumberId(), UserNumberStatusEnum.PURCHASED);
            Assert.notNull(userNumbersVO, () -> new StarException("藏品不存在，请选择正确的藏品信息"));
            Boolean owner = iNumberService.isOwner(userId, givenMangeReq.getThemeId(), givenMangeReq.getNumberId());
            Assert.isTrue(owner, () -> new StarException("藏品不存在，请选择正确的藏品信息"));

            //修改持有状态
            UserThemeMappingVO userThemeMappingVO = getUserThemeMappingVO(userId, givenMangeReq, userInfo, userNumbersVO);
            Boolean isSuccess = template.execute(status -> {
                boolean update = iNumberService.modifyNumberOwnerBy(givenMangeReq.getNumberId(), userInfo.getId(), NumberStatusEnum.SOLD.getCode());
                boolean modify = userThemeService.modifyUserNumberStatus(userId, givenMangeReq.getNumberId(), BigDecimal.ZERO, UserNumberStatusEnum.PURCHASED, UserNumberStatusEnum.GIVEND);
                boolean created = this.numberRepository.createUserNumberMapping(userThemeMappingVO);
                return modify && created && update;
            });
            Assert.isTrue(Boolean.TRUE.equals(isSuccess), () -> new StarException("转赠失败，请稍后再试"));
            redisTemplate.opsForValue().setBit(String.format(RedisKey.GIVEN_MANAGE_BIT_CONFIG.getKey(), givenMangeReq.getThemeId()), givenMangeReq.getNumberId(), true);
            return isSuccess;
        } finally {
            redisLockUtils.unlock(String.format(RedisKey.GIVEN_MANAGE_LOCK.getKey(), givenMangeReq.getNumberId()));
        }

    }

    private UserThemeMappingVO getUserThemeMappingVO(Long userId, GivenMangeReq givenMangeReq, UserInfo userInfo, UserNumbersVO userNumbersVO) {
        UserThemeMappingVO userThemeMappingVO = new UserThemeMappingVO();
        userThemeMappingVO.setBeforeUserId(userId.toString());
        userThemeMappingVO.setUserId(userInfo.getAccount().toString());
        userThemeMappingVO.setSeriesId(givenMangeReq.getSeriesId());
        userThemeMappingVO.setSeriesThemeInfoId(userNumbersVO.getThemeId());
        userThemeMappingVO.setSeriesThemeId(userNumbersVO.getNumberId());
        userThemeMappingVO.setStatus(UserNumberStatusEnum.PURCHASED.getCode());
        userThemeMappingVO.setBuyPrice(BigDecimal.ZERO);
        userThemeMappingVO.setSource(givenMangeReq.getCategoryType());
        return userThemeMappingVO;
    }
}
