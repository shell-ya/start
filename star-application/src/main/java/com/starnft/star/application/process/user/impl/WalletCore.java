package com.starnft.star.application.process.user.impl;

import com.google.common.collect.Lists;
import com.starnft.star.application.process.user.IWalletCore;
import com.starnft.star.application.process.user.req.PayRecordReq;
import com.starnft.star.application.process.user.req.RechargeFacadeReq;
import com.starnft.star.application.process.user.res.RechargeReqResult;
import com.starnft.star.application.process.user.res.TransactionRecord;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.wallet.model.req.TransactionRecordQueryReq;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
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

    @Override
    public ResponsePageResult<TransactionRecord> walletRecordQuery(@Valid PayRecordReq recordReq) {
        ResponsePageResult<WalletRecordVO> walletRecordResult = walletService
                .queryTransactionRecord(TransactionRecordQueryReq.builder()
                        .page(recordReq.getPage()).size(recordReq.getSize())
                        .userId(recordReq.getUserId())
                        .startDate(recordReq.getStartTime())
                        .endDate(recordReq.getEndTime())
                        .transactionType(recordReq.getPayType()).build());

        List<TransactionRecord> res = Lists.newArrayList();
        for (WalletRecordVO walletRecordVO : walletRecordResult.getList()) {
            TransactionRecord transactionRecord = recordVOConvert(walletRecordVO, recordReq.getUserId());
            res.add(transactionRecord);
        }
        return ResponsePageResult.listReplace(walletRecordResult, res);
    }


    /**
     * @param walletRecordVO
     * @param userId
     * @return TransactionRecord
     * @author Ryan Z / haoran
     * @description vo转化
     * @date 2022/5/12
     */
    private TransactionRecord recordVOConvert(WalletRecordVO walletRecordVO, Long userId) {
        return TransactionRecord.builder()
                .userId(userId)
                .channel(walletRecordVO.getPayChannel())
                .money(walletRecordVO.getTsMoney())
                .orderSn(walletRecordVO.getRecordSn())//todo
                .outTradeNo("TODO")
                .status(walletRecordVO.getPayStatus())
                .payTime(walletRecordVO.getPayTime())
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
        boolean exist = true;
        for (StarConstants.PayChannel channelName : StarConstants.PayChannel.values()) {
            if (channelName.name().equals(rechargeFacadeReq.getChannel())) {
                exist = false;
                break;
            }
        }
        if (exist) {
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
