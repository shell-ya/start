package com.starnft.star.domain.numbers.serivce;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.numbers.model.req.NumberReq;
import com.starnft.star.domain.numbers.model.vo.NumberVO;

public interface NumberService {
   ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq);
}
