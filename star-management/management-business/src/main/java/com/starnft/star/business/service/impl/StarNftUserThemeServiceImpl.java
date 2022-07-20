package com.starnft.star.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.StarNftUserThemeMapper;
import com.starnft.star.business.domain.StarNftUserTheme;
import com.starnft.star.business.service.IStarNftUserThemeService;

/**
 * 用户藏品Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-20
 */
@Service
public class StarNftUserThemeServiceImpl implements IStarNftUserThemeService
{
    @Autowired
    private StarNftUserThemeMapper starNftUserThemeMapper;

    /**
     * 查询用户藏品
     *
     * @param id 用户藏品主键
     * @return 用户藏品
     */
    @Override
    public StarNftUserTheme selectStarNftUserThemeById(Long id)
    {
        return starNftUserThemeMapper.selectStarNftUserThemeById(id);
    }

    /**
     * 查询用户藏品列表
     *
     * @param starNftUserTheme 用户藏品
     * @return 用户藏品
     */
    @Override
    public List<StarNftUserTheme> selectStarNftUserThemeList(StarNftUserTheme starNftUserTheme)
    {
        return starNftUserThemeMapper.selectStarNftUserThemeList(starNftUserTheme);
    }

    /**
     * 新增用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    @Override
    public int insertStarNftUserTheme(StarNftUserTheme starNftUserTheme)
    {
        return starNftUserThemeMapper.insertStarNftUserTheme(starNftUserTheme);
    }

    /**
     * 修改用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    @Override
    public int updateStarNftUserTheme(StarNftUserTheme starNftUserTheme)
    {
        return starNftUserThemeMapper.updateStarNftUserTheme(starNftUserTheme);
    }

    /**
     * 批量删除用户藏品
     *
     * @param ids 需要删除的用户藏品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserThemeByIds(Long[] ids)
    {
        return starNftUserThemeMapper.deleteStarNftUserThemeByIds(ids);
    }

    /**
     * 删除用户藏品信息
     *
     * @param id 用户藏品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserThemeById(Long id)
    {
        return starNftUserThemeMapper.deleteStarNftUserThemeById(id);
    }
}
