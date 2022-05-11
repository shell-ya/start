package com.starnft.star.domain.theme.service.impl;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.service.ThemeService;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.domain.theme.repository.IThemeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Resource
    IThemeRepository iThemeRepository;
    @Override
    public ResponsePageResult<ThemeVO> queryMainThemeInfo(ThemeReq requestPage) {
        return iThemeRepository.queryTheme(requestPage);
    }

    @Override
    public ThemeDetailVO queryThemeDetail(Long id) {
        return  iThemeRepository.queryThemeDetail(id);
    }
}
