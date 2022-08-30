package com.starnft.star.domain.number.serivce.impl;

import cn.hutool.json.JSONUtil;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.SortTypeEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.number.model.OrderByEnum;
import com.starnft.star.domain.number.model.dto.NumberBatchUpdateDTO;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.*;
import com.starnft.star.domain.number.repository.INumberRepository;
import com.starnft.star.domain.number.serivce.INumberService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class NumberServiceImpl implements INumberService {
    @Resource
    INumberRepository numberRepository;

    @Resource
    TransactionTemplate template;
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq) {
        return this.numberRepository.queryNumber(numberReq);
    }

    @Override
    public NumberDetailVO getNumberDetail(Long id) {
        Assert.notNull(id, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "商品ID不能为空"));
        NumberDetailVO number = this.numberRepository.getNumberDetail(id);
        Assert.notNull(number, () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "商品不存在"));
        return number;
    }

    @Override
    public Boolean comsumeNumber(Long userId, Long numberId) {
        return numberRepository.comsumeNumber(userId, numberId);
    }

    @Override
    public Boolean isOwner(Long userId, Long themeId, Long numberId) {

        return numberRepository.isOwner(userId, themeId, numberId);
    }

    @Override
    public Integer isOnSell(Long userId, Long themeId, Long numberId) {
        return numberRepository.isOnSell(userId, themeId, numberId);
    }

    @Override
    public ResponsePageResult<NumberVO> listNumber(RequestConditionPage<NumberQueryRequest> request) {
        NumberQueryRequest condition = Optional.ofNullable(request.getCondition()).orElse(new NumberQueryRequest());
        return this.numberRepository.listNumber(
                NumberQueryDTO
                        .builder()
                        .seriesId(condition.getSeriesId())
                        .themeId(condition.getThemeId())
                        .themeType(condition.getThemeType())
                        .themeName(condition.getThemeName())
                        .orderBy(OrderByEnum.getOrderBy(condition.getOrderBy()).getValues())
                        .sortType(SortTypeEnum.getSortTypeEnum(condition.getSortType()).getValue())
                        .page(request.getPage())
                        .size(request.getSize())
                        .build());
    }

    @Override
    public ThemeNumberVo getConsignNumberDetail(Long id) {
        Optional.ofNullable(id)
                .orElseThrow(() -> new StarException(StarError.VALUE_COULD_NOT_BE_NULL, "商品id不能为空"));
        ThemeNumberVo consignNumber = this.numberRepository.getConsignNumber(id);
        Optional.ofNullable(consignNumber)
                .orElseThrow(() -> new StarException(StarError.GOODS_NOT_FOUND, "商品已被购买"));
        return consignNumber;
    }

    @Override
    public List<NumberVO> getNumberListByThemeInfoId(NumberQueryDTO numberQueryDTO) {
        return this.numberRepository.getNumberListByThemeInfoId(numberQueryDTO);
    }

    @Override
    public ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId) {
        return this.numberRepository.queryNumberExist(themeNumber, themeId);
    }

    @Override
    public ThemeNumberVo queryRandomThemeNumber(Long themeId) {
        return this.numberRepository.selectRandomThemeNumber(themeId);
    }

    @Override
    public List<Integer> loadNotSellNumberNumCollection(Long themeId) {
        return this.numberRepository.loadNotSellNumberNumCollection(themeId);
    }

    @Override
    public List<NumberVO> loadNotSellNumberCollection(Long themeId) {
        return this.numberRepository.loadNotSellNumberCollection(themeId);
    }


    @Override
    public boolean handover(HandoverReq handoverReq) {
        NumberDetailVO numberDetail = this.numberRepository.getNumberDetail(handoverReq.getNumberId());

        if (numberDetail == null) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "未找到对应藏品");
        }

        return Boolean.TRUE.equals(this.template.execute(status -> {
            //记录藏品变化log
            Boolean logged = this.numberRepository.saveNumberCirculationRecord(this.createLog(handoverReq));
            //修改藏品售卖状态
            boolean modified = this.numberRepository.modifyNumberStatus(handoverReq.getNumberId(), handoverReq.getUid(), handoverReq.getItemStatus());
            //创建用户藏品所属关系 市场订单 售卖方更新商品售卖状态 购买方添加一条藏品记录
            boolean updated = true;
            if (StarConstants.OrderType.MARKET_GOODS.equals(handoverReq.getOrderType())) {
                updated = this.numberRepository.updateUserNumberMapping(this.updateMapping(handoverReq));
            }
            boolean created = this.numberRepository.createUserNumberMapping(this.createMapping(handoverReq));
            return logged && modified && created && updated;
        }));

    }

    private UserThemeMappingVO updateMapping(HandoverReq handoverReq) {
        UserThemeMappingVO userThemeMappingVO = new UserThemeMappingVO();
        userThemeMappingVO.setBeforeUserId(String.valueOf(handoverReq.getFromUid()));
        userThemeMappingVO.setStatus(UserNumberStatusEnum.SOLD.getCode());
        userThemeMappingVO.setSeriesId(handoverReq.getSeriesId());
        userThemeMappingVO.setSeriesThemeInfoId(handoverReq.getThemeId());
        userThemeMappingVO.setSeriesThemeId(handoverReq.getNumberId());
        userThemeMappingVO.setSellPrice(handoverReq.getPreMoney());
        return userThemeMappingVO;
    }

    @Override
    public Boolean modifyNumberInfo(NumberUpdateDTO param) {
        return this.numberRepository.modifyNumberInfo(param);
    }

    @Override
    public Boolean modifyBatchNumberInfo(NumberBatchUpdateDTO param) {
        return this.numberRepository.modifyBatchNumberInfo(param);
    }

    @Override
    public boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO) {
        return this.numberRepository.createUserNumberMapping(userThemeMappingVO);
    }

    @Override
    public Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation) {
        return this.numberRepository.saveNumberCirculationRecord(numberCirculation);
    }

    @Override
    public Boolean saveBatchNumberCirculationRecord(List<NumberCirculationAddDTO> numberCirculation) {
        return this.numberRepository.saveBatchNumberCirculationRecord(numberCirculation);
    }

    @Override
    public Boolean modifyNumberOwnerBy(Long id, Long userId, Integer code) {
        return this.numberRepository.modifyNumberStatus(id, userId, code);
    }

    @Override
    public List<NumberDetailVO> queryNumberNotOnSell(Long themeId) {
        return this.numberRepository.queryNumberNotOnSell(themeId);
    }


    @Override
    public List<NumberDingVO> getNumberDingList() {

        Object redisManage = redisTemplate.opsForValue().get(RedisKey.DING_PRICE_MANAGE.getKey());
        if (Objects.nonNull(redisManage)) {
            return JSONUtil.toList(redisManage.toString(), NumberDingVO.class);
        }
        List<NumberDingVO> numberDingList = this.numberRepository.getNumberDingList();
        redisTemplate.opsForValue().set(RedisKey.DING_PRICE_MANAGE.getKey(), JSONUtil.toJsonStr(numberDingList), RedisKey.DING_PRICE_MANAGE.getTime(), TimeUnit.SECONDS);
        return numberDingList;
    }

    private UserThemeMappingVO createMapping(HandoverReq handoverReq) {
        UserThemeMappingVO userThemeMappingVO = new UserThemeMappingVO();
        userThemeMappingVO.setUserId(String.valueOf(handoverReq.getUid()));
        userThemeMappingVO.setSeriesThemeId(handoverReq.getNumberId());
        userThemeMappingVO.setStatus(UserNumberStatusEnum.PURCHASED.getCode());
        userThemeMappingVO.setSource(handoverReq.getCategoryType());
        userThemeMappingVO.setSeriesId(handoverReq.getSeriesId());
        userThemeMappingVO.setSeriesThemeInfoId(handoverReq.getThemeId());
        userThemeMappingVO.setBuyPrice(handoverReq.getPreMoney());
        return userThemeMappingVO;
    }

    private NumberCirculationAddDTO createLog(HandoverReq handoverReq) {
        return NumberCirculationAddDTO.builder().numberId(handoverReq.getNumberId())
                .uid(handoverReq.getUid()).type(this.typeMapping(handoverReq.getType()))
                .beforePrice(handoverReq.getPreMoney()).afterPrice(handoverReq.getCurrMoney()).build();
    }

    private NumberCirculationTypeEnum typeMapping(Integer type) {
        for (NumberCirculationTypeEnum value : NumberCirculationTypeEnum.values()) {
            if (value.getCode().equals(type)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Boolean queryThirdPlatSell(Long userId, Long seriesThemeId) {
        return numberRepository.thirdPlatSelling(userId, seriesThemeId);
    }

}
