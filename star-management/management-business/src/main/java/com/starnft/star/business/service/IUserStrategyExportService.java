package com.starnft.star.business.service;

import java.util.List;
import com.starnft.star.business.domain.UserStrategyExport;

/**
 * 用户策略计算结果Service接口
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
public interface IUserStrategyExportService 
{
    /**
     * 查询用户策略计算结果
     * 
     * @param id 用户策略计算结果主键
     * @return 用户策略计算结果
     */
    public UserStrategyExport selectUserStrategyExportById(Long id);

    /**
     * 查询用户策略计算结果列表
     * 
     * @param userStrategyExport 用户策略计算结果
     * @return 用户策略计算结果集合
     */
    public List<UserStrategyExport> selectUserStrategyExportList(UserStrategyExport userStrategyExport);

    /**
     * 新增用户策略计算结果
     * 
     * @param userStrategyExport 用户策略计算结果
     * @return 结果
     */
    public int insertUserStrategyExport(UserStrategyExport userStrategyExport);

    /**
     * 修改用户策略计算结果
     * 
     * @param userStrategyExport 用户策略计算结果
     * @return 结果
     */
    public int updateUserStrategyExport(UserStrategyExport userStrategyExport);

    /**
     * 批量删除用户策略计算结果
     * 
     * @param ids 需要删除的用户策略计算结果主键集合
     * @return 结果
     */
    public int deleteUserStrategyExportByIds(Long[] ids);

    /**
     * 删除用户策略计算结果信息
     * 
     * @param id 用户策略计算结果主键
     * @return 结果
     */
    public int deleteUserStrategyExportById(Long id);
}
