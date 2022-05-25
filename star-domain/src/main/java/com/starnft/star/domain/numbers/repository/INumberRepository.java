package com.starnft.star.domain.numbers.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.numbers.model.dto.NumberQueryDTO;
import com.starnft.star.domain.numbers.model.req.NumberReq;
import com.starnft.star.domain.numbers.model.vo.NumberDetailVO;
import com.starnft.star.domain.numbers.model.vo.NumberVO;

public interface INumberRepository {
    ResponsePageResult<NumberVO> queryNumber(NumberReq numberReq);

    NumberDetailVO getNumberById(Long id);

    ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param);
}
