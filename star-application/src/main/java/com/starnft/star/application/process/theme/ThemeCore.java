package com.starnft.star.application.process.theme;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.res.ThemeDetailRes;
import com.starnft.star.domain.theme.model.res.ThemeRes;

import java.util.List;

public interface ThemeCore {
    List<ThemeRes> queryThemesBySeriesId(Long seriesId);

    ResponsePageResult<ThemeRes> queryMainThemeInfo(ThemeReq build);

    ThemeDetailRes queryThemeDetail(Long id);
}
