package com.starnft.star.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.infrastructure.entity.theme.StarNftThemeInfo;

public interface ThemeService  extends IService<StarNftThemeInfo> {

    PageInfo<StarNftThemeInfo> queryTheme(RequestConditionPage<StarNftThemeInfo> page);
    Boolean insertTheme(StarNftThemeInfo series);
    Boolean updateTheme(StarNftThemeInfo series);
    Boolean  deleteTheme(Long id);
    StarNftThemeInfo detailTheme(Long id);
}
