package com.starnft.star.domain.number.repository;

import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.dto.NumberCirculationDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;

public interface INumberRepository {
    ResponsePageResult<NumberVO> queryNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param);

    Boolean modifyNumberPriceAndStatus(NumberUpdateDTO param);

    Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation);

    NumberCirculationDTO getLastConsignedCirculation(Long numberId);

    Boolean modifyUserNumberStatus(Long uid, Long numberId, UserNumberStatusEnum statusEnum);

}
