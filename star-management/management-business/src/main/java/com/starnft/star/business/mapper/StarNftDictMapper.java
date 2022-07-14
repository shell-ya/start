package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典Mapper接口
 *
 * @author shellya
 * @date 2022-06-08
 */
public interface StarNftDictMapper
{
    /**
     * 查询字典
     *
     * @param id 字典主键
     * @return 字典
     */
    public StarNftDict selectStarNftDictById(Long id);

    /**
     * 查询字典列表
     *
     * @param starNftDict 字典
     * @return 字典集合
     */
    public List<StarNftDict> selectStarNftDictList(StarNftDict starNftDict);

    /**
     * 新增字典
     *
     * @param starNftDict 字典
     * @return 结果
     */
    public int insertStarNftDict(StarNftDict starNftDict);

    /**
     * 修改字典
     *
     * @param starNftDict 字典
     * @return 结果
     */
    public int updateStarNftDict(StarNftDict starNftDict);

    /**
     * 删除字典
     *
     * @param id 字典主键
     * @return 结果
     */
    public int deleteStarNftDictById(Long id);

    /**
     * 批量删除字典
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftDictByIds(Long[] ids);

    public int insertBatch(@Param("entities") List<StarNftDict> entities);

    List<StarNftDict> queryByCategoryCode(@Param("categoryCode") String categoryCode);
}
