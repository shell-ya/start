package com.starnft.star.infrastructure.mapper.user;

import com.starnft.star.infrastructure.entity.user.UserDataAuthorizationAgreementSignEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDataAuthorizationAgreementSignMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDataAuthorizationAgreementSignEntity record);

    int insertSelective(UserDataAuthorizationAgreementSignEntity record);

    UserDataAuthorizationAgreementSignEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDataAuthorizationAgreementSignEntity record);

    int updateByPrimaryKey(UserDataAuthorizationAgreementSignEntity record);
}