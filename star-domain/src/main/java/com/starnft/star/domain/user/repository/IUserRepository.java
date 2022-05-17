package com.starnft.star.domain.user.repository;


import com.starnft.star.domain.user.model.dto.AgreementSignDTO;
import com.starnft.star.domain.user.model.dto.UserInfoAddDTO;
import com.starnft.star.domain.user.model.dto.UserInfoUpdateDTO;
import com.starnft.star.domain.user.model.vo.*;

import java.util.List;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 10:50
 */
public interface IUserRepository {

    /**
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    UserInfo queryUserInfoByPhone(String phone);

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    UserInfo queryUserInfoByUserId(Long userId);

    /**
     * 新增用户
     * @param req
     * @return
     */
    Integer addUserInfo(UserInfoAddDTO req);

    /**
     * 查询用户是否存在
     * @param userId
     * @return
     */
    Boolean doesUserExist(Long userId);

    /**
     * 设置初始密码
     * @param userInfo
     * @param password
     * @return
     */
    Integer setUpPassword(UserInfo userInfo , String password);

    /**
     * 添加修改密码记录
     * @param userId
     * @param password
     * @return
     */
    Integer addUserPwdLog(Long userId, String password);

    /**
     * 查询用户历史密码 10条
     * @param userId
     * @return
     */
    UserPwdChangeLogsVO queryPwdLog(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param password
     * @return
     */
    Integer changePwd(Long userId , String password);

    /**
     * 添加登录日志
     * @param userId
     * @return
     */
    Integer addLoginLog(Long userId);

    /**
     * 清除登录日志
     * @param userId
     * @return
     */
    Integer deleteLoginLog(Long userId);

    /**
     * 修改支付密码
     * @param userId
     * @param payPassword
     * @return
     */
    Boolean changePayPwd(Long userId , String payPassword);

    /**
     * 修改用户信息
     * @param req
     * @return
     */
    Integer updateUserInfo(UserInfoUpdateDTO req);

    /**
     * 根据协议类型查询最新协议
     * @param agreementType
     * @return
     */
    AgreementVO queryAgreementInfoByType(Integer agreementType);

    /**
     * 查询用户签署的协议id
     * @param userId
     * @return
     */
    List<String> queryUserSignAgreement(Long userId);

    /**
     * 根据场景查询最新协议
     * @param scene
     * @return
     */
    List<AgreementVO> queryNewAgreementByScene(Integer scene);

    /**
     * 根据协议场景查询协议弹窗信息
     * @param scene
     * @return
     */
    AgreementPopupInfoVO queryAgreementPopupByScene(Integer scene);

    /**
     * 根据协议id查询协议信息
     * @param agreementIdList
     * @return
     */
    List<AgreementVO> queryAgreementByAgreementId(List<String> agreementIdList);

    /**
     * 新增授权id
     * @param userId
     * @param authorizationId
     * @return
     */
    Integer addAuthorizationId(Long userId,Long authorizationId);

    /**
     * 批量插入协议签署信息
     * @param list
     * @return
     */
    Integer batchInsertAgreementSign(List<AgreementSignDTO> list);
}
