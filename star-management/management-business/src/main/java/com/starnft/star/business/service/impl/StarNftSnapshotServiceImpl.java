package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftSnapshot;
import com.starnft.star.business.mapper.StarNftSnapshotMapper;
import com.starnft.star.business.service.IStarNftSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StarNftSnapshotServiceImpl implements IStarNftSnapshotService {

    @Autowired
    private StarNftSnapshotMapper starNftSnapshotMapper;

    /**
     * 查询快照
     *
     * @param id 快照主键
     * @return 快照
     */
    @Override
    public StarNftSnapshot selectStarNftSnapshotById(Long id)
    {
        return starNftSnapshotMapper.selectStarNftSnapshotById(id);
    }

    /**
     * 查询快照列表
     *
     * @param starNftSnapshot 快照
     * @return 快照
     */
    @Override
    public List<StarNftSnapshot> selectStarNftSnapshotList(StarNftSnapshot starNftSnapshot)
    {
        return starNftSnapshotMapper.selectStarNftSnapshotList(starNftSnapshot);
    }

    /**
     * 新增快照
     *
     * @param starNftSnapshot 快照
     * @return 结果
     */
    @Override
    public int insertStarNftSnapshot(StarNftSnapshot starNftSnapshot)
    {
        starNftSnapshot.setIsDeleted(0);
        starNftSnapshot.setCreateAt(new Date());
        starNftSnapshot.setUpdateAt(new Date());
        return starNftSnapshotMapper.insertStarNftSnapshot(starNftSnapshot);
    }

    /**
     * 修改快照
     *
     * @param starNftSnapshot 快照
     * @return 结果
     */
    @Override
    public int updateStarNftSnapshot(StarNftSnapshot starNftSnapshot)
    {
        return starNftSnapshotMapper.updateStarNftSnapshot(starNftSnapshot);
    }

    /**
     * 批量删除快照
     *
     * @param ids 需要删除的快照主键
     * @return 结果
     */
    @Override
    public int deleteStarNftSnapshotByIds(Long[] ids)
    {
        return starNftSnapshotMapper.deleteStarNftSnapshotByIds(ids);
    }

    /**
     * 删除快照信息
     *
     * @param id 快照主键
     * @return 结果
     */
    @Override
    public int deleteStarNftSnapshotById(Long id)
    {
        return starNftSnapshotMapper.deleteStarNftSnapshotById(id);
    }

    @Override
    public StarNftSnapshot getById(Long id) {
        return starNftSnapshotMapper.selectById(id);
    }

    @Override
    public int saveSnapshot(StarNftSnapshot starNftSnapshot) {
        starNftSnapshot.setIsDeleted(0);
        starNftSnapshot.setCreateAt(new Date());
        starNftSnapshot.setUpdateAt(new Date());
        return  starNftSnapshotMapper.insert(starNftSnapshot);
    }

    @Override
    public int updateSnapshot(StarNftSnapshot snapshot) {
       return starNftSnapshotMapper.updateById(snapshot);
    }



}
