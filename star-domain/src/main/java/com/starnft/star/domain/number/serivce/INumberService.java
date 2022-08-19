package com.starnft.star.domain.number.serivce;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.dto.NumberBatchUpdateDTO;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.number.model.req.HandoverReq;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.*;

import java.util.List;

public interface INumberService {
    ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    Boolean comsumeNumber(Long userId, Long numberId);

    ResponsePageResult<NumberVO> listNumber(RequestConditionPage<NumberQueryRequest> request);

    ThemeNumberVo getConsignNumberDetail(Long id);

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

//    Boolean managePrice(BigDecimal price);
}
