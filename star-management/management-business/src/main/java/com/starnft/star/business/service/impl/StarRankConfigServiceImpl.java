package com.starnft.star.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.starnft.star.business.domain.StarRankConfig;
import com.starnft.star.business.mapper.StarRankConfigMapper;
import com.starnft.star.business.service.IStarRankConfigService;
import com.starnft.star.business.support.rank.core.IRankService;
import com.starnft.star.business.support.rank.model.RankDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 排行榜Service业务层处理
 *
 * @author shellya
 * @date 2022-06-28
 */
@Service
public class StarRankConfigServiceImpl implements IStarRankConfigService
{
    @Autowired
    private StarRankConfigMapper starRankConfigMapper;
    @Resource
    IRankService rankService;

    /**
     * 查询排行榜
     *
     * @param id 排行榜主键
     * @return 排行榜
     */
    @Override
    public StarRankConfig selectStarRankConfigById(Long id)
    {
        return starRankConfigMapper.selectStarRankConfigById(id);
    }

    /**
     * 查询排行榜列表
     *
     * @param starRankConfig 排行榜
     * @return 排行榜
     */
    @Override
    public List<StarRankConfig> selectStarRankConfigList(StarRankConfig starRankConfig)
    {
        return starRankConfigMapper.selectStarRankConfigList(starRankConfig);
    }

    /**
     * 新增排行榜
     *
     * @param starRankConfig 排行榜
     * @return 结果
     */
    @Override
    @Transactional
    public Boolean insertStarRankConfig(StarRankConfig starRankConfig)
    {
        starRankConfig.setId(IdUtil.getSnowflake().nextId());
        starRankConfigMapper.insertStarRankConfig(starRankConfig);
        RankDefinition rankDefinition = new RankDefinition();
        BeanUtil.copyProperties(starRankConfig,rankDefinition);
        boolean rank = rankService.createRank(starRankConfig.getRankName(), rankDefinition);
        return rank;
    }

    /**
     * 修改排行榜
     *
     * @param starRankConfig 排行榜
     * @return 结果
     */
    @Override
    @Transactional
    public int updateStarRankConfig(StarRankConfig starRankConfig)
    {
        starRankConfigMapper.updateStarRankConfig(starRankConfig);
        RankDefinition rankDefinition = new RankDefinition();
        BeanUtil.copyProperties(starRankConfig,rankDefinition);
         rankService.updateRank(starRankConfig.getRankName(), rankDefinition);
        return 1;
    }

    /**
     * 批量删除排行榜
     *
     * @param ids 需要删除的排行榜主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteStarRankConfigByIds(Long[] ids)
    {
        for (Long id : ids) {
            StarRankConfig starRankConfig = starRankConfigMapper.selectStarRankConfigById(id);
            rankService.deleteRank(starRankConfig.getRankName());
        }
        return starRankConfigMapper.deleteStarRankConfigByIds(ids);
    }

    /**
     * 删除排行榜信息
     *
     * @param id 排行榜主键
     * @return 结果
     */
    @Override
    public int deleteStarRankConfigById(Long id)
    {
        StarRankConfig starRankConfig = starRankConfigMapper.selectStarRankConfigById(id);
        rankService.deleteRank(starRankConfig.getRankName());
        return starRankConfigMapper.deleteStarRankConfigById(id);
    }
}
