package com.starnft.star.infrastructure.repository;


import com.starnft.star.domain.user.model.dto.UserInfoAdd;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserRepository implements IUserRepository {

    @Override
    public UserInfo queryUserInfoByPhone(String phone) {
        return null;
    }

    @Override
    public Integer addUserInfo(UserInfoAdd req) {
        return null;
    }
}
