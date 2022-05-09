package com.starnft.star.domain.user.repository;


import com.starnft.star.domain.user.model.dto.UserInfoAdd;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.UserPwdChangeLogsVO;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;

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
     * 新增用户
     * @param req
     * @return
     */
    Integer addUserInfo(UserInfoAdd req);

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
}
