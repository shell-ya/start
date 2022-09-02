package com.starnft.star.business.refund;

import com.starnft.star.business.mapper.StarNftOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Date 2022/9/2 8:37 PM
 * @Author ： shellya
 */
@Slf4j
@Component("refundHandler")
public abstract class RefundHandler {

    @Resource
    private StarNftOrderMapper orderMapper;


    public abstract boolean refundOrder(String orderSn);

    public Boolean refundPayUser(String orderSn){
        //查询订单交易金额 返回付款账户 增加钱包余额  并生成退款记录 walletRecord walletLog
        return true;
    }
}
