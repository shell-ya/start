package com.starnft.star.application.process.number;

import com.starnft.star.application.process.number.res.ConsignDetailRes;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.number.model.req.NumberConsignmentCancelRequest;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.ReNumberVo;

import java.util.List;

/**
 * @author Harlan
 * @date 2022/06/14 22:57
 */
public interface INumberCore {

    NumberDetailVO obtainThemeNumberDetail(Long id);

    ResponsePageResult<NumberVO> obtainThemeNumberList(RequestConditionPage<NumberQueryRequest> request);

    Boolean consignment(NumberConsignmentRequest request);

    Boolean consignmentCancel(NumberConsignmentCancelRequest request);

    ConsignDetailRes obtainConsignDetail(Long id);

    public  ResponsePageResult<UserNumbersVO> checkHasNumber(Long uid, Long themeId, UserNumberStatusEnum statusEnum,Integer page,Integer size);

    void putNumber(long themeId,String time1,String time2,int stock1,int stock2);

    boolean reNumber(ReNumberVo numberVo, List<Long> ids);
}
