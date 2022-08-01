package com.starnft.star.application.process.theme;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.res.ThemeDetailRes;
import com.starnft.star.domain.theme.model.res.ThemeRes;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;

import java.util.List;
import java.util.Set;

public interface ThemeCore {
    List<ThemeRes> queryThemesBySeriesId(Long seriesId);

    ResponsePageResult<ThemeRes> queryMainThemeInfo(ThemeReq build);

    ThemeDetailRes queryThemeDetail(Long id);

    Set<SecKillGoods> querySecKillThemes();
}
