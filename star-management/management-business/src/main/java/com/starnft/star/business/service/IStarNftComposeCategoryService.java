package com.starnft.star.business.service;

import java.util.List;
import com.starnft.star.business.domain.StarNftComposeCategory;
import com.starnft.star.business.domain.vo.StarNftComposeCategoryVO;

/**
 * 合成素材Service接口
 * 
 * @author ruoyi
 * @date 2022-07-30
 */
public interface IStarNftComposeCategoryService 
{
    /**
     * 查询合成素材
     * 
     * @param id 合成素材主键
     * @return 合成素材
     */
    public StarNftComposeCategoryVO selectStarNftComposeCategoryById(Long id);

    /**
     * 查询合成素材列表
     * 
     * @param starNftComposeCategory 合成素材
     * @return 合成素材集合
     */
    public List<StarNftComposeCategoryVO> selectStarNftComposeCategoryList(StarNftComposeCategory starNftComposeCategory);

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
     * 批量删除合成素材
     * 
     * @param ids 需要删除的合成素材主键集合
     * @return 结果
     */
    public int deleteStarNftComposeCategoryByIds(Long[] ids);

    /**
     * 删除合成素材信息
     * 
     * @param id 合成素材主键
     * @return 结果
     */
    public int deleteStarNftComposeCategoryById(Long id);
}
