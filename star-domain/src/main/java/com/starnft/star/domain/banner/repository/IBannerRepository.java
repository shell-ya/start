package com.starnft.star.domain.banner.repository;

import com.starnft.star.domain.banner.model.dto.BannerConditionDto;
import com.starnft.star.domain.banner.model.dto.BannerDto;
import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.domain.banner.model.vo.BannerVo;

import java.util.List;

/**
 * @Date 2022/5/9 10:32 AM
 * @Author ： shellya
 */
public interface IBannerRepository {

    List<BannerVo> queryBanner(BannerReq req);

    int saveBanner(BannerDto bannerDto);

    int deleteBanner(Long id);

    int updateBanner(BannerDto bannerDto);

    List<BannerDto> queryBannerList(BannerConditionDto condition);
}
