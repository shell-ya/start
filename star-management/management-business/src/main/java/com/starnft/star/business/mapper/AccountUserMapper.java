package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.AccountUser;

import java.util.Date;
import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author shellya
 * @date 2022-05-28
 */
public interface AccountUserMapper
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
     * 删除用户
     *
     * @param id 用户主键
     * @return 结果
     */
    public int deleteAccountUserById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountUserByIds(Long[] ids);

    public List<Integer> getDayUserCount(Date date);
    public List<Integer> getToDayUserCount(Date date);
}
