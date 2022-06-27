package com.starnft.star.infrastructure.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.user.UserDataAuthorizationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

public interface UserDataAuthorizationMapper{
    int deleteByPrimaryKey(Long id);

    int insert(UserDataAuthorizationEntity record);

    int insertSelective(UserDataAuthorizationEntity record);

    UserDataAuthorizationEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDataAuthorizationEntity record);

    int updateByPrimaryKey(UserDataAuthorizationEntity record);
}