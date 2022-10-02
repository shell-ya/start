package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.UserStrategyExport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 用户策略计算结果表DAO
 */
@Mapper
public interface IUserStrategyExportDao {

    /**
     * 新增数据
     * @param userStrategyExport 用户策略
     */
    void insert(UserStrategyExport userStrategyExport);

    /**
     * 查询数据
     * @param uId 用户ID
     * @return 用户策略
     */
    List<UserStrategyExport> queryUserStrategyExportByUId(String uId);

    /**
     * 更新发奖状态
     * @param userStrategyExport 发奖信息
     */
    void updateUserAwardState(UserStrategyExport userStrategyExport);

    /**
     * 更新发送MQ状态
     * @param userStrategyExport 发送消息
     */
    void updateInvoiceMqState(UserStrategyExport userStrategyExport);

    /**
     * 扫描发货单 MQ 状态，把未发送 MQ 的单子扫描出来，做补偿 // TODO: 2022/8/19
     *
     * @return 发货单
     */
    List<UserStrategyExport> scanInvoiceMqState();

    List<UserStrategyExport> queryUserStrategyExportNum(String uid);

    List<UserStrategyExport> queryUserStrategyExportList(String uid);


    boolean deleteExport(String orderId);

    UserStrategyExport queryUserHash(@Param("uid") String uid, @Param("awardName") String awardName);


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
     * 删除用户策略计算结果
     *
     * @param id 用户策略计算结果主键
     * @return 结果
     */
    public int deleteUserStrategyExportById(Long id);

    /**
     * 批量删除用户策略计算结果
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserStrategyExportByIds(Long[] ids);
}
