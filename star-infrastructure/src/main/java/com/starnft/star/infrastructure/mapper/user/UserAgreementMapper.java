package com.starnft.star.infrastructure.mapper.user;

import com.starnft.star.infrastructure.entity.user.UserAgreementEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAgreementMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAgreementEntity record);

    int insertSelective(UserAgreementEntity record);

    UserAgreementEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAgreementEntity record);

    int updateByPrimaryKeyWithBLOBs(UserAgreementEntity record);

    int updateByPrimaryKey(UserAgreementEntity record);

    UserAgreementEntity queryAgreementInfoByType(Integer agreementType);

    List<UserAgreementEntity> queryNewAgreementByScene(@Param("scene") Integer scene);
}