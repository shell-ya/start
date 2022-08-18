package com.starnft.star.domain.number.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.dto.*;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;

import java.util.List;

public interface INumberRepository {
    ResponsePageResult<NumberVO> queryNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param);

    Boolean modifyNumberInfo(NumberUpdateDTO param);
    Boolean modifyBatchNumberInfo(NumberBatchUpdateDTO param);

    Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation);
    Boolean saveBatchNumberCirculationRecord(List<NumberCirculationAddDTO> numberCirculation);

    NumberCirculationDTO getLastConsignedCirculation(Long numberId);

    ThemeNumberVo getConsignNumber(Long id);
    ThemeNumberVo selectRandomThemeNumber(Long id);

    boolean modifyNumberStatus(Long numberId, Long uid, Integer status);

    boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO);


    ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId);

    boolean updateUserNumberMapping(UserThemeMappingVO updateThemeMappingVo);

    List<Integer> loadNotSellNumberNumCollection(Long themeId);

    List<NumberVO>  getNumberListByThemeInfoId(NumberQueryDTO numberQueryDTO);

    Boolean thirdPlatSelling(Long userId,Long seriesThemeId);

    public List<NumberDingVO> getNumberDingList();
}
