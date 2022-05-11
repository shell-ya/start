package com.starnft.star.infrastructure.mapper.user;

import com.starnft.star.infrastructure.entity.user.AccountUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccountUserEntity record);

    int insertSelective(AccountUserEntity record);

    AccountUserEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountUserEntity record);

    int updateByPrimaryKey(AccountUserEntity record);
}