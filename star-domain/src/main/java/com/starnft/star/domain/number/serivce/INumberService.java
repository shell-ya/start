package com.starnft.star.domain.number.serivce;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.dto.*;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.req.MarketNumberListReq;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.*;

import java.math.BigDecimal;
import java.util.List;

public interface INumberService {
    ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    Boolean comsumeNumber(Long userId, Long numberId);

    Boolean isOwner(Long userId, Long themeId, Long numberId);

    Integer isOnSell(Long userId, Long themeId, Long numberId);

    ResponsePageResult<NumberVO> listNumber(RequestConditionPage<NumberQueryRequest> request);

    ThemeNumberVo getConsignNumberDetail(NumberDTO dto);

    List<NumberVO> getNumberListByThemeInfoId(NumberQueryDTO numberQueryDTO);

    //查询对应编号藏品信息
    ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId);

    ThemeNumberVo queryRandomThemeNumber(Long themeId);

    List<Integer> loadNotSellNumberNumCollection(Long themeId);

    List<NumberVO> loadNotSellNumberCollection(Long themeId);

    //物品转移
    boolean handover(HandoverReq handoverReq);


    Boolean modifyNumberInfo(NumberUpdateDTO param);

    Boolean modifyBatchNumberInfo(NumberBatchUpdateDTO param);

    Boolean queryThirdPlatSell(Long userId, Long seriesThemeId);

    boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO);

    Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation);

    Boolean saveBatchNumberCirculationRecord(List<NumberCirculationAddDTO> numberCirculation);

    public List<NumberDingVO> getNumberDingList();

    Boolean modifyNumberOwnerBy(Long id, Long userId, Integer code);


    Boolean modifyNumberOwnerByVersion(Long id, Long userId, Integer code,Integer version);

    Boolean deleteNumber(Long uid,Long seriesThemeId);

    Long queryUserFirstNumberId(Long uid,Long seriesThemeInfoId);

    List<NumberDetailVO> queryNumberNotOnSell(Long themeId);
//    Boolean managePrice(BigDecimal price);

    List<ReNumberVo> queryReNumberList(Long themeId);

    List<Long> queryHasReNumberUser(Long seriesThemeId);

    boolean deleteNumber2ReDraw(ReNumberVo numberVo, List<Long> ids);

    Integer destroyedPublishNumber(Long themeId);

    BigDecimal minPrice(Long themeId);

//    @Cached(name = StarConstants.THEME_IN_MARKET_NUMBER_LIST_CACHE_NAME,
//            key = "#marketNumberListReq.themeId,#marketNumberListReq.page",
//            expire = 5,
//            cacheType = CacheType.REMOTE)
//    @CacheRefresh(refresh = 4)
//    @CachePenetrationProtect
    ResponsePageResult<MarketNumberInfoVO> marketNumberList(MarketNumberListReq marketNumberListReq);


    BigDecimal avgPrice(Long themeId);

    BigDecimal medianPrice(Long id);

    List<RaisingTheme> nowRaisingTheme();

    List<ThemeNumberVo> getMinConsignNumberDetail();

    List<ThemeNumberVo> getConsignNumberByTheme(Long themeId);

    boolean takeDownTheme(Long themeId);

    BigDecimal getconsignMinPrice(Long themeInfoId);
}
