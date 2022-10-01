package com.starnft.star.business.service.impl;

import java.util.List;

import com.starnft.star.business.domain.vo.AwardVo;
import com.starnft.star.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.DrawStrategyDetailMapper;
import com.starnft.star.business.domain.DrawStrategyDetail;
import com.starnft.star.business.service.IDrawStrategyDetailService;

/**
 * 策略明细Service业务层处理
 *
 * @author zz
 * @date 2022-09-30
 */
@Service
public class DrawStrategyDetailServiceImpl implements IDrawStrategyDetailService
{
    @Autowired
    private DrawStrategyDetailMapper drawStrategyDetailMapper;

    /**
     * 查询策略明细
     *
     * @param id 策略明细主键
     * @return 策略明细
     */
    @Override
    public DrawStrategyDetail selectDrawStrategyDetailById(Long id)
    {
        return drawStrategyDetailMapper.selectDrawStrategyDetailById(id);
    }

    /**
     * 查询策略明细列表
     *
     * @param drawStrategyDetail 策略明细
     * @return 策略明细
     */
    @Override
    public List<DrawStrategyDetail> selectDrawStrategyDetailList(DrawStrategyDetail drawStrategyDetail)
    {
        return drawStrategyDetailMapper.selectDrawStrategyDetailList(drawStrategyDetail);
    }

    /**
     * 新增策略明细
     *
     * @param drawStrategyDetail 策略明细
     * @return 结果
     */
    @Override
    public int insertDrawStrategyDetail(DrawStrategyDetail drawStrategyDetail)
    {
        drawStrategyDetail.setCreateTime(DateUtils.getNowDate());
        return drawStrategyDetailMapper.insertDrawStrategyDetail(drawStrategyDetail);
    }

    /**
     * 修改策略明细
     *
     * @param drawStrategyDetail 策略明细
     * @return 结果
     */
    @Override
    public int updateDrawStrategyDetail(DrawStrategyDetail drawStrategyDetail)
    {
        drawStrategyDetail.setUpdateTime(DateUtils.getNowDate());
        return drawStrategyDetailMapper.updateDrawStrategyDetail(drawStrategyDetail);
    }

    /**
     * 批量删除策略明细
     *
     * @param ids 需要删除的策略明细主键
     * @return 结果
     */
    @Override
    public int deleteDrawStrategyDetailByIds(Long[] ids)
    {
        return drawStrategyDetailMapper.deleteDrawStrategyDetailByIds(ids);
    }

    /**
     * 删除策略明细信息
     *
     * @param id 策略明细主键
     * @return 结果
     */
    @Override
    public int deleteDrawStrategyDetailById(Long id)
    {
        return drawStrategyDetailMapper.deleteDrawStrategyDetailById(id);
    }

    @Override
    public List<AwardVo> selectDrawStrategyDetailListByStrategyId(Long drawStrategyId) {
        return drawStrategyDetailMapper.selectDrawStrategyDetailListByStrategyId(drawStrategyId);
    }
}
