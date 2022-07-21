package com.starnft.star.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starnft.star.business.domain.StarNftSeries;
import com.starnft.star.business.domain.StarNftUserTheme;
import com.starnft.star.business.domain.vo.UserSeriesVO;
import com.starnft.star.business.mapper.StarNftSeriesMapper;
import com.starnft.star.business.mapper.StarNftUserThemeMapper;
import com.starnft.star.business.service.IStarNftUserThemeService;
import com.starnft.star.common.constant.YesOrNoStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户藏品Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-20
 */
@Service
public class StarNftUserThemeServiceImpl extends ServiceImpl<StarNftUserThemeMapper, StarNftUserTheme> implements IStarNftUserThemeService {
    @Autowired
    private StarNftUserThemeMapper starNftUserThemeMapper;
    @Autowired
    private StarNftSeriesMapper starNftSeriesMapper;

    /**
     * 查询用户藏品
     *
     * @param id 用户藏品主键
     * @return 用户藏品
     */
    @Override
    public StarNftUserTheme selectStarNftUserThemeById(Long id) {
        return starNftUserThemeMapper.selectStarNftUserThemeById(id);
    }

    /**
     * 查询用户藏品列表
     *
     * @param starNftUserTheme 用户藏品
     * @return 用户藏品
     */
    @Override
    public List<StarNftUserTheme> selectStarNftUserThemeList(StarNftUserTheme starNftUserTheme) {
        return starNftUserThemeMapper.selectStarNftUserThemeList(starNftUserTheme);
    }

    /**
     * 新增用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    @Override
    public int insertStarNftUserTheme(StarNftUserTheme starNftUserTheme) {
        return starNftUserThemeMapper.insertStarNftUserTheme(starNftUserTheme);
    }

    /**
     * 修改用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    @Override
    public int updateStarNftUserTheme(StarNftUserTheme starNftUserTheme) {
        return starNftUserThemeMapper.updateStarNftUserTheme(starNftUserTheme);
    }

    /**
     * 批量删除用户藏品
     *
     * @param ids 需要删除的用户藏品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserThemeByIds(Long[] ids) {
        return starNftUserThemeMapper.deleteStarNftUserThemeByIds(ids);
    }

    /**
     * 删除用户藏品信息
     *
     * @param id 用户藏品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserThemeById(Long id) {
        return starNftUserThemeMapper.deleteStarNftUserThemeById(id);
    }

    @Override
    public List<UserSeriesVO> listSeriesByUserId(String id) {
        StarNftUserTheme starNftUserTheme = new StarNftUserTheme();
        starNftUserTheme.setUserId(id);
        starNftUserTheme.setIsDelete(YesOrNoStatusEnum.NO.getCode());
        QueryWrapper<StarNftUserTheme> starNftUserThemeQueryWrapper = new QueryWrapper<StarNftUserTheme>().setEntity(starNftUserTheme);
        List<StarNftUserTheme> result = this.getBaseMapper().selectList(starNftUserThemeQueryWrapper);
        Set<Long> collect = result.stream().map(StarNftUserTheme::getSeriesId).collect(Collectors.toSet());
        Map<Long, StarNftSeries> starNftSeriesArray = starNftSeriesMapper.selectList(new LambdaQueryWrapper<StarNftSeries>().in(StarNftSeries::getId, collect)).stream().collect(Collectors.toMap(StarNftSeries::getId, Function.identity()));
        List<UserSeriesVO> userSeriesVoList = result.stream().map(item -> {
            UserSeriesVO userSeriesVO = new UserSeriesVO();
            StarNftSeries starNftSeries = starNftSeriesArray.get(item.getSeriesId());
            userSeriesVO.setSeriesName(starNftSeries.getSeriesName());
            userSeriesVO.setSeriesId(starNftSeries.getId());
            userSeriesVO.setTypes(item.getStatus());
            return userSeriesVO;
        }).collect(Collectors.toList());
        return userSeriesVoList;
    }


}
