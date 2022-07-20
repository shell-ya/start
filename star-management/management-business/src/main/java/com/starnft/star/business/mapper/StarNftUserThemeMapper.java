package com.starnft.star.business.mapper;

import java.util.List;
import com.starnft.star.business.domain.StarNftUserTheme;

/**
 * 用户藏品Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-20
 */
public interface StarNftUserThemeMapper
{
    /**
     * 查询用户藏品
     *
     * @param id 用户藏品主键
     * @return 用户藏品
     */
    public StarNftUserTheme selectStarNftUserThemeById(Long id);

    /**
     * 查询用户藏品列表
     *
     * @param starNftUserTheme 用户藏品
     * @return 用户藏品集合
     */
    public List<StarNftUserTheme> selectStarNftUserThemeList(StarNftUserTheme starNftUserTheme);

    /**
     * 新增用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    public int insertStarNftUserTheme(StarNftUserTheme starNftUserTheme);

    /**
     * 修改用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    public int updateStarNftUserTheme(StarNftUserTheme starNftUserTheme);

    /**
     * 删除用户藏品
     *
     * @param id 用户藏品主键
     * @return 结果
     */
    public int deleteStarNftUserThemeById(Long id);

    /**
     * 批量删除用户藏品
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftUserThemeByIds(Long[] ids);
}
