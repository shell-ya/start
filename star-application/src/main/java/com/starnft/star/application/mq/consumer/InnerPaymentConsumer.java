package com.starnft.star.application.mq.consumer;

import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.wallet.model.req.WalletPayRequest;
import com.starnft.star.domain.wallet.model.res.WalletPayResult;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InnerPaymentConsumer implements RocketMQListener<WalletPayRequest> {

    private static final Map<StarConstants.PayChannel, Function<WalletPayRequest, WalletPayResult>> payOptMapping = new ConcurrentHashMap<>();

    final WalletService walletService;

    final IPaymentService paymentService;

    @Override
    public void onMessage(WalletPayRequest walletPayRequest) {
        //渠道分流支付
        StarConstants.PayChannel channel = readChannel(walletPayRequest.getChannel());
        if (channel == null) {
            throw new RuntimeException("渠道未知，参数错误！");
        }
        Function<WalletPayRequest, WalletPayResult> payOpt = payOptMapping.get(channel);
        WalletPayResult result = payOpt.apply(walletPayRequest);

        //记录支付状态

    }


    @PostConstruct
    public void initStrategy() {
        payOptMapping.put(StarConstants.PayChannel.Balance, balancePay());
        payOptMapping.put(StarConstants.PayChannel.CloudAccount, cloudPay());
    }

    private Function<WalletPayRequest, WalletPayResult> cloudPay() {
        return walletPayRequest -> {
            PaymentRes results = paymentService.pay(buildPayReq(walletPayRequest));
            return null;
        };
    }

    private PaymentRich buildPayReq(WalletPayRequest walletPayRequest) {
        HashMap<String, Object> valueMap = Maps.newHashMap();
        valueMap.put("cost", walletPayRequest.getFee().toString());
        valueMap.put("remark", walletPayRequest.getThemeId().toString().concat("#").concat(walletPayRequest.getNumberId().toString()));
        valueMap.put("accUserId", walletPayRequest.getUserId().toString());
        return PaymentRich.builder()
                .totalMoney(walletPayRequest.getPayAmount()).payChannel(StarConstants.PayChannel.CloudAccount.name())
                .frontUrl("https://www.circlemeta.cn").clientIp("192.168.1.1")
                .orderSn(walletPayRequest.getOrderSn()).userId(walletPayRequest.getUserId())
                .payExtend(valueMap)
                .orderType(walletPayRequest.getOrderSn().startsWith(StarConstants.OrderPrefix.PublishGoods.getPrefix())
                        ? StarConstants.OrderType.PUBLISH_GOODS : StarConstants.OrderType.MARKET_GOODS).build();
    }

    private Function<WalletPayRequest, WalletPayResult> balancePay() {
        return walletService::doWalletPay;
    }

    private StarConstants.PayChannel readChannel(String channel) {
        for (StarConstants.PayChannel value : StarConstants.PayChannel.values()) {
            if (value.name().equals(channel)) {
                return value;
            }
        }
        return null;
    }
}
