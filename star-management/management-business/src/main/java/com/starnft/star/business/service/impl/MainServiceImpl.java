package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftWalletConfig;
import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.StarNftWithdrawApply;
import com.starnft.star.business.domain.vo.FrontVo;
import com.starnft.star.business.mapper.*;
import com.starnft.star.business.service.IMainService;
import com.starnft.star.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        List<Integer> dayUserCountList = accountUserMapper.getDayUserCount();
        List<Integer> toDayUserCountList = accountUserMapper.getToDayUserCount();
        AtomicReference<Integer> dayUserCount = new AtomicReference<>(0);
        dayUserCountList.stream().reduce(Integer::sum).ifPresent(dayUserCount::set);
        AtomicReference<Integer> toDayUserCount = new AtomicReference<>(0);
        toDayUserCountList.stream().reduce(Integer::sum).ifPresent(toDayUserCount::set);
        List<Integer> allUserCountList = accountUserMapper.getAllUserCount();
        AtomicReference<Integer> allUserCount = new AtomicReference<>(0);
        allUserCountList.stream().reduce(Integer::sum).ifPresent(allUserCount::set);
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

        List<StarNftWalletRecord> dayPayRecord = starNftWalletRecordMapper.dayWalletRecord();
        //昨日充值交易

        List<StarNftWalletRecord> toDayPayRecord = starNftWalletRecordMapper.toDayWalletRecord();
        //当日充值总金额
        BigDecimal dayTotalPayMoney = dayPayRecord.stream().filter(record -> record.getTsType().equals(1L) ).map(StarNftWalletRecord::getTsMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        //昨天充值总金额
        BigDecimal toDayTotalPayMoney = toDayPayRecord.stream().filter(record -> record.getTsType().equals(1L) ).map(StarNftWalletRecord::getTsMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        //当日交易
//        List<StarNftOrder> dayOrder = starNftOrderMapper.dayOrder();
        //昨日交易
//        List<StarNftOrder> toDayOrder = starNftOrderMapper.toDayOrder();
        //当日交易金额
        BigDecimal dayTotalOrderMoney = dayPayRecord.stream().filter(record -> record.getTsType().equals(3L) ).map(StarNftWalletRecord::getTsMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        //昨日交易金额
        BigDecimal toDayTotalOrderMoney = toDayPayRecord.stream().filter(record -> record.getTsType().equals(3L) ).map(StarNftWalletRecord::getTsMoney).reduce(BigDecimal.ZERO, BigDecimal::add);


        //当日交易手续费
        BigDecimal dayTotalOrderRate = dayPayRecord.stream().filter(record -> record.getTsType().equals(3L) ).map(StarNftWalletRecord::getTsFee).reduce(BigDecimal.ZERO, BigDecimal::add);
//        dayTotalOrderRate = dayTotalOrderRate.add(starNftWalletConfig.getStableRate().multiply(new BigDecimal(dayOrder.size())));
        //昨日交易手续费
        BigDecimal toDayTotalOrderRate = toDayPayRecord.stream().filter(record -> record.getTsType().equals(3L) ).map(StarNftWalletRecord::getTsFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        //充值总数
        long dayPayTotal = dayPayRecord.stream().filter(record -> record.getTsType().equals(1L)).count();
        long toDayPayTotal = toDayPayRecord.stream().filter(record -> record.getTsType().equals(1L)).count();
        //交易总数
        long dayOrderTotal = dayPayRecord.stream().filter(record -> record.getTsType().equals(3L)).count();
        long toDayOrderTotal = toDayPayRecord.stream().filter(record -> record.getTsType().equals(3L)).count();
        return FrontVo.builder()
                .dayWithDrawApplyTotal(dayWithDrawApply.size())
                .toDayWithDrawApplyTotal(toDayWithDrawApply.size())
                .dayTotalWithdrawMoney(dayTotalWithdrawMoney)
                .toDayTotalWithdrawMoney(toDayTotalWithdrawMoney)
                .dayTotalWithDrawRate(dayTotalWithDrawRate)
                .toDayTotalWithDrawRate(toDayTotalWithDrawRate)
                .dayPayRecordTotal(dayPayTotal)
                .toDayPayRecordTotal(toDayPayTotal)
                .dayTotalPayMoney(dayTotalPayMoney)
                .toDayTotalPayMoney(toDayTotalPayMoney)
                .dayOrderTotal(dayOrderTotal)
                .toDayOrderTotal(toDayOrderTotal)
                .dayTotalOrderMoney(dayTotalOrderMoney)
                .toDayTotalOrderMoney(toDayTotalOrderMoney)
                .dayTotalOrderRate(dayTotalOrderRate)
                .toDayTotalOrderRate(toDayTotalOrderRate)
                .allUserCount(allUserCount.get())
                .dayUserCount(dayUserCount.get())
                .toDayUserCount(toDayUserCount.get())
                .build();


    }
}
