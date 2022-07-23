package com.starnft.star.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.business.domain.StarNftPromoteRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 推广记录Mapper接口
 *
 * @author shellya
 * @date 2022-07-23
 */
public interface StarNftPromoteRecordMapper
{
    /**
     * 查询推广记录
     *
     * @param id 推广记录主键
     * @return 推广记录
     */
    public StarNftPromoteRecord selectStarNftPromoteRecordById(Long id);

    /**
     * 查询推广记录列表
     *
     * @param starNftPromoteRecord 推广记录
     * @return 推广记录集合
     */
    public List<StarNftPromoteRecord> selectStarNftPromoteRecordList(StarNftPromoteRecord starNftPromoteRecord);

    /**
     * 新增推广记录
     *
     * @param starNftPromoteRecord 推广记录
     * @return 结果
     */
    public int insertStarNftPromoteRecord(StarNftPromoteRecord starNftPromoteRecord);
    public int insertStarNftPromoteRecordBatch(@Param("arrays") List<StarNftPromoteRecord> starNftPromoteRecord);

    /**
     * 修改推广记录
     *
     * @param starNftPromoteRecord 推广记录
     * @return 结果
     */
    public int updateStarNftPromoteRecord(StarNftPromoteRecord starNftPromoteRecord);

    /**
     * 删除推广记录
     *
     * @param id 推广记录主键
     * @return 结果
     */
    public int deleteStarNftPromoteRecordById(Long id);

    /**
     * 批量删除推广记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftPromoteRecordByIds(Long[] ids);
}
