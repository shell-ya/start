package com.starnft.star.application.process.theme;

import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeGoodsReq;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.res.ThemeDetailRes;
import com.starnft.star.domain.theme.model.res.ThemeRes;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.model.vo.ThemeGoodsVO;

import java.util.List;
import java.util.Set;

public interface ThemeCore {
    List<ThemeRes> queryThemesBySeriesId(Long seriesId);

    ResponsePageResult<ThemeRes> queryMainThemeInfo(ThemeReq build);

    ThemeDetailRes queryThemeDetail(Long id);

    Set<SecKillGoods> querySecKillThemes();

    @Cached(name = StarConstants.THEME_IN_MARKET_CACHE_NAME,
            expire = 60,
            cacheType = CacheType.REMOTE)
    @CacheRefresh(refresh = 60)
    @CachePenetrationProtect
    ResponsePageResult<ThemeGoodsVO> themeGoodsList(ThemeGoodsReq themeGoodsReq);
}
