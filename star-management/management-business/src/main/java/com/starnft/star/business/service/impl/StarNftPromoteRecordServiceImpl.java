package com.starnft.star.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.mapper.StarNftPromoteRecordMapper;
import com.starnft.star.business.domain.StarNftPromoteRecord;
import com.starnft.star.business.service.IStarNftPromoteRecordService;

/**
 * 推广记录Service业务层处理
 *
 * @author shellya
 * @date 2022-07-23
 */
@Service
public class StarNftPromoteRecordServiceImpl  implements IStarNftPromoteRecordService
{
    @Autowired
    private StarNftPromoteRecordMapper starNftPromoteRecordMapper;

    /**
     * 查询推广记录
     *
     * @param id 推广记录主键
     * @return 推广记录
     */
    @Override
    public StarNftPromoteRecord selectStarNftPromoteRecordById(Long id)
    {
        return starNftPromoteRecordMapper.selectStarNftPromoteRecordById(id);
    }

    /**
     * 查询推广记录列表
     *
     * @param starNftPromoteRecord 推广记录
     * @return 推广记录
     */
    @Override
    public List<StarNftPromoteRecord> selectStarNftPromoteRecordList(StarNftPromoteRecord starNftPromoteRecord)
    {
        return starNftPromoteRecordMapper.selectStarNftPromoteRecordList(starNftPromoteRecord);
    }

    /**
     * 新增推广记录
     *
     * @param starNftPromoteRecord 推广记录
     * @return 结果
     */
    @Override
    public int insertStarNftPromoteRecord(StarNftPromoteRecord starNftPromoteRecord)
    {
        return starNftPromoteRecordMapper.insertStarNftPromoteRecord(starNftPromoteRecord);
    }
    @Override
    public int insertStarNftPromoteRecordBatch(List<StarNftPromoteRecord> starNftPromoteRecord)
    {
        return  starNftPromoteRecordMapper.insertStarNftPromoteRecordBatch(starNftPromoteRecord);

    }
    /**
     * 修改推广记录
     *
     * @param starNftPromoteRecord 推广记录
     * @return 结果
     */
    @Override
    public int updateStarNftPromoteRecord(StarNftPromoteRecord starNftPromoteRecord)
    {
        return starNftPromoteRecordMapper.updateStarNftPromoteRecord(starNftPromoteRecord);
    }

    /**
     * 批量删除推广记录
     *
     * @param ids 需要删除的推广记录主键
     * @return 结果
     */
    @Override
    public int deleteStarNftPromoteRecordByIds(Long[] ids)
    {
        return starNftPromoteRecordMapper.deleteStarNftPromoteRecordByIds(ids);
    }

    /**
     * 删除推广记录信息
     *
     * @param id 推广记录主键
     * @return 结果
     */
    @Override
    public int deleteStarNftPromoteRecordById(Long id)
    {
        return starNftPromoteRecordMapper.deleteStarNftPromoteRecordById(id);
    }
}
