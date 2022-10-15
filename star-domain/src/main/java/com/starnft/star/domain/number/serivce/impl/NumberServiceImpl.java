package com.starnft.star.domain.number.serivce.impl;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
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
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.number.model.OrderByEnum;
import com.starnft.star.domain.number.model.dto.*;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.req.MarketNumberListReq;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.*;
import com.starnft.star.domain.number.repository.INumberRepository;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.raising.service.IRaisingService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class NumberServiceImpl implements INumberService {
    @Resource
    INumberRepository numberRepository;

    @Resource
    TransactionTemplate template;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    RedisLockUtils redisLockUtils;

    @Resource
    IRaisingService raisingService;

    @Override
    public ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq) {
        return this.numberRepository.queryNumber(numberReq);
    }

    @Override
    public NumberDetailVO getNumberDetail(Long id) {
        Assert.notNull(id, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "商品ID不能为空"));
        NumberDetailVO number = this.numberRepository.getNumberDetail(id);
        number.setIsLock(redisLockUtils.isLock(String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), number.getId())) ? 1 : 0);
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
    public ThemeNumberVo getConsignNumberDetail(NumberDTO dto) {
        Optional.ofNullable(dto.getThemeId())
                .orElseThrow(() -> new StarException(StarError.VALUE_COULD_NOT_BE_NULL, "藏品id不能为空"));
        Optional.ofNullable(dto.getOwnerBy())
                .orElseThrow(() -> new StarException(StarError.VALUE_COULD_NOT_BE_NULL, "藏品寄售人不能为空"));
        ThemeNumberVo consignNumber = this.numberRepository.getConsignNumber(dto);
        Optional.ofNullable(consignNumber)
                .orElseThrow(() -> new StarException(StarError.GOODS_NOT_FOUND, "藏品已被购买"));
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
            if (!(logged && modified && created && updated)) {
                throw new StarException(StarError.ORDER_STATUS_ERROR);
            }
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
    public Boolean modifyNumberOwnerByVersion(Long id, Long userId, Integer code, Integer version) {
        Boolean result = this.numberRepository.modifyNumberStatusVersion(id, userId, code, version);
        Assert.isTrue(result, () -> new StarException("合成藏品已被抢走，请重新合成"));
        return result;
    }

    @Override
    public Boolean deleteNumber(Long uid, Long seriesThemeId) {
        return numberRepository.deleteNumber(uid, seriesThemeId);
    }

    @Override
    public Long queryUserFirstNumberId(Long uid, Long seriesThemeInfoId) {
        return numberRepository.firstNumber(uid, seriesThemeInfoId);
    }

    @Override
    public List<ReNumberVo> queryReNumberList(Long themeId) {
        return numberRepository.queryReNumberList(themeId);
    }

    @Override
    public List<Long> queryHasReNumberUser(Long seriesThemeId) {
        return numberRepository.queryHasReNumberUser(seriesThemeId);
    }

    @Override
    public boolean deleteNumber2ReDraw(ReNumberVo numberVo, List<Long> ids) {
        return numberRepository.deleteNumber2ReDraw(numberVo, ids);
    }

    @Override
    public Integer destroyedPublishNumber(Long themeId) {
        return numberRepository.destroyedPublishNumber(themeId);
    }

    @Override
    public BigDecimal minPrice(Long themeId) {
        return numberRepository.minPrice(themeId);
    }

    @Override
    public ResponsePageResult<MarketNumberInfoVO> marketNumberList(MarketNumberListReq marketNumberListReq) {
        return numberRepository.marketNumberList(marketNumberListReq);
    }

    @Override
    public BigDecimal avgPrice(Long themeId) {
        return numberRepository.avgPrice(themeId);
    }

    @Override
    public BigDecimal medianPrice(Long id) {
        List<BigDecimal> prices = numberRepository.allPrice(id, null);
        BigDecimal median = median(prices);
        List<BigDecimal> outMedianPrice = numberRepository.allPrice(id, median.multiply(new BigDecimal(5)));
        return avg(outMedianPrice);
    }

    @Override
    public List<RaisingTheme> nowRaisingTheme() {
        return numberRepository.nowRaisingTheme();
    }

    @Override
    public List<ThemeNumberVo> getMinConsignNumberDetail() {
        return numberRepository.minConsignNumberDetail();
    }

    @Override
    public List<ThemeNumberVo> getConsignNumberByTheme(Long themeId) {
        return numberRepository.getConsignNumberByTheme(themeId);
    }

    @Override
    public boolean takeDownTheme(Long themeId) {

        return numberRepository.takeDown(themeId);
    }

    @Override
    public BigDecimal getconsignMinPrice(Long themeInfoId) {
        return numberRepository.consignMinPrice(themeInfoId);
    }

    private BigDecimal getAvg(List<BigDecimal> outMedianPrice) {
        BigDecimal avg = avg(outMedianPrice);
        boolean over = true;
        LinkedList<BigDecimal> linkedList = Lists.newLinkedList(outMedianPrice);
        Collections.sort(linkedList);
        while (over) {
            linkedList.removeLast();
            BigDecimal currentAvg = avg(linkedList);
            int i = avg.divide(currentAvg, RoundingMode.CEILING).compareTo(new BigDecimal("0.3"));
            avg = currentAvg;
            if (i <= 0) {
                over = false;
            }

        }
        return avg;
    }

    private BigDecimal avg(List<BigDecimal> list) {
        if (null == list) return BigDecimal.ZERO;
        Collections.sort(list);
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal b :
                list) {
            sum = sum.add(b);
        }
        BigDecimal size = new BigDecimal(list.size());
        return sum.divide(size, RoundingMode.CEILING);
    }

    private BigDecimal median(List<BigDecimal> list) {
        if (null == list) return BigDecimal.ZERO;
        Collections.sort(list);
        int size = list.size();
        if (size % 2 == 1) {
            return list.get((size - 1) / 2);
        } else {
            return (list.get((size / 2) - 1).add(list.get(size / 2)).divide(new BigDecimal(2), RoundingMode.FLOOR));
        }
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
        List<NumberDingVO> numberDingList = new ArrayList<>();
//        for (NumberDingVO dingVo :
//                numberDingList) {x
//            if (dingVo.getName().startsWith("Pluviophile")){
//                dingVo.setName(dingVo.getName().s);
//            }
//
//        }
        List<ThemeDingVo> themeDingList = this.numberRepository.getThemeDingList();
        for (ThemeDingVo theme : themeDingList) {
            NumberDingVO numberDingVO = new NumberDingVO();
            numberDingVO.setImage(theme.getImage());
            numberDingVO.setName(theme.getName().contains("Pluviophile ") ? theme.getName().substring(12) : theme.getName());
            //返回价格为空 去判断当日是否涨停 涨停显示涨停价 未涨停显示开盘价
            if (null == theme.getPrice() || BigDecimal.ZERO.compareTo(theme.getPrice()) == 0) {
                RaisingTheme raisingTheme = raisingService.nowRaisingTheme(theme.getId());
                // 如果raisingTheme为空，则跳过这个theme，进入下一个主题处理
                if (Objects.isNull(raisingTheme)) {
                    continue;
                }
                numberDingVO.setPrice(raisingTheme.getLimitPrice());
            } else {
                numberDingVO.setPrice(theme.getPrice());
            }
            numberDingList.add(numberDingVO);
        }

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
