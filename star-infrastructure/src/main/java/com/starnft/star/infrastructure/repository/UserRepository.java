package com.starnft.star.infrastructure.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starnft.star.common.enums.LoginStatus;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.domain.user.model.dto.UserInfoAdd;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.model.vo.UserPwdChangeLogsVO;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.infrastructure.entity.user.UserInfoEntity;
import com.starnft.star.infrastructure.entity.user.UserLoginLogEntity;
import com.starnft.star.infrastructure.entity.user.UserPwdHistoryLogEntity;
import com.starnft.star.infrastructure.mapper.UserInfoMapper;
import com.starnft.star.infrastructure.mapper.UserLoginLogMapper;
import com.starnft.star.infrastructure.mapper.UserPwdHistoryLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserRepository implements IUserRepository {

    @Resource
    UserInfoMapper userInfoMapper;
    @Resource
    UserPwdHistoryLogMapper userPwdHistoryLogMapper;
    @Resource
    UserLoginLogMapper userLoginLogMapper;

    @Override
    public UserInfo queryUserInfoByPhone(String phone) {
        UserInfoEntity queryUser = new UserInfoEntity();
        queryUser.setIsDelete(Boolean.FALSE);
        queryUser.setPhone(phone);
        UserInfoEntity userInfoEntity = userInfoMapper.selectOne(new QueryWrapper<UserInfoEntity>().setEntity(queryUser));
        if (Objects.nonNull(userInfoEntity)) {
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
        addUserInfo.setIsDelete(Boolean.FALSE);
        addUserInfo.setIsActive(Boolean.FALSE);
        addUserInfo.setIsAuth(Boolean.FALSE);
        addUserInfo.setAccount(req.getUserId());
        addUserInfo.setNickName(req.getNickName());
        addUserInfo.setPhone(req.getPhone());
        return userInfoMapper.insert(addUserInfo);
    }

    @Transactional
    @Override
    public Integer setUpPassword(UserInfo userInfo, String password) {
        Integer updateRows = null;
        if (StringUtils.isBlank(userInfo.getPassword())) {

            UpdateWrapper updateWrapper  = new UpdateWrapper();
            updateWrapper.eq("account",userInfo.getAccount());
            updateWrapper.eq("is_delete", Boolean.FALSE);

            UserInfoEntity updateUserInfo = new UserInfoEntity();
            updateUserInfo.setId(updateUserInfo.getId());
            String newPassword = StarUtils.getSHA256Str(password);
            updateUserInfo.setPassword(newPassword);

            updateRows = userInfoMapper.update(updateUserInfo ,updateWrapper);

            //密码修改记录
            addUserPwdLog(userInfo.getAccount(), newPassword);
        }
        return updateRows;
    }

    @Transactional
    @Override
    public Integer addUserPwdLog(Long userId, String password) {
        UserPwdHistoryLogEntity addLog = new UserPwdHistoryLogEntity();
        addLog.setPassword(password);
        addLog.setUserId(userId);
        addLog.setCreatedAt(new Date());
        addLog.setCreatedBy(userId);
        addLog.setModifiedAt(new Date());
        addLog.setModifiedBy(userId);
        addLog.setIsDelete(Boolean.FALSE);
        return userPwdHistoryLogMapper.insert(addLog);
    }

    @Override
    public UserPwdChangeLogsVO queryPwdLog(Long userId) {
        List<UserPwdHistoryLogEntity> userPwdHistoryLogEntities = userPwdHistoryLogMapper.selectByParamAndLimiTen(userId);

        List<String> passwords = Optional.ofNullable(userPwdHistoryLogEntities)
                .orElse(new ArrayList<>())
                .stream()
                .map(UserPwdHistoryLogEntity::getPassword)
                .collect(Collectors.toList());

        UserPwdChangeLogsVO userPwdChangeLogsVO = new UserPwdChangeLogsVO();
        userPwdChangeLogsVO.setPasswords(passwords);
        userPwdChangeLogsVO.setUserId(userId);
        return userPwdChangeLogsVO;
    }

    @Override
    public Integer changePwd(Long userId, String password) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setPassword(StarUtils.getSHA256Str(password));
        userInfo.setIsDelete(Boolean.FALSE);

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", userId);

        return userInfoMapper.update(userInfo, updateWrapper);
    }

    @Override
    public Integer addLoginLog(Long userId) {
        UserLoginLogEntity userLoginLog = new UserLoginLogEntity();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginStatus(LoginStatus.LOGINED.getCode());
        userLoginLog.setCreatedAt(new Date());
        userLoginLog.setCreatedBy(userId);
        userLoginLog.setModifiedAt(new Date());
        userLoginLog.setModifiedBy(userId);
        userLoginLog.setIsDelete(Boolean.FALSE);
        return userLoginLogMapper.insert(userLoginLog);
    }

    @Override
    public Integer deleteLoginLog(Long userId) {
        UserLoginLogEntity userLoginLog = new UserLoginLogEntity();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginStatus(LoginStatus.NOT_LOGINED.getCode());
        userLoginLog.setCreatedAt(new Date());
        userLoginLog.setCreatedBy(userId);
        userLoginLog.setModifiedAt(new Date());
        userLoginLog.setModifiedBy(userId);
        userLoginLog.setIsDelete(Boolean.FALSE);

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userId);
        updateWrapper.eq("is_delete", Boolean.FALSE);
        return userLoginLogMapper.update(userLoginLog, updateWrapper);
    }


}
