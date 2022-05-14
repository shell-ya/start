package com.starnft.star.infrastructure.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.user.UserAgreementPopupEntity;
import org.apache.ibatis.annotations.Mapper;

public interface UserAgreementPopupMapper extends BaseMapper<UserAgreementPopupEntity> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserAgreementPopupEntity record);

    UserAgreementPopupEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAgreementPopupEntity record);

    int updateByPrimaryKey(UserAgreementPopupEntity record);
}