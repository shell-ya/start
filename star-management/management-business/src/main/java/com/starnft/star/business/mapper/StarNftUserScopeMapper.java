package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftUserScope;

import java.util.List;

/**
 * 用户积分Mapper接口
 *
 * @author shellya
 * @date 2022-06-25
 */
public interface StarNftUserScopeMapper
{
    /**
     * 查询用户积分
     *
     * @param id 用户积分主键
     * @return 用户积分
     */
    public StarNftUserScope selectStarNftUserScopeById(Long id);

    /**
     * 查询用户积分列表
     *
     * @param starNftUserScope 用户积分
     * @return 用户积分集合
     */
    public List<StarNftUserScope> selectStarNftUserScopeList(StarNftUserScope starNftUserScope);
    public StarNftUserScope selectStarNftUserScope(StarNftUserScope starNftUserScope);

    /**
     * 新增用户积分
     *
     * @param starNftUserScope 用户积分
     * @return 结果
     */
    public int insertStarNftUserScope(StarNftUserScope starNftUserScope);

    /**
     * 修改用户积分
     *
     * @param starNftUserScope 用户积分
     * @return 结果
     */
    public int updateStarNftUserScope(StarNftUserScope starNftUserScope);
    public int updateStarNftUserScopeVersion(StarNftUserScope starNftUserScope);

    /**
     * 删除用户积分
     *
     * @param id 用户积分主键
     * @return 结果
     */
    public int deleteStarNftUserScopeById(Long id);

    /**
     * 批量删除用户积分
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftUserScopeByIds(Long[] ids);
}
