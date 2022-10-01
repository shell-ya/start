package com.starnft.star.business.service.impl;

import java.util.List;
import com.starnft.star.business.mapper.IUserStrategyExportDao;
import com.starnft.star.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starnft.star.business.domain.UserStrategyExport;
import com.starnft.star.business.service.IUserStrategyExportService;

/**
 * 用户策略计算结果Service业务层处理
 *
 * @author ruoyi
 * @date 2022-09-29
 */
@Service
public class UserStrategyExportServiceImpl implements IUserStrategyExportService
{
    @Autowired
    private IUserStrategyExportDao userStrategyExportMapper;

    /**
     * 查询用户策略计算结果
     *
     * @param id 用户策略计算结果主键
     * @return 用户策略计算结果
     */
    @Override
    public UserStrategyExport selectUserStrategyExportById(Long id)
    {
        return userStrategyExportMapper.selectUserStrategyExportById(id);
    }

    /**
     * 查询用户策略计算结果列表
     *
     * @param userStrategyExport 用户策略计算结果
     * @return 用户策略计算结果
     */
    @Override
    public List<UserStrategyExport> selectUserStrategyExportList(UserStrategyExport userStrategyExport)
    {
        return userStrategyExportMapper.selectUserStrategyExportList(userStrategyExport);
    }

    /**
     * 新增用户策略计算结果
     *
     * @param userStrategyExport 用户策略计算结果
     * @return 结果
     */
    @Override
    public int insertUserStrategyExport(UserStrategyExport userStrategyExport)
    {
        userStrategyExport.setCreateTime(DateUtils.getNowDate());
        return userStrategyExportMapper.insertUserStrategyExport(userStrategyExport);
    }

    /**
     * 修改用户策略计算结果
     *
     * @param userStrategyExport 用户策略计算结果
     * @return 结果
     */
    @Override
    public int updateUserStrategyExport(UserStrategyExport userStrategyExport)
    {
        userStrategyExport.setUpdateTime(DateUtils.getNowDate());
        return userStrategyExportMapper.updateUserStrategyExport(userStrategyExport);
    }

    /**
     * 批量删除用户策略计算结果
     *
     * @param ids 需要删除的用户策略计算结果主键
     * @return 结果
     */
    @Override
    public int deleteUserStrategyExportByIds(Long[] ids)
    {
        return userStrategyExportMapper.deleteUserStrategyExportByIds(ids);
    }

    /**
     * 删除用户策略计算结果信息
     *
     * @param id 用户策略计算结果主键
     * @return 结果
     */
    @Override
    public int deleteUserStrategyExportById(Long id)
    {
        return userStrategyExportMapper.deleteUserStrategyExportById(id);
    }
}
