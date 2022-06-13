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
import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import com.starnft.star.domain.publisher.repository.IPublisherRepository;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.domain.theme.repository.IThemeRepository;
import com.starnft.star.domain.theme.service.ThemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Resource
    IThemeRepository themeRepository;
    @Resource
    IPublisherRepository publisherRepository;
    @Override
    public ResponsePageResult<ThemeVO> queryMainThemeInfo(ThemeReq requestPage) {
        ResponsePageResult<ThemeVO> themeVOResponsePageResult = this.themeRepository.queryTheme(requestPage);
        List<ThemeVO> list = getPublisher(themeVOResponsePageResult.getList());
        themeVOResponsePageResult.setList(list);
        return themeVOResponsePageResult;
    }

    @Override
    @Cached(name = StarConstants.THEME_DETAIL_CACHE_NAME,
            expire = 3600 * 12,
            cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 3600 * 6, stopRefreshAfterLastAccess = 3600 * 3)
    @CachePenetrationProtect
    public ThemeDetailVO queryThemeDetail(Long id) {
        ThemeDetailVO themeDetailVO = this.themeRepository.queryThemeDetail(id);
        if (Objects.nonNull(themeDetailVO.getPublisherId())) {
            PublisherReq publisherReq = new PublisherReq();
            publisherReq.setPublisherId(themeDetailVO.getPublisherId());
            PublisherVO publisherVO = publisherRepository.queryPublisher(publisherReq);
            themeDetailVO.setPublisherVO(publisherVO);
        }
        return themeDetailVO;
    }

    @Override
    @Cached(name = StarConstants.THEME_CACHE_NAME,
            expire = 3600 * 12,
            cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 3600 * 6, stopRefreshAfterLastAccess = 3600 * 3)
    @CachePenetrationProtect
    public List<ThemeVO> queryThemesBySeriesId(Long seriesId) {
        Assert.notNull(seriesId, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "系列id不能为空"));
        List<ThemeVO> theme = this.themeRepository.queryTheme(seriesId);
        return getPublisher(theme);
    }


    private List<ThemeVO> getPublisher(List<ThemeVO> theme) {
        Set<Long> collect = theme.stream().map(ThemeVO::getPublisherId).collect(Collectors.toSet());
        if (!collect.isEmpty()){
            Map<Long, List<PublisherVO>> pubs = publisherRepository.queryPublisherByIds(collect).stream().collect(Collectors.groupingBy(PublisherVO::getAuthorId));
            theme = theme.stream().peek(item -> item.setPublisherVO(pubs.get(item.getPublisherId()).get(0))).collect(Collectors.toList());
        }
        return theme;
    }
}
