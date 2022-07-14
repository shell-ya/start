package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarNftPublisher;
import com.starnft.star.business.domain.vo.PublisherVo;
import com.starnft.star.common.core.domain.TreeSelect;

import java.util.List;

/**
 * 工作室Service接口
 *
 * @author shellya
 * @date 2022-06-26
 */
public interface IStarNftPublisherService
{
    /**
     * 查询工作室
     *
     * @param id 工作室主键
     * @return 工作室
     */
    public StarNftPublisher selectStarNftPublisherById(Long id);

    /**
     * 查询工作室列表
     *
     * @param starNftPublisher 工作室
     * @return 工作室集合
     */
    public List<StarNftPublisher> selectStarNftPublisherList(StarNftPublisher starNftPublisher);

    /**
     * 新增工作室
     *
     * @param starNftPublisher 工作室
     * @return 结果
     */
    public int insertStarNftPublisher(StarNftPublisher starNftPublisher);

    /**
     * 修改工作室
     *
     * @param starNftPublisher 工作室
     * @return 结果
     */
    public int updateStarNftPublisher(StarNftPublisher starNftPublisher);

    /**
     * 批量删除工作室
     *
     * @param ids 需要删除的工作室主键集合
     * @return 结果
     */
    public int deleteStarNftPublisherByIds(Long[] ids);

    /**
     * 删除工作室信息
     *
     * @param id 工作室主键
     * @return 结果
     */
    public int deleteStarNftPublisherById(Long id);

    public List<PublisherVo> selectAllPublisher();

    List<TreeSelect> treeSelect(StarNftPublisher starNftPublisher);

}
