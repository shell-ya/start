package com.starnft.star.business.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.json.JSONUtil;
import com.starnft.star.business.domain.vo.ComposeMaterial;
import com.starnft.star.business.domain.vo.StarNftComposeCategoryVO;
import com.starnft.star.common.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.StarNftComposeCategoryMapper;
import com.starnft.star.business.domain.StarNftComposeCategory;
import com.starnft.star.business.service.IStarNftComposeCategoryService;

/**
 * 合成素材Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-30
 */
@Service
public class StarNftComposeCategoryServiceImpl implements IStarNftComposeCategoryService 
{
    @Autowired
    private StarNftComposeCategoryMapper starNftComposeCategoryMapper;
    /**
     * 查询合成素材
     * 
     * @param id 合成素材主键
     * @return 合成素材
     */
    @Override
    public StarNftComposeCategoryVO selectStarNftComposeCategoryById(Long id)
    {
        StarNftComposeCategory starNftComposeCategory = starNftComposeCategoryMapper.selectStarNftComposeCategoryById(id);
        StarNftComposeCategoryVO starNftComposeCategoryVO = getStarNftComposeCategoryVO(starNftComposeCategory);
        return starNftComposeCategoryVO;
    }

    /**
     * 查询合成素材列表
     * 
     *
     * @return 合成素材
     */
    @Override
    public List<StarNftComposeCategoryVO> selectStarNftComposeCategoryList(StarNftComposeCategory nftComposeCategory)
    {
        List<StarNftComposeCategory> starNftComposeCategories = starNftComposeCategoryMapper.selectStarNftComposeCategoryList(nftComposeCategory);
        List<StarNftComposeCategoryVO> collect = starNftComposeCategories.stream().map(starNftComposeCategory -> {
            StarNftComposeCategoryVO starNftComposeCategoryVO = getStarNftComposeCategoryVO(starNftComposeCategory);
            return starNftComposeCategoryVO;
        }).collect(Collectors.toList());
        return collect;

    }

    @NotNull
    private StarNftComposeCategoryVO getStarNftComposeCategoryVO(StarNftComposeCategory starNftComposeCategory) {
        StarNftComposeCategoryVO starNftComposeCategoryVO = new StarNftComposeCategoryVO();
        starNftComposeCategoryVO.setComposeId(starNftComposeCategory.getComposeId());
        starNftComposeCategoryVO.setComposeScopeNumber(starNftComposeCategory.getComposeScopeNumber());
        starNftComposeCategoryVO.setId(starNftComposeCategory.getId());
        starNftComposeCategoryVO.setIsScore(starNftComposeCategory.getIsScore());
        starNftComposeCategoryVO.setComposeScopeType(starNftComposeCategory.getComposeScopeType());
        if (StringUtils.isNotBlank(starNftComposeCategory.getComposeMaterial())){
            starNftComposeCategoryVO.setComposeMaterial(JSONUtil.toList(starNftComposeCategory.getComposeMaterial(), ComposeMaterial.class));
        }
        return starNftComposeCategoryVO;
    }

    /**
     * 新增合成素材
     * 
     * @param starNftComposeCategory 合成素材
     * @return 结果
     */
    @Override
    public int insertStarNftComposeCategory(StarNftComposeCategory starNftComposeCategory)
    {
        return starNftComposeCategoryMapper.insertStarNftComposeCategory(starNftComposeCategory);
    }

    /**
     * 修改合成素材
     * 
     * @param starNftComposeCategory 合成素材
     * @return 结果
     */
    @Override
    public int updateStarNftComposeCategory(StarNftComposeCategory starNftComposeCategory)
    {
        return starNftComposeCategoryMapper.updateStarNftComposeCategory(starNftComposeCategory);
    }

    /**
     * 批量删除合成素材
     * 
     * @param ids 需要删除的合成素材主键
     * @return 结果
     */
    @Override
    public int deleteStarNftComposeCategoryByIds(Long[] ids)
    {
        return starNftComposeCategoryMapper.deleteStarNftComposeCategoryByIds(ids);
    }

    /**
     * 删除合成素材信息
     * 
     * @param id 合成素材主键
     * @return 结果
     */
    @Override
    public int deleteStarNftComposeCategoryById(Long id)
    {
        return starNftComposeCategoryMapper.deleteStarNftComposeCategoryById(id);
    }
}
