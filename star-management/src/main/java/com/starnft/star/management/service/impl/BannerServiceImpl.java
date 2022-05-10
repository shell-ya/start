package com.starnft.star.management.service.impl;

import com.github.pagehelper.PageHelper;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.infrastructure.entity.banner.BannerEntity;
import com.starnft.star.infrastructure.repository.BannerRepository;
import com.starnft.star.domain.banner.model.dto.BannerConditionDto;
import com.starnft.star.domain.banner.model.dto.BannerDto;
import com.starnft.star.management.service.IBannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2022/5/9 8:37 PM
 * @Author ： shellya
 */
@Service
public class BannerServiceImpl implements IBannerService {

    @Resource
    private BannerRepository bannerRepository;

    @Override
    public Boolean saveBanner(BannerDto bannerDto) {
        return bannerRepository.saveBanner(bannerDto) > 0;
    }

    @Override
    public Boolean modifyBanner(BannerDto bannerDto) {
        return bannerRepository.updateBanner(bannerDto) > 0;
    }

    @Override
    public Boolean deleteBanner(Long id) {
        return bannerRepository.deleteBanner(id) > 0;
    }

    @Override
    public ResponsePageResult<BannerDto> getBannerPage(RequestConditionPage<BannerConditionDto> reqDto) {
        PageHelper.startPage(reqDto.getPage(),reqDto.getSize());
        List<BannerDto> bannerList = bannerRepository.queryBannerList(reqDto.getCondition());
        return new ResponsePageResult<>(bannerList, reqDto.getPage(), reqDto.getSize(), (long) bannerList.size());
    }
}
