package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.vo.StarNftThemeInfoVo;
import com.starnft.star.business.domain.vo.ThemeInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 主题Mapper接口
 *
 * @author shellya
 * @date 2022-06-03
 */
public interface StarNftThemeInfoMapper
{
    /**
     * 查询主题
     *
     * @param id 主题主键
     * @return 主题
     */
    public StarNftThemeInfo selectStarNftThemeInfoById(Long id);

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
    public int insertStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo);

    /**
     * 修改主题
     *
     * @param starNftThemeInfo 主题
     * @return 结果
     */
    public int updateStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo);

    /**
     * 删除主题
     *
     * @param id 主题主键
     * @return 结果
     */
    public int deleteStarNftThemeInfoById(Long id);

    /**
     * 批量删除主题
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftThemeInfoByIds(Long[] ids);

    List<ThemeInfoVo> selectThemeInfoByPublisherId(Long publisherId);

    List<StarNftThemeInfoVo> selectStarNftThemeInfoVoList(StarNftThemeInfo starNftThemeInfo);

    List<StarNftThemeInfo> selectStarNftThemeInfoByIds(@Param("array") Collection ids);


}
