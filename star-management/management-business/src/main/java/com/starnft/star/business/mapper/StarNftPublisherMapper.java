package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftPublisher;
import com.starnft.star.business.domain.vo.PublisherVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作室Mapper接口
 *
 * @author shellya
 * @date 2022-06-26
 */
public interface StarNftPublisherMapper
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
     * 删除工作室
     *
     * @param id 工作室主键
     * @return 结果
     */
    public int deleteStarNftPublisherById(Long id);

    /**
     * 批量删除工作室
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftPublisherByIds(Long[] ids);

    List<PublisherVo> selectParentPublisher();

    List<PublisherVo> selectChildrenPublisher(@Param("pid") Long pid);
    List<StarNftPublisher> selectChildrenPublisherByPids(@Param("array") List<Long> array);
}
