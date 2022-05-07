package com.starnft.star.domain.user.repository;


import com.starnft.star.domain.user.model.dto.UserInfoAdd;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
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
}
