package com.starnft.star.application.process.user.impl;

import com.starnft.star.application.process.user.UserTotalInfoCompose;
import com.starnft.star.application.process.user.req.UserGatheringInfoReq;
import com.starnft.star.application.process.user.res.UserGatheringInfoRes;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

@Service
public class UserTotalInfoComposeImpl implements UserTotalInfoCompose {

    @Resource
    private WalletService walletService;
    @Resource
    private IUserService userService;

    @Override
    public UserGatheringInfoRes ObtainUserGatheringInfo(@Validated UserGatheringInfoReq req) {

        UserGatheringInfoRes userGatheringInfoRes = new UserGatheringInfoRes();

        //获取User信息
        UserInfoVO userInfoVO = userService.queryUserInfo(req.getUid());
        userInfoVO.setUserId(req.getUid());
        populateUserInfo(userGatheringInfoRes, userInfoVO);

        //获取钱包信息
        WalletResult walletResult = walletService.queryWalletInfo(new WalletInfoReq(req.getUid()));
        populateWalletInfo(userGatheringInfoRes, walletResult);

        return userGatheringInfoRes;
    }

    private void populateWalletInfo(UserGatheringInfoRes userGatheringInfoRes, WalletResult walletResult) {
        userGatheringInfoRes.setBalance(walletResult.getBalance());
        userGatheringInfoRes.setWalletId(walletResult.getWalletId());
        userGatheringInfoRes.setFrozen(walletResult.isFrozen());
        userGatheringInfoRes.setFrozen_fee(walletResult.getFrozen_fee());
        userGatheringInfoRes.setWallet_income(walletResult.getWallet_income());
        userGatheringInfoRes.setWallet_outcome(walletResult.getWallet_outcome());
    }

    private void populateUserInfo(UserGatheringInfoRes userGatheringInfoRes, UserInfoVO userInfoVO) {
        userGatheringInfoRes.setNickName(userInfoVO.getNickName());
        userGatheringInfoRes.setUid(userInfoVO.getUserId());
        userGatheringInfoRes.setPhone(userInfoVO.getPhone());
        userGatheringInfoRes.setAvatar(userInfoVO.getAvatar());
    }
}
