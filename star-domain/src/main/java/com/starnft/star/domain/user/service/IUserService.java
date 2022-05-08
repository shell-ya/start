package com.starnft.star.domain.user.service;


import com.starnft.star.domain.user.model.dto.AuthMaterialDTO;
import com.starnft.star.domain.user.model.dto.UserLoginDTO;
import com.starnft.star.domain.user.model.dto.UserVerifyCodeDTO;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.model.vo.UserVerifyCode;

/**
 * @author WeiChunLAI
 */
public interface IUserService {

    UserInfoVO login(UserLoginDTO req);

    UserRegisterInfoVO loginByPhone(UserLoginDTO req);

    Boolean logOut(Long userId);

    UserVerifyCode getVerifyCode(UserVerifyCodeDTO req);

    Boolean setUpPassword(AuthMaterialDTO materialDTO);

    Boolean changePassword(AuthMaterialDTO materialDTO);
}
