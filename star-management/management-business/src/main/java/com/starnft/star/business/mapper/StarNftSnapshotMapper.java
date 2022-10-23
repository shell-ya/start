package com.starnft.star.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.business.domain.StarNftSnapshot;

import java.util.List;

/**
 * 快照Mapper接口
 *
 * @author zz
 * @date 2022-10-18
 */
public interface StarNftSnapshotMapper  extends BaseMapper<StarNftSnapshot>
{
    /**
     * 查询快照
     *
     * @param id 快照主键
     * @return 快照
     */
    public StarNftSnapshot selectStarNftSnapshotById(Long id);

    /**
     * 查询快照列表
     *
     * @param starNftSnapshot 快照
     * @return 快照集合
     */
    public List<StarNftSnapshot> selectStarNftSnapshotList(StarNftSnapshot starNftSnapshot);

    /**
     * 新增快照
     *
     * @param starNftSnapshot 快照
     * @return 结果
     */
    public int insertStarNftSnapshot(StarNftSnapshot starNftSnapshot);

    /**
     * 修改快照
     *
     * @param starNftSnapshot 快照
     * @return 结果
     */
    public int updateStarNftSnapshot(StarNftSnapshot starNftSnapshot);

    /**
     * 删除快照
     *
     * @param id 快照主键
     * @return 结果
     */
    public int deleteStarNftSnapshotById(Long id);

    /**
     * 批量删除快照
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftSnapshotByIds(Long[] ids);
}
