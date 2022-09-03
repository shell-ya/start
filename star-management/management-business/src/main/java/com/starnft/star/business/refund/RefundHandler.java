package com.starnft.star.business.refund;

import com.starnft.star.business.domain.StarNftOrder;
import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.dto.WalletRecordReq;
import com.starnft.star.business.mapper.StarNftOrderMapper;
import com.starnft.star.business.mapper.StarNftWalletRecordMapper;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.SnowflakeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Date 2022/9/2 8:37 PM
 * @Author ： shellya
 */
@Slf4j
@Component("refundHandler")
public abstract class RefundHandler {

    @Resource
    private StarNftOrderMapper orderMapper;
    @Resource
    private StarNftWalletRecordMapper recordMapper;

    public abstract boolean refundOrder(String orderSn);

    public Boolean refundPayUser(String orderSn){
        //查询订单交易金额 返回付款账户 增加钱包余额  并生成退款记录 walletRecord walletLog
        StarNftWalletRecord record = queryOrder(orderSn);
        //生成退款记录
        WalletRecordReq walletRecordReq = this.walletRecordInit(record);

        //生成变化记录
        return true;
    }

    private StarNftWalletRecord queryOrder(String recordSn){
        List<StarNftWalletRecord> starNftWalletRecords = recordMapper.queryStarNftWalletRecord(recordSn);

        if (starNftWalletRecords.size() > 1) throw new StarException(StarError.SYSTEM_ERROR);

        return starNftWalletRecords.get(0);
    }

    private WalletRecordReq walletRecordInit(StarNftWalletRecord record) {

        return WalletRecordReq.builder()
                .recordSn(StarConstants.OrderPrefix.RefundSN.getPrefix().concat(String.valueOf(SnowflakeWorker.generateId())))
                .from_uid(record.getToUid()) // 充值为0
                .to_uid(record.getFromUid())
                .payChannel(StarConstants.PayChannel.Balance.name())
                .tsType(StarConstants.Transaction_Type.Refund.getCode())
                .tsMoney(record.getTsMoney())
                .tsCost(record.getTsCost())
                .tsFee(BigDecimal.ZERO)
                .payTime(new Date())
                .payStatus(StarConstants.Pay_Status.PAY_SUCCESS.name())
                .build();
    }
}
