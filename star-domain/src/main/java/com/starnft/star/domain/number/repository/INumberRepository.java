package com.starnft.star.domain.number.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.dto.*;
import com.starnft.star.domain.number.model.req.MarketNumberListReq;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.*;

import java.math.BigDecimal;
import java.util.List;

public interface INumberRepository {
    ResponsePageResult<NumberVO> queryNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param);

    Boolean modifyNumberInfo(NumberUpdateDTO param);
    Boolean modifyBatchNumberInfo(NumberBatchUpdateDTO param);

    Boolean comsumeNumber(Long userId,Long numberId);


    Boolean isOwner(Long userId, Long themeId, Long numberId);

    Integer isOnSell(Long userId, Long themeId, Long numberId);

    Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation);
    Boolean saveBatchNumberCirculationRecord(List<NumberCirculationAddDTO> numberCirculation);

    NumberCirculationDTO getLastConsignedCirculation(Long numberId);

    ThemeNumberVo getConsignNumber(NumberDTO dto);
    ThemeNumberVo selectRandomThemeNumber(Long id);

    boolean modifyNumberStatus(Long numberId, Long uid, Integer status);

    boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO);


    ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId);

    boolean updateUserNumberMapping(UserThemeMappingVO updateThemeMappingVo);

    List<Integer> loadNotSellNumberNumCollection(Long themeId);
    List<NumberVO> loadNotSellNumberCollection(Long themeId);

    List<NumberVO>  getNumberListByThemeInfoId(NumberQueryDTO numberQueryDTO);

    Boolean thirdPlatSelling(Long userId,Long seriesThemeId);

    public List<NumberDingVO> getNumberDingList();

    List<ThemeDingVo> getThemeDingList();

    Long firstNumber(Long uid, Long seriesThemeInfoId);

    Boolean deleteNumber(Long uid, Long seriesThemeId);

    List<ReNumberVo> queryReNumberList(Long themeId);

    List<Long> queryHasReNumberUser(Long seriesThemeId);

    boolean deleteNumber2ReDraw(ReNumberVo numberVo, List<Long> ids);

    List<NumberDetailVO> queryNumberNotOnSell(Long themeId);

    Boolean modifyNumberStatusVersion(Long id, Long userId, Integer code, Integer version);

    Integer destroyedPublishNumber(Long themeId);

    BigDecimal minPrice(Long themeId);

    ResponsePageResult<MarketNumberInfoVO> marketNumberList(MarketNumberListReq marketNumberListReq);

    BigDecimal avgPrice(Long themeId);

    List<BigDecimal> allPrice(Long themeId,BigDecimal price);

    List<RaisingTheme> nowRaisingTheme();
    List<ThemeNumberVo> minConsignNumberDetail();

    List<ThemeNumberVo> getConsignNumberByTheme(Long themeId);

    boolean takeDown(Long themeId);

    BigDecimal consignMinPrice(Long themeInfoId);
}
