package com.starnft.star.domain.banner.service.impl;

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
    public List<BannerVo> queryBannerVo(BannerReq req) {
        return bannerRepository.queryBanner(req);
    }
}
