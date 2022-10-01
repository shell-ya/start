package com.starnft.star.business.service;

import java.util.List;
import com.starnft.star.business.domain.DrawStrategyDetail;
import com.starnft.star.business.domain.vo.AwardVo;

/**
 * 策略明细Service接口
 *
 * @author zz
 * @date 2022-09-30
 */
public interface IDrawStrategyDetailService
{
    /**
     * 查询策略明细
     *
     * @param id 策略明细主键
     * @return 策略明细
     */
    public DrawStrategyDetail selectDrawStrategyDetailById(Long id);

    /**
     * 查询策略明细列表
     *
     * @param drawStrategyDetail 策略明细
     * @return 策略明细集合
     */
    public List<DrawStrategyDetail> selectDrawStrategyDetailList(DrawStrategyDetail drawStrategyDetail);

    /**
     * 新增策略明细
     *
     * @param drawStrategyDetail 策略明细
     * @return 结果
     */
    public int insertDrawStrategyDetail(DrawStrategyDetail drawStrategyDetail);

    /**
     * 修改策略明细
     *
     * @param drawStrategyDetail 策略明细
     * @return 结果
     */
    public int updateDrawStrategyDetail(DrawStrategyDetail drawStrategyDetail);

    /**
     * 批量删除策略明细
     *
     * @param ids 需要删除的策略明细主键集合
     * @return 结果
     */
    public int deleteDrawStrategyDetailByIds(Long[] ids);

    /**
     * 删除策略明细信息
     *
     * @param id 策略明细主键
     * @return 结果
     */
    public int deleteDrawStrategyDetailById(Long id);

    List<AwardVo> selectDrawStrategyDetailListByStrategyId(Long drawStrategyId);
}
