package com.starnft.star.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.user.UserPwdHistoryLogEntity;

import java.util.List;

/**
 * @author WeiChunLAI
 */
public interface UserPwdHistoryLogMapper extends BaseMapper<UserPwdHistoryLogEntity> {

    List<UserPwdHistoryLogEntity> selectByParamAndLimiTen(Long userId);
}
