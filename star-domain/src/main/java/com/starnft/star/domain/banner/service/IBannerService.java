package com.starnft.star.domain.banner.service;

import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.domain.banner.model.vo.BannerVo;

import java.util.List;

/**
 * @Date 2022/5/9 10:37 AM
 * @Author ： shellya
 */
public interface IBannerService {

    List<BannerVo> queryBannerVo(BannerReq req);
}
