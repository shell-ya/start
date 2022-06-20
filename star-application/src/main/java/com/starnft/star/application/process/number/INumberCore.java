package com.starnft.star.application.process.number;

import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.number.res.ConsignDetailRes;
import com.starnft.star.application.process.number.res.MarketOrderRes;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;

/**
 * @author Harlan
 * @date 2022/06/14 22:57
 */
public interface INumberCore {

    NumberDetailVO obtainThemeNumberDetail(Long id);

    ResponsePageResult<NumberVO> obtainThemeNumberList(RequestConditionPage<NumberQueryRequest> request);

    Boolean consignment(Long uid, NumberConsignmentRequest request);

    Boolean consignmentCancel(Long uid, Long numberId);

    ConsignDetailRes obtainConsignDetail(Long id);

}
