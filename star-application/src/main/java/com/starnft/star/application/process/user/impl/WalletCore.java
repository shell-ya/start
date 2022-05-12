package com.starnft.star.application.process.user.impl;

import com.google.common.collect.Lists;
import com.starnft.star.application.process.user.IWalletCore;
import com.starnft.star.application.process.user.req.PayRecordReq;
import com.starnft.star.application.process.user.req.RechargeFacadeReq;
import com.starnft.star.application.process.user.res.RechargeCallbackRes;
import com.starnft.star.application.process.user.res.RechargeReqResult;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.wallet.model.req.TransactionRecordQueryReq;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.service.WalletService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

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

    @Override
    public ResponsePageResult<RechargeCallbackRes> walletRecordQuery(@Valid PayRecordReq recordReq) {
        ResponsePageResult<WalletRecordVO> walletRecordResult = walletService.queryTransactionRecord(TransactionRecordQueryReq.builder()
                .page(recordReq.getPage()).size(recordReq.getSize())
                .userId(recordReq.getUserId())
                .startDate(recordReq.getStartTime())
                .endDate(recordReq.getEndTime())
                .transactionType(recordReq.getPayType()).build());

        List<RechargeCallbackRes> res = Lists.newArrayList();
        for (WalletRecordVO walletRecordVO : walletRecordResult.getList()) {
            RechargeCallbackRes rechargeCallbackRes = recordVOConvert(walletRecordVO);
            res.add(rechargeCallbackRes);
        }
        return ResponsePageResult.listReplace(walletRecordResult, res);
    }


    /**
     * @param walletRecordVO
     * @return RechargeCallbackRes
     * @author Ryan Z / haoran
     * @description vo转化
     * @date 2022/5/12
     */
    private RechargeCallbackRes recordVOConvert(WalletRecordVO walletRecordVO) {
        return RechargeCallbackRes.builder()
                .channel(walletRecordVO.getPayChannel())
                .money(walletRecordVO.getTsMoney())
                .orderSn(walletRecordVO.getRecordSn())//todo
                .outTradeNo("TODO")
                .status(walletRecordVO.getPayStatus())
                .payTime(walletRecordVO.getPayTime().toString())
                .patType(walletRecordVO.getTsType())
                .transactionSn(walletRecordVO.getRecordSn()).build();
    }

    /**
     * @param rechargeFacadeReq
     * @author Ryan Z / haoran
     * @description 参数验证
     * @date 2022/5/12
     */
    private void verifyParam(RechargeFacadeReq rechargeFacadeReq) {
        String channel = rechargeFacadeReq.getChannel();
        int exist = 0;
        for (StarConstants.PayChannel channelName : StarConstants.PayChannel.values()) {
            if (channelName.name().equals(rechargeFacadeReq.getChannel())) {
                exist = 1;
                break;
            }
        }
        if (exist == 0) {
            throw new StarException(StarError.PARAETER_UNSUPPORTED, "渠道代码不存在！");
        }
    }


    /**
     * @param rechargeFacadeReq
     * @return WalletRecordReq
     * @author Ryan Z / haoran
     * @description 参数初始化
     * @date 2022/5/12
     */
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
