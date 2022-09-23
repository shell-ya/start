package com.starnft.star.domain.payment.handler;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.model.req.CloudAccountBalanceReq;
import com.starnft.star.domain.payment.model.req.CloudAccountOPenReq;
import com.starnft.star.domain.payment.model.req.CloudAccountStatusReq;
import com.starnft.star.domain.payment.model.res.CloudAccountBalanceRes;
import com.starnft.star.domain.payment.model.res.CloudAccountOPenRes;
import com.starnft.star.domain.payment.model.res.CloudAccountStatusRes;

import java.util.Map;

public interface ISandPayCloudPayHandler {
    public CloudAccountStatusRes  accountStatus(CloudAccountStatusReq cloudAccountStatusReq);

    public CloudAccountBalanceRes queryBalance(CloudAccountBalanceReq cloudAccountBalanceReq);

    public CloudAccountOPenRes openAccount(CloudAccountOPenReq cloudAccountOPenReq);

}
