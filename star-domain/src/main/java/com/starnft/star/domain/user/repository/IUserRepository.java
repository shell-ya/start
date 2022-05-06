package com.starnft.star.domain.user.repository;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.model.dto.UserLoginDTO;
import com.starnft.star.domain.model.dto.UserRegisterDTO;
import com.starnft.star.domain.model.vo.UserInfoVO;
import com.starnft.star.domain.model.vo.UserRegisterInfoVO;

/**
 * @author Ryan z
 */
public interface IUserRepository {

    UserInfoVO login(UserLoginDTO req);

    UserRegisterInfoVO register(UserRegisterDTO req);
}
