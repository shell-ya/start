package com.starnft.star.infrastructure.repository.user;

import cn.hutool.crypto.digest.MD5;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.component.AuthTokenComponent;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.po.AccessToken;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.model.dto.UserLoginDTO;
import com.starnft.star.domain.model.dto.UserRegisterDTO;
import com.starnft.star.domain.model.vo.UserInfoVO;
import com.starnft.star.domain.model.vo.UserRegisterInfoVO;
import com.starnft.star.domain.user.repository.IUserRepository;
import com.starnft.star.infrastructure.entity.user.UserInfoEntity;
import com.starnft.star.infrastructure.mapper.UserInfoMapper;
import com.starnft.star.infrastructure.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author WeiChunLAI
 */
@Slf4j
@Service
public class UserRepository implements IUserRepository {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private AuthTokenComponent authTokenComponent;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserInfoVO login(UserLoginDTO req) {

        //校验用户是否存在
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setIsDeleted(Boolean.FALSE);
        userInfo.setPhone(req.getPhone());
        UserInfoEntity userInfoEntity = userInfoMapper.selectOne(userInfo);
        if (Objects.isNull(userInfoEntity)) {
            log.warn("用户不存在");
            throw new StarException(StarError.USER_NOT_EXISTS, "用户不存在，请先注册");
        }

        //校验密码是否正确
        String retryPwdKey = String.format(RedisKey.RETRY_PWD.getKey(), req.getPhone());
        Integer errorCount = (Integer) redisUtil.get(retryPwdKey);
        if (errorCount <= StarConstants.RETRY_COUNT) {
            userInfo.setPassword(MD5.create().digestHex(req.getPassword()));
            userInfoEntity = userInfoMapper.selectOne(userInfo);
            if (Objects.isNull(userInfoEntity)) {
                log.warn("密码错误");
                errorCount++;
                if (StarConstants.RETRY_COUNT.equals(errorCount)) {
                    log.warn("账号已被冻结:{}", req.getPhone());
                    redisUtil.set(retryPwdKey, errorCount);
                    redisUtil.expire(retryPwdKey, 600);
                } else {
                    redisUtil.set(retryPwdKey, errorCount);
                }
                throw new StarException(StarError.USER_NOT_EXISTS, "密码错误，请重新输入，剩余 " + (5 - errorCount) + "次机会");
            }
        } else {
            long expire = redisUtil.getExpire(retryPwdKey) / 60;
            throw new StarException(StarError.USER_NOT_EXISTS, "账号已被冻结，请 " + expire + "分钟再重试");
        }

        //todo 禁用校验

        //登录成功后删除redis记录的密码错误次数
        redisUtil.delByKey(retryPwdKey);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAccount(userInfoEntity.getAccount());
        userInfoVO.setAvatar(userInfoEntity.getAvatar());
        userInfoVO.setPhone(userInfoEntity.getPhone());
        userInfoVO.setNickName(userInfoEntity.getNickName());

        //创建token
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userInfoVO.getAccount());
        userInfoVO.setToken(authTokenComponent.createAccessToken(accessToken));

        return userInfoVO;
    }

    @Override
    public UserRegisterInfoVO register(UserRegisterDTO req) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setIsDeleted(Boolean.FALSE);
        userInfoEntity.setPhone(req.getPhone());

        //校验是否新用户
        if (Objects.nonNull(userInfoMapper.selectOne(userInfoEntity))) {
            log.warn("该用户已注册账号:{}", req.getPhone());
            throw new StarException(StarError.USER_EXISTS, "该手机号已注册用户，请直接登录!");
        }

        //校验短信验证码是否正确
        String smsCodeKey = String.format(RedisKey.SMS_CODE.getKey(), req.getPhone());
        String smsCode = (String) redisUtil.get(smsCodeKey);
        if (StringUtils.isBlank(smsCode) || !req.getVerificationCode().equals(smsCode)) {
            log.warn("验证码已过期");
            throw new StarException(StarError.VERIFICATION_CODE_ERROR, "验证码已过期或输入错误，请重试!");
        }

        //todo 分布式锁

        //新增用户
        UserInfoEntity registerUser = new UserInfoEntity();
        registerUser.setPhone(req.getPhone());
        registerUser.setAccount(SnowflakeWorker.generateId());
        registerUser.setNickName(req.getNickName());
        registerUser.setPassword(MD5.create().digestHex(req.getPassword()));
        registerUser.setIsDeleted(Boolean.FALSE);
        registerUser.setCreatedAt(new Date());
        registerUser.setCreatedBy(registerUser.getAccount());
        registerUser.setModifiedAt(new Date());
        registerUser.setModifiedBy(registerUser.getAccount());
        int insert = userInfoMapper.insert(registerUser);
        if (insert == 1) {
            UserRegisterInfoVO userRegisterInfoVO = new UserRegisterInfoVO();
            userInfoEntity.setAccount(registerUser.getAccount());
            return userRegisterInfoVO;
        }
        throw new StarException(StarError.SYSTEM_ERROR, "注册失败，请稍后重试!");
    }
}
