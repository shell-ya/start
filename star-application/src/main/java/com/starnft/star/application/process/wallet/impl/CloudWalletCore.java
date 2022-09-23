package com.starnft.star.application.process.wallet.impl;

import com.starnft.star.application.process.wallet.ICloudWalletCore;
import com.starnft.star.application.process.wallet.req.OpenCloudAccountReq;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.payment.handler.ISandPayCloudPayHandler;
import com.starnft.star.domain.payment.model.req.CloudAccountBalanceReq;
import com.starnft.star.domain.payment.model.req.CloudAccountOPenReq;
import com.starnft.star.domain.payment.model.req.CloudAccountStatusReq;
import com.starnft.star.domain.payment.model.res.CloudAccountBalanceRes;
import com.starnft.star.domain.payment.model.res.CloudAccountOPenRes;
import com.starnft.star.domain.payment.model.res.CloudAccountStatusRes;
import com.starnft.star.domain.user.model.vo.UserRealInfo;
import com.starnft.star.domain.user.service.IUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CloudWalletCore implements ICloudWalletCore {
    @Resource
    ISandPayCloudPayHandler iSandPayCloudPayHandler;
    @Resource
    IUserService userService;
    @Resource
    RedisTemplate redisTemplate;
    @Override
    public CloudAccountStatusRes cloudWalletStatus(Long userId) {
        Boolean isOPen = redisTemplate.opsForValue().getBit(RedisKey.CLOUD_ACCOUNT_STATUS_LIST.getKey(), userId);
       if (!isOPen){
           CloudAccountStatusReq cloudAccountStatusReq = new CloudAccountStatusReq();
           UserRealInfo userRealInfo = userService.getUserRealInfo(userId);
           Assert.notNull(userRealInfo,()->new StarException("查无此用户"));
           Assert.notBlank(userRealInfo.getFullName(),()->new StarException("请先实名认证"));
           Assert.notBlank(userRealInfo.getIdNumber(),()->new StarException("请先实名认证"));
           cloudAccountStatusReq.setUserId(userId.toString());
           cloudAccountStatusReq.setRealName(userRealInfo.getFullName());
           cloudAccountStatusReq.setIdCard(userRealInfo.getIdNumber());
           CloudAccountStatusRes cloudAccountStatusRes = iSandPayCloudPayHandler.accountStatus(cloudAccountStatusReq);
          if (cloudAccountStatusRes.getStatus()){
              redisTemplate.opsForValue().setBit(RedisKey.CLOUD_ACCOUNT_STATUS_LIST.getKey(), userId,cloudAccountStatusRes.getStatus());
          }
           return cloudAccountStatusRes;
       }else {
           CloudAccountStatusRes cloudAccountStatusRes = new CloudAccountStatusRes();
           cloudAccountStatusRes.setStatus(isOPen);
           return cloudAccountStatusRes;
       }

    }

    @Override
    public CloudAccountBalanceRes cloudWalletBalance(Long userId) {
        CloudAccountBalanceReq cloudAccountBalanceReq = new CloudAccountBalanceReq();
        cloudAccountBalanceReq.setUserId(userId.toString());
        CloudAccountBalanceRes cloudAccountBalanceRes = iSandPayCloudPayHandler.queryBalance(cloudAccountBalanceReq);
        return cloudAccountBalanceRes;
    }

    @Override
    public CloudAccountOPenRes openCloudWallet(Long userId, OpenCloudAccountReq openCloudAccountReq) {
        UserRealInfo userRealInfo = userService.getUserRealInfo(userId);
        Assert.notNull(userRealInfo,()->new StarException("查无此用户"));
        Assert.notBlank(userRealInfo.getFullName(),()->new StarException("请先实名认证"));
        Assert.notBlank(userRealInfo.getIdNumber(),()->new StarException("请先实名认证"));
        CloudAccountOPenReq cloudAccountOPenReq = new CloudAccountOPenReq();
        cloudAccountOPenReq.setUserId(userId.toString());
        cloudAccountOPenReq.setReturnUri(openCloudAccountReq.getReturnUri());
        cloudAccountOPenReq.setRealName(userRealInfo.getFullName());
        cloudAccountOPenReq.setIdCard(userRealInfo.getIdNumber());
        CloudAccountOPenRes cloudAccountOPenRes = iSandPayCloudPayHandler.openAccount(cloudAccountOPenReq);
        return cloudAccountOPenRes;
    }
}
