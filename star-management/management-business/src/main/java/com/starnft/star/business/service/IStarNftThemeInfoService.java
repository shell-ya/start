package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.vo.StarNftThemeInfoVo;
import com.starnft.star.business.domain.vo.ThemeInfoVo;

import java.util.List;

/**
 * 主题Service接口
 *
 * @author shellya
 * @date 2022-06-03
 */
public interface IStarNftThemeInfoService
{
    /**
     * 查询主题
     *
     * @param id 主题主键
     * @return 主题
     */
    public StarNftThemeInfo selectStarNftThemeInfoById(Long id);
    public List<StarNftThemeInfo> selectStarNftThemeInfoByIds(Long[] ids);

    /**
     * 查询主题列表
     *
     * @param starNftThemeInfo 主题
     * @return 主题集合
     */
    public List<StarNftThemeInfo> selectStarNftThemeInfoList(StarNftThemeInfo starNftThemeInfo);

    /**
     * 新增主题
     *
     * @param starNftThemeInfo 主题
     * @return 结果
     */
    public int insertStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo, Long userId);

    /**
     * 修改主题
     *
     * @param starNftThemeInfo 主题
     * @return 结果
     */
    public int updateStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo);

    /**
     * 批量删除主题
     *
     * @param ids 需要删除的主题主键集合
     * @return 结果
     */
    public int deleteStarNftThemeInfoByIds(Long[] ids);

    /**
     * 删除主题信息
     *
     * @param id 主题主键
     * @return 结果
     */
    public int deleteStarNftThemeInfoById(Long id);

    public List<ThemeInfoVo> selectThemeInfoByPublisherId(Long publisherId);

    public List<StarNftThemeInfoVo> selectStarNftThemeInfoVoList(StarNftThemeInfo starNftThemeInfo);
}
