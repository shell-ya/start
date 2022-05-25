package com.starnft.star.domain.numbers.serivce.impl;

import com.starnft.star.common.enums.SortTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.numbers.model.OrderByEnum;
import com.starnft.star.domain.numbers.model.dto.NumberQueryDTO;
import com.starnft.star.domain.numbers.model.req.NumberQueryRequest;
import com.starnft.star.domain.numbers.model.req.NumberReq;
import com.starnft.star.domain.numbers.model.vo.NumberDetailVO;
import com.starnft.star.domain.numbers.model.vo.NumberVO;
import com.starnft.star.domain.numbers.repository.INumberRepository;
import com.starnft.star.domain.numbers.serivce.NumberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class NumberServiceImpl implements NumberService {
    @Resource
    INumberRepository iNumberRepository;

    @Override
    public ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq) {
        return this.iNumberRepository.queryNumber(numberReq);
    }

    @Override
    public NumberDetailVO getNumberById(Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new StarException(StarError.PARAETER_UNSUPPORTED, "商品ID不能为空"));
        NumberDetailVO number = this.iNumberRepository.getNumberById(id);
        Optional.ofNullable(number).orElseThrow(() -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "商品不存在"));
        return number;
    }

    @Override
    public ResponsePageResult<NumberVO> listNumber(RequestConditionPage<NumberQueryRequest> request) {
        NumberQueryRequest condition = Optional.ofNullable(request.getCondition()).orElse(new NumberQueryRequest());
        return this.iNumberRepository.listNumber(
                NumberQueryDTO
                        .builder()
                        .seriesId(condition.getSeriesId())
                        .themeId(condition.getThemeId())
                        .themeType(condition.getThemeType())
                        .themeName(condition.getThemeName())
                        .orderBy(OrderByEnum.getOrderBy(condition.getOrderBy()).getValues())
                        .sortType(SortTypeEnum.getSortTypeEnum(condition.getSortType()).getValue())
                        .page(request.getPage())
                        .size(request.getSize())
                        .build());
    }
}
