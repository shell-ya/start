package com.starnft.star.infrastructure.mapper.user;

import com.starnft.star.domain.user.model.dto.AgreementSignDTO;
import com.starnft.star.infrastructure.entity.user.UserDataAuthorizationAgreementSignEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDataAuthorizationAgreementSignMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDataAuthorizationAgreementSignEntity record);

    int insertSelective(UserDataAuthorizationAgreementSignEntity record);

    UserDataAuthorizationAgreementSignEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDataAuthorizationAgreementSignEntity record);

    int updateByPrimaryKey(UserDataAuthorizationAgreementSignEntity record);

    List<String> batchSelectUserAgreementId(@Param("userId") Long userId);

    int batchInsertAgreementSign(@Param("agreementSign")List<AgreementSignDTO> agreementSigns);
}