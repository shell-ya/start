package com.starnft.star.domain.banner.service.impl;

import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.domain.banner.model.vo.BannerVo;
import com.starnft.star.domain.banner.repository.IBannerRepository;
import com.starnft.star.domain.banner.service.IBannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2022/5/9 10:38 AM
 * @Author ： shellya
 */
@Service
public class BannerServiceImpl implements IBannerService {

    @Resource
    private IBannerRepository bannerRepository;

    @Override
    @Cached(name = StarConstants.BANNER_CACHE_NAME,
            expire = 3600 * 12,
            cacheType = CacheType.REMOTE)
    @CacheRefresh(refresh = 3600 * 6, stopRefreshAfterLastAccess = 3600 * 3)
    @CachePenetrationProtect
    public List<BannerVo> queryBannerVo(BannerReq req) {
        return bannerRepository.queryBanner(req);
    }
}
