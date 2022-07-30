package com.starnft.star.business.mapper;

import java.util.List;
import com.starnft.star.business.domain.StarNftCompose;

/**
 * 合成活动Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-30
 */
public interface StarNftComposeMapper 
{
    /**
     * 查询合成活动
     * 
     * @param id 合成活动主键
     * @return 合成活动
     */
    public StarNftCompose selectStarNftComposeById(Long id);

    /**
     * 查询合成活动列表
     * 
     * @param starNftCompose 合成活动
     * @return 合成活动集合
     */
    public List<StarNftCompose> selectStarNftComposeList(StarNftCompose starNftCompose);

    /**
     * 新增合成活动
     * 
     * @param starNftCompose 合成活动
     * @return 结果
     */
    public int insertStarNftCompose(StarNftCompose starNftCompose);

    /**
     * 修改合成活动
     * 
     * @param starNftCompose 合成活动
     * @return 结果
     */
    public int updateStarNftCompose(StarNftCompose starNftCompose);

    /**
     * 删除合成活动
     * 
     * @param id 合成活动主键
     * @return 结果
     */
    public int deleteStarNftComposeById(Long id);

    /**
     * 批量删除合成活动
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftComposeByIds(Long[] ids);
}
