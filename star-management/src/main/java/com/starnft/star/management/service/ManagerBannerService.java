package com.starnft.star.management.service;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.banner.model.dto.BannerConditionDto;
import com.starnft.star.domain.banner.model.dto.BannerDto;


/**
 * @Date 2022/5/9 8:37 PM
 * @Author ： shellya
 */
public interface ManagerBannerService {

    Boolean saveBanner(BannerDto bannerDto);
    Boolean modifyBanner(BannerDto bannerDto);
    Boolean deleteBanner(Long id);
    ResponsePageResult<Object> getBannerPage(RequestConditionPage<BannerConditionDto> reqDto);

}
