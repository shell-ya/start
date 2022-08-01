package com.starnft.star.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.StarNftComposeMapper;
import com.starnft.star.business.domain.StarNftCompose;
import com.starnft.star.business.service.IStarNftComposeService;

/**
 * 合成活动Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-30
 */
@Service
public class StarNftComposeServiceImpl implements IStarNftComposeService 
{
    @Autowired
    private StarNftComposeMapper starNftComposeMapper;

    /**
     * 查询合成活动
     * 
     * @param id 合成活动主键
     * @return 合成活动
     */
    @Override
    public StarNftCompose selectStarNftComposeById(Long id)
    {
        return starNftComposeMapper.selectStarNftComposeById(id);
    }

    /**
     * 查询合成活动列表
     * 
     * @param starNftCompose 合成活动
     * @return 合成活动
     */
    @Override
    public List<StarNftCompose> selectStarNftComposeList(StarNftCompose starNftCompose)
    {
        return starNftComposeMapper.selectStarNftComposeList(starNftCompose);
    }

    /**
     * 新增合成活动
     * 
     * @param starNftCompose 合成活动
     * @return 结果
     */
    @Override
    public int insertStarNftCompose(StarNftCompose starNftCompose)
    {
        return starNftComposeMapper.insertStarNftCompose(starNftCompose);
    }

    /**
     * 修改合成活动
     * 
     * @param starNftCompose 合成活动
     * @return 结果
     */
    @Override
    public int updateStarNftCompose(StarNftCompose starNftCompose)
    {
        return starNftComposeMapper.updateStarNftCompose(starNftCompose);
    }

    /**
     * 批量删除合成活动
     * 
     * @param ids 需要删除的合成活动主键
     * @return 结果
     */
    @Override
    public int deleteStarNftComposeByIds(Long[] ids)
    {
        return starNftComposeMapper.deleteStarNftComposeByIds(ids);
    }

    /**
     * 删除合成活动信息
     * 
     * @param id 合成活动主键
     * @return 结果
     */
    @Override
    public int deleteStarNftComposeById(Long id)
    {
        return starNftComposeMapper.deleteStarNftComposeById(id);
    }
}
