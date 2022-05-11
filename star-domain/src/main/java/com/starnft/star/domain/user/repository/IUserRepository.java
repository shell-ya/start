package com.starnft.star.domain.user.repository;


import com.starnft.star.domain.user.model.dto.UserInfoAddDTO;
import com.starnft.star.domain.user.model.dto.UserInfoUpdateDTO;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.model.vo.UserPwdChangeLogsVO;

/**
 * @author Ryan z
 */
public interface IUserRepository {

    /**
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    UserInfo queryUserInfoByPhone(String phone);

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    UserInfo queryUserInfoByUserId(Long userId);

    /**
     * 新增用户
     * @param req
     * @return
     */
    Integer addUserInfo(UserInfoAddDTO req);

    /**
     * 设置初始密码
     * @param userInfo
     * @param password
     * @return
     */
    Integer setUpPassword(UserInfo userInfo , String password);

    /**
     * 添加修改密码记录
     * @param userId
     * @param password
     * @return
     */
    Integer addUserPwdLog(Long userId, String password);

    /**
     * 查询用户历史密码 10条
     * @param userId
     * @return
     */
    UserPwdChangeLogsVO queryPwdLog(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param password
     * @return
     */
    Integer changePwd(Long userId , String password);

    /**
     * 添加登录日志
     * @param userId
     * @return
     */
    Integer addLoginLog(Long userId);

    /**
     * 清除登录日志
     * @param userId
     * @return
     */
    Integer deleteLoginLog(Long userId);

    /**
     * 修改支付密码
     * @param userId
     * @param payPassword
     * @return
     */
    Boolean changePayPwd(Long userId , String payPassword);

    /**
     * 修改用户信息
     * @param req
     * @return
     */
    Integer updateUserInfo(UserInfoUpdateDTO req);
}
