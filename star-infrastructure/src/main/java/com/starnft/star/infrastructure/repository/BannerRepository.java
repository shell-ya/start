package com.starnft.star.infrastructure.repository;

import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.domain.banner.model.vo.BannerVo;
import com.starnft.star.domain.banner.repository.IBannerRepository;
import com.starnft.star.infrastructure.entity.banner.BannerEntity;
import com.starnft.star.infrastructure.mapper.banner.BannerMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date 2022/5/9 10:31 AM
 * @Author ： shellya
 */
@Repository
public class BannerRepository implements IBannerRepository {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public List<BannerVo> queryBanner(BannerReq req) {
        List<BannerEntity> bannerList = this.bannerMapper.queryBanner(req.getType());
        return bannerList.stream().map(item -> BannerVo.builder()
                .title(item.getTitle())
                .imgUrl(item.getImgUrl())
                .position(item.getPosition())
                .url(item.getUrl()).build()
        ).collect(Collectors.toList());
    }
}
