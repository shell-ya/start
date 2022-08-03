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
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;

import java.util.List;

public interface INumberService {
    ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    ResponsePageResult<NumberVO> listNumber(RequestConditionPage<NumberQueryRequest> request);

    ThemeNumberVo getConsignNumberDetail(Long id);
    List<NumberVO> getNumberListByThemeInfoId(NumberQueryDTO numberQueryDTO);

    //查询对应编号藏品信息
    ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId);
    ThemeNumberVo queryRandomThemeNumber(Long themeId);

    List<Integer> loadNotSellNumberNumCollection(Long themeId);

    //物品转移
    boolean handover(HandoverReq handoverReq);


    Boolean modifyNumberInfo(NumberUpdateDTO param);
    Boolean modifyBatchNumberInfo(NumberBatchUpdateDTO param);

    boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO);
    Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation);
    Boolean saveBatchNumberCirculationRecord(List<NumberCirculationAddDTO> numberCirculation);

}
