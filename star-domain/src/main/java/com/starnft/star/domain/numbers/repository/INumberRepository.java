package com.starnft.star.domain.numbers.repository;

import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.numbers.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.numbers.model.dto.NumberCirculationDTO;
import com.starnft.star.domain.numbers.model.dto.NumberQueryDTO;
import com.starnft.star.domain.numbers.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.numbers.model.req.NumberReq;
import com.starnft.star.domain.numbers.model.vo.NumberDetailVO;
import com.starnft.star.domain.numbers.model.vo.NumberVO;

public interface INumberRepository {
    ResponsePageResult<NumberVO> queryNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param);

    Boolean modifyNumberPriceAndStatus(NumberUpdateDTO param);

    Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation);

    NumberCirculationDTO getLastConsignedCirculation(Long numberId);

    Boolean modifyUserNumberStatus(Long uid, Long numberId, UserNumberStatusEnum statusEnum);

}
