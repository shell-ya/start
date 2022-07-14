package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftScopeRecord;
import com.starnft.star.business.mapper.StarNftScopeRecordMapper;
import com.starnft.star.business.service.IStarNftScopeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分数据Service业务层处理
 *
 * @author shellya
 * @date 2022-06-25
 */
@Service
public class StarNftScopeRecordServiceImpl implements IStarNftScopeRecordService
{
    @Autowired
    private StarNftScopeRecordMapper starNftScopeRecordMapper;

    /**
     * 查询积分数据
     *
     * @param id 积分数据主键
     * @return 积分数据
     */
    @Override
    public StarNftScopeRecord selectStarNftScopeRecordById(Long id)
    {
        return starNftScopeRecordMapper.selectStarNftScopeRecordById(id);
    }

    /**
     * 查询积分数据列表
     *
     * @param starNftScopeRecord 积分数据
     * @return 积分数据
     */
    @Override
    public List<StarNftScopeRecord> selectStarNftScopeRecordList(StarNftScopeRecord   starNftScopeRecord)
    {
        return starNftScopeRecordMapper.selectStarNftScopeRecordList(starNftScopeRecord);
    }

    /**
     * 新增积分数据
     *
     * @param starNftScopeRecord 积分数据
     * @return 结果
     */
    @Override
    public int insertStarNftScopeRecord(StarNftScopeRecord starNftScopeRecord)
    {
        return starNftScopeRecordMapper.insertStarNftScopeRecord(starNftScopeRecord);
    }

    /**
     * 修改积分数据
     *
     * @param starNftScopeRecord 积分数据
     * @return 结果
     */
    @Override
    public int updateStarNftScopeRecord(StarNftScopeRecord starNftScopeRecord)
    {
        return starNftScopeRecordMapper.updateStarNftScopeRecord(starNftScopeRecord);
    }

    /**
     * 批量删除积分数据
     *
     * @param ids 需要删除的积分数据主键
     * @return 结果
     */
    @Override
    public int deleteStarNftScopeRecordByIds(Long[] ids)
    {
        return starNftScopeRecordMapper.deleteStarNftScopeRecordByIds(ids);
    }

    /**
     * 删除积分数据信息
     *
     * @param id 积分数据主键
     * @return 结果
     */
    @Override
    public int deleteStarNftScopeRecordById(Long id)
    {
        return starNftScopeRecordMapper.deleteStarNftScopeRecordById(id);
    }
}
