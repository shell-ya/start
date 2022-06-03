package com.starnft.star.domain.theme.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;

import java.util.List;

public interface ThemeService {
    ResponsePageResult<ThemeVO> queryMainThemeInfo(ThemeReq requestPage);

    ThemeDetailVO queryThemeDetail(Long id);

    List<ThemeVO> queryThemesBySeriesId(Long seriesId);
}
