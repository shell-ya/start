package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.domain.theme.repository.IThemeRepository;
import com.starnft.star.infrastructure.entity.theme.StarNftThemeInfo;
import com.starnft.star.infrastructure.mapper.theme.StarNftThemeInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Slf4j
@Repository
public class ThemeRepository  implements IThemeRepository {
    @Resource
    StarNftThemeInfoMapper starNftThemeInfoMapper;
    @Override
    public ResponsePageResult<ThemeVO> queryTheme(ThemeReq requestPage) {
        QueryWrapper<StarNftThemeInfo> wrapper = new QueryWrapper<StarNftThemeInfo>().eq(StarNftThemeInfo.COL_IS_DELETE, Boolean.FALSE);
        if (Objects.nonNull(requestPage.getIsRecommend())){
            wrapper.eq(StarNftThemeInfo.COL_IS_RECOMMEND,requestPage.getIsRecommend());
        }
        if (Objects.nonNull(requestPage.getSeriesId())){
            wrapper.eq(StarNftThemeInfo.COL_SERIES_ID,requestPage.getSeriesId());
        }
        PageInfo<StarNftThemeInfo> result = PageHelper.startPage(requestPage.getPage(), requestPage.getSize())
                .doSelectPageInfo(() ->  starNftThemeInfoMapper.selectList(wrapper));
        List<ThemeVO> list = result.getList().stream()
                .map(item ->
                        ThemeVO
                                .builder()
                                .id(item.getId())
                                .themeName(item.getThemeName())
                                .themeLevel(item.getThemeLevel())
                                .themePic(item.getThemePic())
                                .lssuePrice(item.getLssuePrice())
                                .publishNumber(item.getPublishNumber())
                                .themeType(item.getThemeType())
                                .tags(item.getTags())
                                .build()

                )
                .collect(Collectors.toList());
        ResponsePageResult<ThemeVO> pageResult = new ResponsePageResult<>();
        pageResult.setPage(result.getPageNum());
        pageResult.setList(list);
        pageResult.setTotal(result.getTotal());
        pageResult.setSize(result.getSize());
        return pageResult;
    }

    @Override
    public ThemeDetailVO queryThemeDetail(Long id) {
        StarNftThemeInfo repository = starNftThemeInfoMapper.selectById(id);
        if (repository.getIsDelete().equals(Boolean.TRUE)){
            return  null;
        }
        return  ThemeDetailVO
                .builder()
                .id(repository.getId())
                .themeName(repository.getThemeName())
                .themePic(repository.getThemePic())
                .contractAddress(repository.getContractAddress())
                .descrption(repository.getDescrption())
                .stock(repository.getStock())
                .themeType(repository.getThemeType())
                .themeLevel(repository.getThemeLevel())
                .lssuePrice(repository.getLssuePrice())
                .publishNumber(repository.getPublishNumber())
                .build();
    }

}