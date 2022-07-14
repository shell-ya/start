package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftOrder;
import com.starnft.star.business.domain.StarNftWalletConfig;
import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.StarNftWithdrawApply;
import com.starnft.star.business.domain.vo.FrontVo;
import com.starnft.star.business.mapper.*;
import com.starnft.star.business.service.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Date 2022/6/10 1:18 AM
 * @Author ： shellya
 */
@Service
public class MainServiceImpl implements IMainService {

    @Autowired
    private AccountUserMapper accountUserMapper;
    @Autowired
    private StarNftOrderMapper starNftOrderMapper;
    @Autowired
    private StarNftWithdrawApplyMapper starNftWithdrawApplyMapper;
    @Autowired
    private StarNftWalletRecordMapper starNftWalletRecordMapper;
    @Autowired
    private StarNftWalletConfigMapper starNftWalletConfigMapper;

    @Override
    public FrontVo getFront() {
        StarNftWalletConfig starNftWalletConfig = starNftWalletConfigMapper.selectStarNftWalletConfigByChannel("BankCard");

        //当日提现订单
        List<StarNftWithdrawApply> dayWithDrawApply = starNftWithdrawApplyMapper.dayWithDrawApply();
        //昨日提现订单
        List<StarNftWithdrawApply> toDayWithDrawApply = starNftWithdrawApplyMapper.toDayWithDrawApply();
        //当日提现总金额
        BigDecimal dayTotalWithdrawMoney = dayWithDrawApply.stream().map(StarNftWithdrawApply::getWithdrawMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        //昨日提现总金额
        BigDecimal toDayTotalWithdrawMoney = toDayWithDrawApply.stream().map(StarNftWithdrawApply::getWithdrawMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        //当日提现手续费
        BigDecimal dayTotalWithDrawRate = dayWithDrawApply.stream().map(apply -> {
            return apply.getWithdrawMoney().multiply(starNftWalletConfig.getChargeRate());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        dayTotalWithDrawRate = dayTotalWithDrawRate.add(starNftWalletConfig.getStableRate().multiply(new BigDecimal(dayWithDrawApply.size())));
        //昨日提现手续费
        BigDecimal toDayTotalWithDrawRate = dayWithDrawApply.stream().map(apply -> {
            return apply.getWithdrawMoney().multiply(starNftWalletConfig.getChargeRate());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        toDayTotalWithDrawRate = toDayTotalWithDrawRate.add(starNftWalletConfig.getStableRate().multiply(new BigDecimal(dayWithDrawApply.size())));

        //当日充值交易
        List<StarNftWalletRecord> dayPayRecord = starNftWalletRecordMapper.dayWalletRecord(1);
        //昨日充值交易
        List<StarNftWalletRecord> toDayPayRecord = starNftWalletRecordMapper.toDayWalletRecord(1);
        //当日充值总金额
        BigDecimal dayTotalPayMoney = dayPayRecord.stream().map(StarNftWalletRecord::getTsMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        //昨天充值总金额
        BigDecimal toDayTotalPayMoney = toDayPayRecord.stream().map(StarNftWalletRecord::getTsMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        //当日交易
        List<StarNftOrder> dayOrder = starNftOrderMapper.dayOrder();
        //昨日交易
        List<StarNftOrder> toDayOrder = starNftOrderMapper.toDayOrder();
        //当日交易金额
        BigDecimal dayTotalOrderMoney = dayOrder.stream().map(StarNftOrder::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        //昨日交易金额
        BigDecimal toDayTotalOrderMoney = toDayOrder.stream().map(StarNftOrder::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);


        //当日交易手续费
        BigDecimal dayTotalOrderRate = dayOrder.stream().map(order -> {
            return order.getTotalAmount().multiply(starNftWalletConfig.getServiceRate());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        dayTotalOrderRate = dayTotalOrderRate.add(starNftWalletConfig.getStableRate().multiply(new BigDecimal(dayOrder.size())));
        //昨日交易手续费
        BigDecimal toDayTotalOrderRate = toDayOrder.stream().map(order -> {
            return order.getTotalAmount().multiply(starNftWalletConfig.getServiceRate());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        toDayTotalOrderRate = toDayTotalOrderRate.add(starNftWalletConfig.getStableRate().multiply(new BigDecimal(toDayOrder.size())));

        return FrontVo.builder()
                .dayWithDrawApplyTotal(dayWithDrawApply.size())
                .toDayWithDrawApplyTotal(toDayWithDrawApply.size())
                .dayTotalWithdrawMoney(dayTotalWithdrawMoney)
                .toDayTotalWithdrawMoney(toDayTotalWithdrawMoney)
                .dayTotalWithDrawRate(dayTotalWithDrawRate)
                .toDayTotalWithDrawRate(toDayTotalWithDrawRate)
                .dayPayRecordTotal(dayPayRecord.size())
                .toDayPayRecordTotal(toDayPayRecord.size())
                .dayTotalPayMoney(dayTotalPayMoney)
                .toDayTotalPayMoney(toDayTotalPayMoney)
                .dayOrderTotal(dayOrder.size())
                .toDayOrderTotal(toDayOrder.size())
                .dayTotalOrderMoney(dayTotalOrderMoney)
                .toDayTotalOrderMoney(toDayTotalOrderMoney)
                .dayTotalOrderRate(dayTotalOrderRate)
                .toDayTotalOrderRate(toDayTotalOrderRate)
                .build();


    }
}
