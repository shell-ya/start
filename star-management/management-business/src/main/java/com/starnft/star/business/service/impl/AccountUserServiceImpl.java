package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.AccountUser;
import com.starnft.star.business.mapper.AccountUserMapper;
import com.starnft.star.business.service.IAccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service业务层处理
 *
 * @author shellya
 * @date 2022-05-28
 */
@Service
public class AccountUserServiceImpl implements IAccountUserService
{
    @Autowired
    private AccountUserMapper accountUserMapper;

    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    @Override
    public AccountUser selectAccountUserById(Long id)
    {
        return accountUserMapper.selectAccountUserById(id);
    }

    /**
     * 查询用户列表
     *
     * @param accountUser 用户
     * @return 用户
     */
    @Override
    public List<AccountUser> selectAccountUserList(AccountUser accountUser)
    {
        return accountUserMapper.selectAccountUserList(accountUser);
    }

    /**
     * 新增用户
     *
     * @param accountUser 用户
     * @return 结果
     */
    @Override
    public int insertAccountUser(AccountUser accountUser)
    {
        return accountUserMapper.insertAccountUser(accountUser);
    }

    /**
     * 修改用户
     *
     * @param accountUser 用户
     * @return 结果
     */
    @Override
    public int updateAccountUser(AccountUser accountUser)
    {
        return accountUserMapper.updateAccountUser(accountUser);
    }

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deleteAccountUserByIds(Long[] ids)
    {
        return accountUserMapper.deleteAccountUserByIds(ids);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    @Override
    public int deleteAccountUserById(Long id)
    {
        return accountUserMapper.deleteAccountUserById(id);
    }

    @Override
    public AccountUser selectUserByAccount(Long account) {
        return accountUserMapper.selectUserByAccount(account);
    }
}
