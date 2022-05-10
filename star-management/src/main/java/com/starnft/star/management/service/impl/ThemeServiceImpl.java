package com.starnft.star.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.infrastructure.entity.series.StarNftSeries;
import com.starnft.star.infrastructure.entity.theme.StarNftThemeInfo;
import com.starnft.star.infrastructure.mapper.theme.StarNftThemeInfoMapper;
import com.starnft.star.management.service.ThemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ThemeServiceImpl  extends ServiceImpl<StarNftThemeInfoMapper, StarNftThemeInfo> implements ThemeService {
    @Resource
    StarNftThemeInfoMapper starNftThemeInfoMapper;
    @Override
    public PageInfo<StarNftThemeInfo> queryTheme(RequestConditionPage<StarNftThemeInfo> page) {
        return PageHelper.startPage(page.getPage(), page.getSize()).doSelectPageInfo(() -> {
            this.list(new QueryWrapper<StarNftThemeInfo>().setEntity(page.getCondition()).
                    eq(StarNftSeries.COL_IS_DELETE, Boolean.FALSE));
        });
    }

    @Override
    public Boolean insertTheme(StarNftThemeInfo themeInfo) {
        return this.save(themeInfo);
    }

    @Override
    public Boolean updateTheme(StarNftThemeInfo themeInfo) {
        return this.updateById(themeInfo);
    }

    @Override
    public Boolean deleteTheme(Long id) {
        return this.removeById(id);
    }

    @Override
    public StarNftThemeInfo detailTheme(Long id) {
        return this.getById(id);
    }
}
