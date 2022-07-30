package com.starnft.star.business.mapper;

import java.util.List;
import com.starnft.star.business.domain.StarNftComposePrize;

/**
 * 合成奖品Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-30
 */
public interface StarNftComposePrizeMapper 
{
    /**
     * 查询合成奖品
     * 
     * @param id 合成奖品主键
     * @return 合成奖品
     */
    public StarNftComposePrize selectStarNftComposePrizeById(Long id);

    /**
     * 查询合成奖品列表
     * 
     * @param starNftComposePrize 合成奖品
     * @return 合成奖品集合
     */
    public List<StarNftComposePrize> selectStarNftComposePrizeList(StarNftComposePrize starNftComposePrize);

    /**
     * 新增合成奖品
     * 
     * @param starNftComposePrize 合成奖品
     * @return 结果
     */
    public int insertStarNftComposePrize(StarNftComposePrize starNftComposePrize);

    /**
     * 修改合成奖品
     * 
     * @param starNftComposePrize 合成奖品
     * @return 结果
     */
    public int updateStarNftComposePrize(StarNftComposePrize starNftComposePrize);

    /**
     * 删除合成奖品
     * 
     * @param id 合成奖品主键
     * @return 结果
     */
    public int deleteStarNftComposePrizeById(Long id);

    /**
     * 批量删除合成奖品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftComposePrizeByIds(Long[] ids);
}
