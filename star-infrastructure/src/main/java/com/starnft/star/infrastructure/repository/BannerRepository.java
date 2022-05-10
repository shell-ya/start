package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.starnft.star.domain.banner.model.dto.BannerConditionDto;
import com.starnft.star.domain.banner.model.dto.BannerDto;
import com.starnft.star.domain.banner.model.req.BannerReq;
import com.starnft.star.domain.banner.model.vo.BannerVo;
import com.starnft.star.domain.banner.repository.IBannerRepository;
import com.starnft.star.infrastructure.entity.banner.BannerEntity;
import com.starnft.star.infrastructure.mapper.banner.BannerMapper;
import org.springframework.boot.Banner;
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
        List<BannerEntity> bannerList = bannerMapper.queryBanner(req.getType(),req.getSize());
        return bannerList.stream().map(item -> BannerVo.builder()
                .title(item.getTitle())
                .imgUrl(item.getUrl())
                .position(item.getPosition())
                .url(item.getUrl()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public int saveBanner(BannerDto bannerDto) {
        BannerEntity banner = BannerEntity.builder()
                .title(bannerDto.getTitle())
                .imgUrl(bannerDto.getImgUrl())
                .position(bannerDto.getPosition())
                .display(bannerDto.getDisplay())
                .sort(bannerDto.getSort())
                .url(bannerDto.getUrl())
                .crawlersUrl(bannerDto.getCrawlersUrl())
                .build();

        return bannerMapper.insert(banner);
    }


    public int deleteBanner(Long id) {
        return bannerMapper.deleteById(id);
    }

    public int updateBanner(BannerDto bannerDto) {

        BannerEntity banner = BannerEntity.builder()
                .id(bannerDto.getId())
                .title(bannerDto.getTitle())
                .imgUrl(bannerDto.getImgUrl())
                .position(bannerDto.getPosition())
                .display(bannerDto.getDisplay())
                .sort(bannerDto.getSort())
                .url(bannerDto.getUrl())
                .crawlersUrl(bannerDto.getCrawlersUrl())
                .build();
        return bannerMapper.updateById(banner);
    }

    public List<BannerDto> queryBannerList(BannerConditionDto condition) {
        return bannerMapper.selectBannerList(condition);
    }
}
