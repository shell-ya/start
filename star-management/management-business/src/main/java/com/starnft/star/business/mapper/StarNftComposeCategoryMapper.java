package com.starnft.star.business.mapper;

import java.util.List;
import com.starnft.star.business.domain.StarNftComposeCategory;

/**
 * 合成素材Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-30
 */
public interface StarNftComposeCategoryMapper 
{
    /**
     * 查询合成素材
     * 
     * @param id 合成素材主键
     * @return 合成素材
     */
    public StarNftComposeCategory selectStarNftComposeCategoryById(Long id);

    /**
     * 查询合成素材列表
     * 
     * @param starNftComposeCategory 合成素材
     * @return 合成素材集合
     */
    public List<StarNftComposeCategory> selectStarNftComposeCategoryList(StarNftComposeCategory starNftComposeCategory);

    /**
     * 新增合成素材
     * 
     * @param starNftComposeCategory 合成素材
     * @return 结果
     */
    public int insertStarNftComposeCategory(StarNftComposeCategory starNftComposeCategory);

    /**
     * 修改合成素材
     * 
     * @param starNftComposeCategory 合成素材
     * @return 结果
     */
    public int updateStarNftComposeCategory(StarNftComposeCategory starNftComposeCategory);

    /**
     * 删除合成素材
     * 
     * @param id 合成素材主键
     * @return 结果
     */
    public int deleteStarNftComposeCategoryById(Long id);

    /**
     * 批量删除合成素材
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftComposeCategoryByIds(Long[] ids);
}
