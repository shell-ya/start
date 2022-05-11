package com.starnft.star.infrastructure.mapper.user;

import com.starnft.star.infrastructure.entity.user.UserAgreementEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAgreementMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAgreementEntity record);

    int insertSelective(UserAgreementEntity record);

    UserAgreementEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAgreementEntity record);

    int updateByPrimaryKeyWithBLOBs(UserAgreementEntity record);

    int updateByPrimaryKey(UserAgreementEntity record);
}