package com.starnft.star.domain.theme.service.impl;

import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.domain.theme.repository.IThemeRepository;
import com.starnft.star.domain.theme.service.ThemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Resource
    IThemeRepository themeRepository;

    @Override
    public ResponsePageResult<ThemeVO> queryMainThemeInfo(ThemeReq requestPage) {
        return this.themeRepository.queryTheme(requestPage);
    }

    @Override
    @Cached(name = StarConstants.THEME_DETAIL_CACHE_NAME,
            expire = 3600 * 12,
            cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 3600 * 6, stopRefreshAfterLastAccess = 3600 * 3)
    @CachePenetrationProtect
    public ThemeDetailVO queryThemeDetail(Long id) {
        return this.themeRepository.queryThemeDetail(id);
    }

    @Override
    @Cached(name = StarConstants.THEME_CACHE_NAME,
            expire = 3600 * 12,
            cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 3600 * 6, stopRefreshAfterLastAccess = 3600 * 3)
    @CachePenetrationProtect
    public List<ThemeVO> queryThemesBySeriesId(Long seriesId) {
        Assert.notNull(seriesId, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "系列id不能为空"));
        return this.themeRepository.queryTheme(seriesId);
    }
}
