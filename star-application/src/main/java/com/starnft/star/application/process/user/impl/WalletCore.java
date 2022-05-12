package com.starnft.star.application.process.user.impl;

import com.starnft.star.application.process.user.IWalletCore;
import com.starnft.star.application.process.user.req.RechargeFacadeReq;
import com.starnft.star.application.process.user.res.RechargeReqResult;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Service
public class WalletCore implements IWalletCore {

    @Resource
    private WalletService walletService;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public RechargeReqResult recharge(@Valid RechargeFacadeReq rechargeFacadeReq) {

        //参数验证
        verifyParam(rechargeFacadeReq);
        //钱包领域 生成待支付充值订单
        WalletRecordReq walletRecordReq = walletRecordInit(rechargeFacadeReq);
        boolean isSuccess = walletService.rechargeRecordGenerate(walletRecordReq);

        //整合订单信息

        //调用支付领域服务 获取对应支付渠道配置 创建待支付支付单

        // 调用支付

        return null;
    }

    private void verifyParam(RechargeFacadeReq rechargeFacadeReq) {
        String channel = rechargeFacadeReq.getChannel();
        int exist = 0;
        for (StarConstants.PayChannel channelName : StarConstants.PayChannel.values()) {
            if (channelName.name().equals(rechargeFacadeReq.getChannel())) {
                exist = 1;
            }
        }
        if (exist == 1) {
            throw new StarException(StarError.PARAETER_UNSUPPORTED, "渠道代码不存在！");
        }
    }

    private WalletRecordReq walletRecordInit(RechargeFacadeReq rechargeFacadeReq) {
        IIdGenerator iIdGenerator = idGeneratorMap.get(StarConstants.Ids.SnowFlake);
        return WalletRecordReq.builder()
                .recordSn(String.valueOf(iIdGenerator.nextId()))
                .from_uid(0L) // 充值为0
                .to_uid(rechargeFacadeReq.getUserId())
                .payChannel(rechargeFacadeReq.getChannel())
                .tsType(StarConstants.Transaction_Type.Recharge.getFont())
                .tsMoney(rechargeFacadeReq.getMoney())
                .payTime(new Date())
                .payStatus(StarConstants.Pay_Status.WAIT_PAY.name())
                .checkStatus(0)
                .build();
    }
}
