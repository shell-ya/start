package com.starnft.star.application.process.number.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.number.res.ConsignDetailRes;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.number.model.req.*;
import com.starnft.star.domain.number.model.vo.*;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.raising.service.IRaisingService;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.service.ThemeService;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.service.WalletConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;


/**
 * @author Harlan
 * @date 2022/06/14 22:57
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NumberCoreImpl implements INumberCore {

    private final INumberService numberService;
    private final ThemeService themeService;
    private final UserThemeService userThemeService;
    private final TransactionTemplate transactionTemplate;
    private final RedisUtil redisUtil;
    private final RedisLockUtils redisLockUtils;

    private final IRaisingService raisingService;

    @Override
    public NumberDetailVO obtainThemeNumberDetail(Long id) {
        NumberDetailVO numberDetail = this.numberService.getNumberDetail(id);
        numberDetail.setIssuedQty(this.themeService.obtainThemeIssuedQty(numberDetail.getThemeId()));
        return numberDetail;
    }

    @Override
    public ResponsePageResult<NumberVO> obtainThemeNumberList(RequestConditionPage<NumberQueryRequest> request) {
        ResponsePageResult<NumberVO> numberList = this.numberService.listNumber(request);
        Map<Long, Integer> issuedQtyCache = new HashMap<>();
        numberList.getList().forEach(numberVO -> {
            numberVO.setIssuedQty(
                    Optional.ofNullable(issuedQtyCache.get(numberVO.getThemeId())).orElseGet(() -> {
                        Integer qty = this.themeService.obtainThemeIssuedQty(numberVO.getThemeId());
                        issuedQtyCache.put(numberVO.getThemeId(), qty);
                        return qty;
                    }));
            //设置交易状态
            String isTransaction = String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), numberVO.getId());
            if (redisUtil.hasKey(RedisLockUtils.REDIS_LOCK_PREFIX + isTransaction)) {
                numberVO.setIsTransaction(1);
            }
        });
        return numberList;
    }

    @Override
    public Boolean consignment(NumberConsignmentRequest request) {

//        throw new StarException(StarError.CONSIGNMENT_NOT_OPEN);

        Long uid = request.getUid();

        // 校验是否拥有该藏品
        UserNumbersVO userNumbers = this.checkNumberOwner(uid, request.getNumberId(), UserNumberStatusEnum.PURCHASED);

        //校验藏品是否已涨停

        if(raisingService.themeRaisingFlag(userNumbers.getThemeId())){
            throw new StarException(StarError.IS_RAISING);
        }

        //校验是否在第三方平台挂售
        if (numberService.queryThirdPlatSell(uid, request.getNumberId())) {
            throw new StarException(StarError.THIRD_PLAT_SELL);
        }

        //  校验是否到藏品市场开放时间
        ThemeDetailVO themeDetailVO = themeService.queryThemeDetail(userNumbers.getThemeId());
        if (DateUtil.date().before(themeDetailVO.getMarketOpenDate())) {
            throw new StarException(StarError.MARKET_DO_NOT_START_ERROR);
        }
        //  校验藏品可交易
        if (StarConstants.themeResaleEnum.NOT_RESALE.getCode().equals(themeDetailVO.getIsResale())) {
            throw new StarException(StarError.GOOD_NOT_RESALE_ERROR);
        }

        if (!Objects.equals(userNumbers.getStatus(), UserNumberStatusEnum.PURCHASED.getCode())) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "只有已购买状态的藏品才能进行转售");
        }

        Boolean status = this.transactionTemplate.execute(transactionStatus -> {
            // 修改商品价格和状态
            Boolean updBool = numberService.modifyNumberInfo(
                    NumberUpdateDTO.builder()
                            .uid(uid)
                            .numberId(request.getNumberId())
                            .price(request.getPrice())
                            .status(NumberStatusEnum.ON_CONSIGNMENT)
                            .build());
            // 保存寄售记录
            Boolean saveBool = numberService.saveNumberCirculationRecord(
                    NumberCirculationAddDTO.builder()
                            .uid(uid)
                            .numberId(request.getNumberId())
                            .type(NumberCirculationTypeEnum.CONSIGNMENT)
                            .beforePrice(userNumbers.getPrice())
                            .afterPrice(request.getPrice())
                            .build());
            // 修改用户藏品状态
            Boolean updUserNumberBool = userThemeService.modifyUserNumberStatus(uid, request.getNumberId(), request.getPrice(), UserNumberStatusEnum.PURCHASED, UserNumberStatusEnum.ON_CONSIGNMENT);

            return updBool && saveBool && updUserNumberBool;
        });
        Assert.isTrue(Boolean.TRUE.equals(status), () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "寄售失败"));
        return Boolean.TRUE;
    }

    @Override
    public Boolean consignmentCancel(NumberConsignmentCancelRequest request) {
        Long uid = request.getUid();

        String isTransaction = String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), request.getNumberId());
        long lockTimes = RedisKey.MARKET_ORDER_TRANSACTION.getTimeUnit().toSeconds(RedisKey.MARKET_ORDER_TRANSACTION.getTime());

        Boolean lock = redisLockUtils.lock(isTransaction, lockTimes);

        Assert.isTrue(Boolean.TRUE.equals(lock), () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "藏品正在交易。"));

        try {

            // 校验是否拥有该藏品
            UserNumbersVO userNumbers = this.checkNumberOwner(uid, request.getNumberId(), UserNumberStatusEnum.ON_CONSIGNMENT);

            // 判断商品是否在寄售中
            if (!Objects.equals(UserNumberStatusEnum.ON_CONSIGNMENT.getCode(), userNumbers.getStatus())) {
                throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "该藏品不是寄售状态 无法取消");
            }
            Boolean status = this.transactionTemplate.execute(transactionStatus -> {
                // 还原商品状态
                Boolean updBool = this.numberService.modifyNumberInfo(
                        NumberUpdateDTO.builder()
                                .uid(uid)
                                .numberId(request.getNumberId())
                                .status(NumberStatusEnum.SOLD)
                                .build());

                // 保存取消寄售记录
                Boolean saveBool = this.numberService.saveNumberCirculationRecord(
                        NumberCirculationAddDTO.builder()
                                .uid(uid)
                                .numberId(request.getNumberId())
                                .type(NumberCirculationTypeEnum.CANCEL_CONSIGNMENT)
                                .build());

                // 还原用户藏品状态
                Boolean updUserNumberBool = this.userThemeService.modifyUserNumberStatus(uid, request.getNumberId(), null, UserNumberStatusEnum.ON_CONSIGNMENT, UserNumberStatusEnum.PURCHASED);

                return updBool && saveBool && updUserNumberBool;
            });

            Assert.isTrue(Boolean.TRUE.equals(status), () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "取消寄售失败"));
            return Boolean.TRUE;
        } finally {
            redisLockUtils.unlock(isTransaction);
        }


    }

    @Override
    public ConsignDetailRes obtainConsignDetail(Long id) {
        // 获取市场相关费率配置
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.Market);
        Assert.notNull(config, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "市场相关费率配置不存在"));

        // 获取商品信息
        NumberDetailVO numberDetail = this.numberService.getNumberDetail(id);
        //查询当前主题开盘价与收盘价
        RaisingTheme raisingTheme = raisingService.nowRaisingTheme(numberDetail.getThemeId());
        ConsignDetailRes build = ConsignDetailRes.builder()
                .id(id)
                .number(numberDetail.getNumber())
                .name(numberDetail.getName())
                .picture(numberDetail.getPicture())
                .price(numberDetail.getPrice())
                .copyrightRate(config.getCopyrightRate())
                .serviceRate(config.getServiceRate())
                .build();
        if (null != raisingTheme){
            build.setLimitPrice(raisingTheme.getLimitPrice());
            build.setOpeningPrice(raisingTheme.getFloorPrice());
        }
        return build;
    }

    private UserNumbersVO checkNumberOwner(Long uid, Long numberId, UserNumberStatusEnum statusEnum) {
        UserNumbersVO userNumberInfo = this.userThemeService.queryUserNumberInfo(uid, numberId, statusEnum);
        Assert.notNull(userNumberInfo, () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "你不是该藏品的拥有者 无法进行相关操作"));
        return userNumberInfo;
    }

    @Override
    public ResponsePageResult<UserNumbersVO> checkHasNumber(Long uid, Long themeId, UserNumberStatusEnum statusEnum, Integer page, Integer size) {
        return this.userThemeService.queryUserArticleNumberInfoByThemeIds(uid, Lists.newArrayList(themeId), statusEnum, page, size);

    }

    @Override
    public void putNumber(long themeId, String time1, String time2, int stock1, int stock2) {
        List<Integer> stockNums = this.numberService.loadNotSellNumberNumCollection(themeId);
        Integer[] integers = stockNums.subList(0, stock1).toArray(new Integer[stock1]);
        Integer[] integers2 = stockNums.subList(stock1, stock1 + stock2).toArray(new Integer[stock2]);

        String stockKey1 = String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), themeId, time1);
        String stockKey2 = String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), themeId, time2);
//        redisUtil.hashIncr(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.format("%s-time-%s",themeId,time1), stock1);
        redisUtil.delByKey(stockKey1);
        redisUtil.addToListLeft(stockKey1, RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTime(), RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTimeUnit(), integers);
//
        redisUtil.delByKey(stockKey2);
//        redisUtil.hashIncr(RedisKey.SECKILL_GOODS_STOCK_NUMBER.getKey(), String.format("%s-time-%s",themeId,time2), stock2);
        redisUtil.addToListLeft(stockKey2, RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTime(), RedisKey.SECKILL_GOODS_STOCK_QUEUE.getTimeUnit(), integers2);


    }

    @Override
    public boolean reNumber(ReNumberVo numberVo, List<Long> ids) {
        //删除用户持有的重复藏品

        return this.transactionTemplate.execute(transactionStatus -> {

            boolean b = numberService.deleteNumber2ReDraw(numberVo, ids);
            boolean result = true;
            for (Long id :
                    ids) {
                boolean handover = numberService.handover(buildHandOverReq(numberVo, id));
                if (!handover) result = false;
            }

            return b && result;
        });
    }

    @Override
    public ResponsePageResult<MarketNumberInfoVO> marketNumberList(MarketNumberListReq marketNumberListReq) {
        ResponsePageResult<MarketNumberInfoVO> marketNumberList = numberService.marketNumberList(marketNumberListReq);
        ArrayList<@Nullable MarketNumberInfoVO> currList = Lists.newArrayList();
        for (MarketNumberInfoVO marketNumberInfoVO : marketNumberList.getList()) {
            String isTransaction = String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), marketNumberInfoVO.getNumId());
            marketNumberInfoVO.setStatus(redisLockUtils.isLock(isTransaction) ? 1 : 0);
            currList.add(marketNumberInfoVO);
        }
        marketNumberList.setList(currList);
        return marketNumberList;
    }

    private HandoverReq buildHandOverReq(ReNumberVo numberVo, Long id) {

        Long awardCategoryId = numberVo.getSeriesThemeInfoId();
        ThemeDetailVO themeDetailVO = themeService.queryThemeDetail(awardCategoryId);
        List<NumberVO> numberVOS = numberService.loadNotSellNumberCollection(awardCategoryId);

        if (CollectionUtil.isEmpty(numberVOS)) {
            throw new RuntimeException("藏品余量不足！");
        }
        HandoverReq handoverReq = new HandoverReq();
        handoverReq.setUid(id);
        handoverReq.setSeriesId(themeDetailVO.getSeriesId());
        handoverReq.setType(NumberCirculationTypeEnum.CASTING.getCode());
        handoverReq.setCategoryType(1);
        handoverReq.setCurrMoney(BigDecimal.ZERO);
        handoverReq.setPreMoney(BigDecimal.ZERO);
        handoverReq.setFromUid(0L);
        handoverReq.setToUid(id);
        handoverReq.setThemeId(awardCategoryId);
        handoverReq.setOrderType(StarConstants.OrderType.PUBLISH_GOODS);
        handoverReq.setNumberId(numberVOS.get(0).getId());
        handoverReq.setItemStatus(1);

        return handoverReq;
    }

}
