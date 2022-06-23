package com.starnft.star.domain.number.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.dto.NumberCirculationDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;

public interface INumberRepository {
    ResponsePageResult<NumberVO> queryNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param);

    Boolean modifyNumberInfo(NumberUpdateDTO param);

    Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation);

    NumberCirculationDTO getLastConsignedCirculation(Long numberId);

    ThemeNumberVo getConsignNumber(Long id);

    boolean modifyNumberStatus(Long numberId, Long uid, Integer status);

    boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO);

    ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId);

}
