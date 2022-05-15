package com.starnft.star.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.infrastructure.entity.banner.BannerEntity;
import com.starnft.star.infrastructure.mapper.banner.BannerMapper;
import com.starnft.star.domain.banner.model.dto.BannerConditionDto;
import com.starnft.star.domain.banner.model.dto.BannerDto;
import com.starnft.star.management.service.ManagerBannerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date 2022/5/9 8:37 PM
 * @Author ： shellya
 */
@Service
public class ManagerBannerServiceImpl extends ServiceImpl<BannerMapper,BannerEntity> implements ManagerBannerService {

    @Override
    public Boolean saveBanner(BannerDto bannerDto) {
        BannerEntity banner = BannerEntity.builder()
                .title(bannerDto.getTitle())
                .imgUrl(bannerDto.getImgUrl())
                .position(bannerDto.getPosition())
                .display(bannerDto.getDisplay())
                .sort(bannerDto.getSort())
                .url(bannerDto.getUrl())
                .jumpType(bannerDto.getJumpType())
                .build();
        return this.save(banner);
    }

    @Override
    public Boolean modifyBanner(BannerDto bannerDto) {
        BannerEntity banner = BannerEntity.builder()
                .id(bannerDto.getId())
                .title(bannerDto.getTitle())
                .imgUrl(bannerDto.getImgUrl())
                .position(bannerDto.getPosition())
                .display(bannerDto.getDisplay())
                .sort(bannerDto.getSort())
                .url(bannerDto.getUrl())
                .build();
        return this.updateById(banner);
    }

    @Override
    public Boolean deleteBanner(Long id) {
        return this.removeById(id);
    }

    @Override
    public ResponsePageResult<Object> getBannerPage(RequestConditionPage<BannerConditionDto> reqDto) {
        Page<Object> objects = PageHelper.startPage(reqDto.getPage(), reqDto.getSize()).doSelectPage(() -> {
            baseMapper.selectBannerList(reqDto.getCondition());
        });
        return new ResponsePageResult<>(objects.getResult(), reqDto.getPage(), reqDto.getSize(), objects.getTotal());
    }
}
