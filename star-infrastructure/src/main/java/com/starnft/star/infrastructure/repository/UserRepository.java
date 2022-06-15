package com.starnft.star.infrastructure.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.starnft.star.common.enums.LoginStatus;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.common.utils.StarUtils;
import com.starnft.star.domain.user.model.dto.AgreementSignDTO;
import com.starnft.star.domain.user.model.dto.UserInfoAddDTO;
import com.starnft.star.domain.user.model.dto.UserInfoUpdateDTO;
import com.starnft.star.domain.user.model.vo.*;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.infrastructure.entity.user.*;
import com.starnft.star.infrastructure.mapper.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserRepository implements IUserRepository {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserPwdHistoryLogMapper userPwdHistoryLogMapper;
    @Resource
    private UserLoginLogMapper userLoginLogMapper;
    @Resource
    private AccountUserMapper accountUserMapper;
    @Resource
    private UserAgreementMapper userAgreementMapper;
    @Resource
    private UserDataAuthorizationAgreementSignMapper signMapper;
    @Resource
    private UserAgreementPopupMapper userAgreementPopupMapper;
    @Resource
    private UserDataAuthorizationMapper userDataAuthorizationMapper;


    @Override
    public UserInfo queryUserInfoByPhone(String phone) {
        UserInfoEntity queryUser = new UserInfoEntity();
        queryUser.setIsDeleted(Boolean.FALSE);
        queryUser.setPhone(phone);
        UserInfoEntity userInfoEntity = this.userInfoMapper.selectOne(new QueryWrapper<UserInfoEntity>().setEntity(queryUser));
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
    public UserInfo queryUserInfoByUserId(Long userId) {
        UserInfoEntity userInfoEntity = this.getUserInfoEntity(userId);
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
            userInfo.setRealPersonFlag(userInfoEntity.getRealPersonFlag());
            userInfo.setBlockchainAddress(userInfoEntity.getBlockchainAddress());
            userInfo.setBriefIntroduction(userInfoEntity.getBriefIntroduction());
            return userInfo;
        }
        return null;
    }

    private UserInfoEntity getUserInfoEntity(Long userId) {
        UserInfoEntity queryUser = new UserInfoEntity();
        queryUser.setIsDeleted(Boolean.FALSE);
        queryUser.setAccount(userId);
        return this.userInfoMapper.selectOne(new QueryWrapper<UserInfoEntity>().setEntity(queryUser));
    }


    @Override
    public Long addUserInfo(UserInfoAddDTO req) {
        UserInfoEntity userInfo = createUserInfo(req);
        if (this.userInfoMapper.insert(userInfo) == 1) {
            return userInfo.getAccount();
        }
        throw new StarException(StarError.USER_CREATION_ERROR);
    }

    private UserInfoEntity createUserInfo(UserInfoAddDTO req) {
        UserInfoEntity addUserInfo = new UserInfoEntity();
        Long userId = RandomUtil.randomLong(9);
        UserInfo userInfo = queryUserInfoByUserId(userId);
        if (null == userInfo) {
            addUserInfo.setCreatedAt(new Date());
            addUserInfo.setCreatedBy(userId);
            addUserInfo.setModifiedAt(new Date());
            addUserInfo.setModifiedBy(userId);
            addUserInfo.setIsDeleted(Boolean.FALSE);
            addUserInfo.setIsActive(Boolean.FALSE);
            addUserInfo.setAccount(userId);
            addUserInfo.setNickName(req.getNickName());
            addUserInfo.setPhone(req.getPhone());
//            addUserInfo.s
            return addUserInfo;
        }
        return createUserInfo(req);
    }

    @Transactional
    @Override
    public Integer setUpPassword(UserInfo userInfo, String password) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", userInfo.getAccount());
        updateWrapper.eq("is_deleted", Boolean.FALSE);

        UserInfoEntity updateUserInfo = new UserInfoEntity();
        updateUserInfo.setId(updateUserInfo.getId());
        String newPassword = StarUtils.getSHA256Str(password);
        updateUserInfo.setPassword(newPassword);
        updateUserInfo.setModifiedAt(new Date());
        updateUserInfo.setModifiedBy(userInfo.getAccount());

        Integer updateRows = this.userInfoMapper.update(updateUserInfo, updateWrapper);
        //密码修改记录
        this.addUserPwdLog(userInfo.getAccount(), newPassword);
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
        addLog.setIsDeleted(Boolean.FALSE);
        return this.userPwdHistoryLogMapper.insert(addLog);
    }

    @Override
    public UserPwdChangeLogsVO queryPwdLog(Long userId) {
        List<UserPwdHistoryLogEntity> userPwdHistoryLogEntities = this.userPwdHistoryLogMapper.selectByParamAndLimiTen(userId);

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
        userInfo.setIsDeleted(Boolean.FALSE);
        userInfo.setModifiedBy(userId);
        userInfo.setModifiedAt(new Date());

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", userId);
        updateWrapper.eq("is_deleted", Boolean.FALSE);
        return this.userInfoMapper.update(userInfo, updateWrapper);
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
        userLoginLog.setIsDeleted(Boolean.FALSE);
        return this.userLoginLogMapper.insert(userLoginLog);
    }

    @Override
    public Integer deleteLoginLog(Long userId) {
        UserLoginLogEntity userLoginLog = new UserLoginLogEntity();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginStatus(LoginStatus.NOT_LOGINED.getCode());
        userLoginLog.setModifiedAt(new Date());
        userLoginLog.setModifiedBy(userId);

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userId);
        updateWrapper.eq("is_delete", Boolean.FALSE);
        return this.userLoginLogMapper.update(userLoginLog, updateWrapper);
    }

    @Override
    public Boolean changePayPwd(Long userId, String payPassword) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", userId);
        updateWrapper.eq("is_deleted", Boolean.FALSE);

        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setPlyPassword(StarUtils.getSHA256Str(payPassword));
        userInfo.setIsDeleted(Boolean.FALSE);
        userInfo.setModifiedBy(userId);
        userInfo.setModifiedAt(new Date());

        int row = this.userInfoMapper.update(userInfo, updateWrapper);
        return row > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Integer updateUserInfo(UserInfoUpdateDTO req) {
        UserInfoEntity userInfo = BeanColverUtil.colver(req, UserInfoEntity.class);
        userInfo.setModifiedAt(new Date());
        userInfo.setModifiedBy(req.getAccount());

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("account", req.getAccount());
        return this.userInfoMapper.update(userInfo, updateWrapper);
    }

    @Override
    public AgreementVO queryAgreementInfoByType(Integer agreementType) {
        UserAgreementEntity userAgreementEntity = this.userAgreementMapper.queryAgreementInfoByType(agreementType);
        return BeanColverUtil.colver(userAgreementEntity, AgreementVO.class);
    }

    @Override
    public List<String> queryUserSignAgreement(Long userId) {
        return this.signMapper.batchSelectUserAgreementId(userId);
    }

    @Override
    public List<AgreementVO> queryNewAgreementByScene(Integer scene) {
        List<UserAgreementEntity> userAgreementEntity = this.userAgreementMapper.queryNewAgreementByScene(scene);
        return BeanColverUtil.colverList(userAgreementEntity, AgreementVO.class);
    }

    @Override
    public AgreementPopupInfoVO queryAgreementPopupByScene(Integer scene) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("agreement_popup_scene", scene);
        wrapper.eq("is_deleted", Boolean.FALSE);
        wrapper.le("effective_time", LocalDateTime.now());
        wrapper.last("limit 1");
        UserAgreementPopupEntity userAgreementPopupEntity = this.userAgreementPopupMapper.selectOne(wrapper);
        return BeanColverUtil.colver(userAgreementPopupEntity, AgreementPopupInfoVO.class);
    }

    @Override
    public List<AgreementVO> queryAgreementByAgreementId(List<String> agreementIdList) {
        List<UserAgreementEntity> userAgreementEntities = this.userAgreementMapper.batchQueryAgreementById(agreementIdList);
        return BeanColverUtil.colverList(userAgreementEntities, AgreementVO.class);
    }

    @Override
    public Integer addAuthorizationId(Long userId, Long authorizationId) {
        UserDataAuthorizationEntity userDataAuthorizationEntity = new UserDataAuthorizationEntity();
        //协议无授权结束时间
        userDataAuthorizationEntity.setAuthorizationEndTime(LocalDateTime.now().plusYears(200L));
        userDataAuthorizationEntity.setAuthorizationStartTime(LocalDateTime.now());
        userDataAuthorizationEntity.setAuthorizationId(authorizationId);
        userDataAuthorizationEntity.setCreatedAt(new Date());
        userDataAuthorizationEntity.setCreatedBy(userId);
        userDataAuthorizationEntity.setModifiedAt(new Date());
        userDataAuthorizationEntity.setModifiedBy(userId);
        userDataAuthorizationEntity.setIsDeleted(Boolean.FALSE);
        return this.userDataAuthorizationMapper.insert(userDataAuthorizationEntity);
    }

    @Override
    public Integer batchInsertAgreementSign(List<AgreementSignDTO> list) {
        return this.signMapper.batchInsertAgreementSign(list);
    }

    @Override
    public UserRealInfo getUserInfoAll(Long userId) {
        UserInfoEntity queryUser = new UserInfoEntity();
        queryUser.setIsDeleted(Boolean.FALSE);
        queryUser.setAccount(userId);
        UserInfoEntity userInfoEntity = this.userInfoMapper.selectOne(new QueryWrapper<UserInfoEntity>().setEntity(queryUser));
        return BeanColverUtil.colver(userInfoEntity, UserRealInfo.class);
    }

    @Override
    public Boolean doesUserExist(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", userId);
        queryWrapper.eq("is_deleted", Boolean.FALSE);
        UserInfoEntity userInfoEntity = this.userInfoMapper.selectOne(queryWrapper);
        return Objects.nonNull(userInfoEntity);
    }
}
