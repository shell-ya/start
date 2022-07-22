package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.vo.StarNftThemeNumberVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 主题编号Mapper接口
 *
 * @author shellya
 * @date 2022-06-03
 */
public interface StarNftThemeNumberMapper
{
    /**
     * 查询主题编号
     *
     * @param id 主题编号主键
     * @return 主题编号
     */
    public StarNftThemeNumber selectStarNftThemeNumberById(Long id);

    /**
     * 查询主题编号列表
     *
     * @param starNftThemeNumber 主题编号
     * @return 主题编号集合
     */
    public List<StarNftThemeNumber> selectStarNftThemeNumberList(StarNftThemeNumber starNftThemeNumber);

    /**
     * 新增主题编号
     *
     * @param starNftThemeNumber 主题编号
     * @return 结果
     */
    public int insertStarNftThemeNumber(StarNftThemeNumber starNftThemeNumber);

    /**
     * 修改主题编号
     *
     * @param starNftThemeNumber 主题编号
     * @return 结果
     */
    public int updateStarNftThemeNumber(StarNftThemeNumber starNftThemeNumber);

    /**
     * 删除主题编号
     *
     * @param id 主题编号主键
     * @return 结果
     */
    public int deleteStarNftThemeNumberById(Long id);

    /**
     * 批量删除主题编号
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftThemeNumberByIds(Long[] ids);

    List<StarNftThemeNumberVo> selectStarNftThemeNumberVoList(StarNftThemeNumberVo starNftThemeNumber);

    List<StarNftThemeNumber> selectStarNftThemeNumberByIds(@Param("ids") Collection ids);
}
