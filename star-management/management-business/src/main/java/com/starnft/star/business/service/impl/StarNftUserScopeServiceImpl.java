package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftUserScope;
import com.starnft.star.business.mapper.StarNftUserScopeMapper;
import com.starnft.star.business.service.IStarNftUserScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户积分Service业务层处理
 *
 * @author shellya
 * @date 2022-06-25
 */
@Service
public class StarNftUserScopeServiceImpl implements IStarNftUserScopeService
{
    @Autowired
    private StarNftUserScopeMapper starNftUserScopeMapper;

    /**
     * 查询用户积分
     *
     * @param id 用户积分主键
     * @return 用户积分
     */
    @Override
    public StarNftUserScope selectStarNftUserScopeById(Long id)
    {
        return starNftUserScopeMapper.selectStarNftUserScopeById(id);
    }

    /**
     * 查询用户积分列表
     *
     * @param starNftUserScope 用户积分
     * @return 用户积分
     */
    @Override
    public List<StarNftUserScope> selectStarNftUserScopeList(StarNftUserScope starNftUserScope)
    {
        return starNftUserScopeMapper.selectStarNftUserScopeList(starNftUserScope);
    }

    /**
     * 新增用户积分
     *
     * @param starNftUserScope 用户积分
     * @return 结果
     */
    @Override
    public int insertStarNftUserScope(StarNftUserScope starNftUserScope)
    {
        return starNftUserScopeMapper.insertStarNftUserScope(starNftUserScope);
    }

    /**
     * 修改用户积分
     *
     * @param starNftUserScope 用户积分
     * @return 结果
     */
    @Override
    public int updateStarNftUserScope(StarNftUserScope starNftUserScope)
    {
        return starNftUserScopeMapper.updateStarNftUserScope(starNftUserScope);
    }

    /**
     * 批量删除用户积分
     *
     * @param ids 需要删除的用户积分主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserScopeByIds(Long[] ids)
    {
        return starNftUserScopeMapper.deleteStarNftUserScopeByIds(ids);
    }

    /**
     * 删除用户积分信息
     *
     * @param id 用户积分主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserScopeById(Long id)
    {
        return starNftUserScopeMapper.deleteStarNftUserScopeById(id);
    }

    @Override
    public List<StarNftUserScope> selectScopeByAccount(Long id) {
        StarNftUserScope starNftUserScope = new StarNftUserScope();
        starNftUserScope.setUserId(id);
        return starNftUserScopeMapper.selectStarNftUserScopeList(starNftUserScope);
    }
}
