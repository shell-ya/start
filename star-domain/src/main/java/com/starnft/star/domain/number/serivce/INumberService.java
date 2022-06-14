package com.starnft.star.domain.number.serivce;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;

public interface INumberService {
    ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq);

    NumberDetailVO getNumberDetail(Long id);

    ResponsePageResult<NumberVO> listNumber(RequestConditionPage<NumberQueryRequest> request);

    Boolean consignment(Long uid, NumberConsignmentRequest request);

    Boolean consignmentCancel(Long uid, Long numberId);
}
