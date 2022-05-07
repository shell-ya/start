package com.starnft.star.domain.user.service;


import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;

/**
 * @author Ryan z
 */
public interface IUserService {


    UserInfoVO login(UserLoginDTO req);

    UserRegisterInfoVO loginByPhone(UserLoginDTO req);
}
