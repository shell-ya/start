package com.starnft.star.infrastructure.repository;


import com.starnft.star.domain.user.model.dto.UserInfoAdd;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.infrastructure.entity.user.UserInfoEntity;
import com.starnft.star.infrastructure.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserRepository implements IUserRepository {

    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo queryUserInfoByPhone(String phone) {
        UserInfoEntity queryUser  = new UserInfoEntity();
        queryUser.setIsDeleted(Boolean.FALSE);
        queryUser.setPhone(phone);
        UserInfoEntity userInfoEntity = userInfoMapper.selectOne(new QueryWrapper<UserInfoEntity>().setEntity(queryUser));
        if (Objects.nonNull(userInfoEntity)){
            UserInfo userInfo = new UserInfo();
            userInfo.setAccount(userInfoEntity.getAccount());
            userInfo.setAvatar(userInfoEntity.getAvatar());
            userInfo.setIsActive(userInfoEntity.getIsActive());
            userInfo.setNickName(userInfoEntity.getNickName());
            userInfo.setPhone(userInfoEntity.getPhone());
            userInfo.setPassword(userInfoEntity.getPassword());
            userInfo.setPlyPassword(userInfoEntity.getPlyPassword());
            userInfo.setId(userInfoEntity.getId());
            return userInfo;
        }
        return null;
    }

    @Override
    public Integer addUserInfo(UserInfoAdd req) {
        UserInfoEntity addUserInfo = new UserInfoEntity();
        addUserInfo.setCreatedAt(new Date());
        addUserInfo.setCreatedBy(req.getCreateBy());
        addUserInfo.setModifiedAt(new Date());
        addUserInfo.setModifiedBy(req.getCreateBy());
        addUserInfo.setIsDeleted(Boolean.FALSE);
        addUserInfo.setIsActive(Boolean.FALSE);
        addUserInfo.setIsAuth(Boolean.FALSE);
        addUserInfo.setAccount(req.getUserId());
        addUserInfo.setNickName(req.getNickName());
        addUserInfo.setPhone(req.getPhone());
        return userInfoMapper.insert(addUserInfo);
    }

    @Override
    public Integer setUpPassword(UserInfo userInfo, String password) {
        Integer updateRows = null;
        if (StringUtils.isBlank(userInfo.getPassword())) {
            UserInfoEntity updateUserInfo = new UserInfoEntity();
            updateUserInfo.setId(updateUserInfo.getId());
            updateUserInfo.setPassword(StarUtils.getSHA256Str(password));
            updateRows = userInfoMapper.updateByPrimaryKey(updateUserInfo);

            //todo 密码修改记录

        }

        return updateRows;
    }
}
