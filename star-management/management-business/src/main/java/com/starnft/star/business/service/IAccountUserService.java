package com.starnft.star.business.service;

import com.starnft.star.business.domain.AccountUser;
import com.starnft.star.business.domain.vo.UserInfo;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author shellya
 * @date 2022-05-28
 */
public interface IAccountUserService
{
    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    public AccountUser selectAccountUserById(Long id);

    /**
     * 查询用户列表
     *
     * @param accountUser 用户
     * @return 用户集合
     */
    public List<AccountUser> selectAccountUserList(AccountUser accountUser);

    /**
     * 新增用户
     *
     * @param accountUser 用户
     * @return 结果
     */
    public int insertAccountUser(AccountUser accountUser);

    /**
     * 修改用户
     *
     * @param accountUser 用户
     * @return 结果
     */
    public int updateAccountUser(AccountUser accountUser);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键集合
     * @return 结果
     */
    public int deleteAccountUserByIds(Long[] ids);

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    public int deleteAccountUserById(Long id);

    public AccountUser selectUserByAccount(Long account);

    List<UserInfo> queryUserId(String[] phones);
}
