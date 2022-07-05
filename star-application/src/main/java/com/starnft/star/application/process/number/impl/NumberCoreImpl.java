package com.starnft.star.application.process.number.impl;

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
import com.starnft.star.domain.number.model.req.NumberConsignmentCancelRequest;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.theme.service.ThemeService;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.service.WalletConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


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
        Long uid = request.getUid();

        // 校验是否拥有该藏品
        UserNumbersVO userNumbers = this.checkNumberOwner(uid, request.getNumberId(), UserNumberStatusEnum.PURCHASED);

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
            Boolean updUserNumberBool = userThemeService.modifyUserNumberStatus(uid, request.getNumberId(), UserNumberStatusEnum.PURCHASED, UserNumberStatusEnum.ON_CONSIGNMENT);

            return updBool && saveBool && updUserNumberBool;
        });
        Assert.isTrue(Boolean.TRUE.equals(status), () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "寄售失败"));
        return Boolean.TRUE;
    }

    @Override
    public Boolean consignmentCancel(NumberConsignmentCancelRequest request) {
        Long uid = request.getUid();

        // 校验是否拥有该藏品
        UserNumbersVO userNumbers = this.checkNumberOwner(uid, request.getNumberId(), UserNumberStatusEnum.ON_CONSIGNMENT);

        // 判断商品是否在寄售中
        if (!Objects.equals(UserNumberStatusEnum.ON_CONSIGNMENT.getCode(), userNumbers.getStatus())) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "该藏品不是是寄售状态 无法取消");
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
            Boolean updUserNumberBool = this.userThemeService.modifyUserNumberStatus(uid, request.getNumberId(), UserNumberStatusEnum.ON_CONSIGNMENT, UserNumberStatusEnum.PURCHASED);

            return updBool && saveBool && updUserNumberBool;
        });
        Assert.isTrue(Boolean.TRUE.equals(status), () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "取消寄售失败"));
        return Boolean.TRUE;
    }

    @Override
    public ConsignDetailRes obtainConsignDetail(Long id) {
        // 获取市场相关费率配置
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.Market);
        Assert.notNull(config, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "市场相关费率配置不存在"));

        // 获取商品信息
        NumberDetailVO numberDetail = this.numberService.getNumberDetail(id);
        return ConsignDetailRes.builder()
                .id(id)
                .number(numberDetail.getNumber())
                .name(numberDetail.getName())
                .picture(numberDetail.getPicture())
                .price(numberDetail.getPrice())
                .copyrightRate(config.getCopyrightRate())
                .serviceRate(config.getServiceRate())
                .build();
    }

    private UserNumbersVO checkNumberOwner(Long uid, Long numberId, UserNumberStatusEnum statusEnum) {
        UserNumbersVO userNumberInfo = this.userThemeService.queryUserNumberInfo(uid, numberId, statusEnum);
        Assert.notNull(userNumberInfo, () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "你不是该藏品的拥有者 无法进行相关操作"));
        return userNumberInfo;
    }

}
