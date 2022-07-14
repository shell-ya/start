package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftScopeRecord;

import java.util.List;

/**
 * 积分数据Mapper接口
 *
 * @author shellya
 * @date 2022-06-25
 */
public interface StarNftScopeRecordMapper
{
    /**
     * 查询积分数据
     *
     * @param id 积分数据主键
     * @return 积分数据
     */
    public StarNftScopeRecord selectStarNftScopeRecordById(Long id);

    /**
     * 查询积分数据列表
     *
     * @param starNftScopeRecord 积分数据
     * @return 积分数据集合
     */
    public List<StarNftScopeRecord> selectStarNftScopeRecordList(StarNftScopeRecord starNftScopeRecord);

    /**
     * 新增积分数据
     *
     * @param starNftScopeRecord 积分数据
     * @return 结果
     */
    public int insertStarNftScopeRecord(StarNftScopeRecord starNftScopeRecord);

    /**
     * 修改积分数据
     *
     * @param starNftScopeRecord 积分数据
     * @return 结果
     */
    public int updateStarNftScopeRecord(StarNftScopeRecord starNftScopeRecord);

    /**
     * 删除积分数据
     *
     * @param id 积分数据主键
     * @return 结果
     */
    public int deleteStarNftScopeRecordById(Long id);

    /**
     * 批量删除积分数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftScopeRecordByIds(Long[] ids);
}
