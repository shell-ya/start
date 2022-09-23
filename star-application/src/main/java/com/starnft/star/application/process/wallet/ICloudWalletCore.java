package com.starnft.star.application.process.wallet;

import com.starnft.star.application.process.wallet.req.OpenCloudAccountReq;
import com.starnft.star.domain.payment.model.res.CloudAccountBalanceRes;
import com.starnft.star.domain.payment.model.res.CloudAccountOPenRes;
import com.starnft.star.domain.payment.model.res.CloudAccountStatusRes;

public interface ICloudWalletCore {
    public CloudAccountStatusRes cloudWalletStatus(Long userId);

    CloudAccountBalanceRes cloudWalletBalance(Long userId);

    CloudAccountOPenRes openCloudWallet(Long userId, OpenCloudAccountReq openCloudAccountReq);
}
