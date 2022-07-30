package com.starnft.star.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.StarNftComposePrizeMapper;
import com.starnft.star.business.domain.StarNftComposePrize;
import com.starnft.star.business.service.IStarNftComposePrizeService;

/**
 * 合成奖品Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-30
 */
@Service
public class StarNftComposePrizeServiceImpl implements IStarNftComposePrizeService 
{
    @Autowired
    private StarNftComposePrizeMapper starNftComposePrizeMapper;

    /**
     * 查询合成奖品
     * 
     * @param id 合成奖品主键
     * @return 合成奖品
     */
    @Override
    public StarNftComposePrize selectStarNftComposePrizeById(Long id)
    {
        return starNftComposePrizeMapper.selectStarNftComposePrizeById(id);
    }

    /**
     * 查询合成奖品列表
     * 
     * @param starNftComposePrize 合成奖品
     * @return 合成奖品
     */
    @Override
    public List<StarNftComposePrize> selectStarNftComposePrizeList(StarNftComposePrize starNftComposePrize)
    {
        return starNftComposePrizeMapper.selectStarNftComposePrizeList(starNftComposePrize);
    }

    /**
     * 新增合成奖品
     * 
     * @param starNftComposePrize 合成奖品
     * @return 结果
     */
    @Override
    public int insertStarNftComposePrize(StarNftComposePrize starNftComposePrize)
    {
        return starNftComposePrizeMapper.insertStarNftComposePrize(starNftComposePrize);
    }

    /**
     * 修改合成奖品
     * 
     * @param starNftComposePrize 合成奖品
     * @return 结果
     */
    @Override
    public int updateStarNftComposePrize(StarNftComposePrize starNftComposePrize)
    {
        return starNftComposePrizeMapper.updateStarNftComposePrize(starNftComposePrize);
    }

    /**
     * 批量删除合成奖品
     * 
     * @param ids 需要删除的合成奖品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftComposePrizeByIds(Long[] ids)
    {
        return starNftComposePrizeMapper.deleteStarNftComposePrizeByIds(ids);
    }

    /**
     * 删除合成奖品信息
     * 
     * @param id 合成奖品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftComposePrizeById(Long id)
    {
        return starNftComposePrizeMapper.deleteStarNftComposePrizeById(id);
    }
}
