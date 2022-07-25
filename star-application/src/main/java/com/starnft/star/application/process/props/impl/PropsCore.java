package com.starnft.star.application.process.props.impl;

import com.starnft.star.application.process.props.IPropsCore;
import com.starnft.star.application.process.props.invokers.PropsExecutor;
import com.starnft.star.application.process.props.model.req.PropBuyReq;
import com.starnft.star.application.process.props.model.req.PropInvokeReq;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.prop.model.req.PropsConsumptionReq;
import com.starnft.star.domain.prop.model.req.PropsDeliveryReq;
import com.starnft.star.domain.prop.model.vo.PropsRecordVO;
import com.starnft.star.domain.prop.model.vo.PropsRelationVO;
import com.starnft.star.domain.prop.model.vo.PropsVO;
import com.starnft.star.domain.prop.service.IPropsService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.WalletPayRequest;
import com.starnft.star.domain.wallet.model.res.WalletPayResult;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropsCore implements IPropsCore {

    private static final Logger log = LoggerFactory.getLogger(PropsCore.class);
    final IPropsService propsService;
    final PropsExecutor propsExecutor;
    final WalletService walletService;
    final IUserService userService;
    final TransactionTemplate transactionTemplate;

    final Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public Boolean propInvoke(PropInvokeReq req) {

        PropsRelationVO propsRelationVO = propsService.specificProp(req.getUserId(), Long.parseLong(req.getPropId()));

        //道具存在判断
        if (propsRelationVO == null) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "您未拥有该道具");
        }

        // TODO: 2022/7/9  道具可使用时间判断


        PropsVO propsVO = propsService.queryPropsDetails(Long.parseLong(req.getPropId()));

        Boolean isSuccess = propsService.propsConsumption(PropsConsumptionReq.builder()
                .propId(Long.parseLong(req.getPropId()))
                .userId(req.getUserId())
                .propsRelationVO(propsRelationVO)
                .invoker(() -> propsExecutor.execute(req.getUserId(), propsVO))
                .build());
        return isSuccess;
    }

    @Override
    public Boolean propPay(@Validated PropBuyReq propBuyReq) {
        //验证支付凭证
//        userService.assertPayPwdCheckSuccess(propBuyReq.getUid(), propBuyReq.getPayToken(), true);

        PropsVO propsVO = propsService.queryPropsDetails(Long.parseLong(propBuyReq.getPropId()));
        //道具存在性判断
        if (Objects.isNull(propsVO)) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "道具不存在");
        }

        //道具购买条件校验
        if (propsVO.getCanBuy() != 1) {
            throw new StarException(StarError.PROP_CAN_NOT_BUY);
        }

        //可购买数量限制
        if (propsVO.getBuyLimit() != -1 && propsVO.getBuyLimit() < propBuyReq.getNumbers()) {
            throw new StarException(StarError.PROP_CAN_BUY_OVERFLOW);
        }

        //库存判断

        try {
            //验证余额
            walletService.balanceVerify(propBuyReq.getUid(), propsVO.getPrice());
            return transactionTemplate.execute(status -> {

                String orderSn = StarConstants.OrderPrefix.PROPSHOP.getPrefix().concat(String.valueOf(idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId()));

                // TODO: 2022/7/24
                Function<BigDecimal, BigDecimal> rightCount = doRight();
                //余额减扣
                WalletPayRequest walletPayReq = createWalletPayReq(propBuyReq, propsVO, rightCount, orderSn);
                WalletPayResult walletPayResult = walletService.doWalletPay(walletPayReq);

                //库存减扣

                //道具发放
                Boolean deliverySuccess = propsService.propsDelivery(new PropsDeliveryReq(propBuyReq.getUid(), propsVO, propBuyReq.getNumbers()));

                //生成道具购买记录
                Boolean recordGenerated = propsService.propsPurchaseRecordGenerated(
                        createPropRecord(orderSn, propBuyReq, walletPayResult.getStatus(), propsVO, walletPayReq));
                return ResultCode.SUCCESS.getCode().equals(walletPayResult.getStatus()) && deliverySuccess && recordGenerated;
            });

        } finally {
            walletService.threadClear();
        }
    }

    private PropsRecordVO createPropRecord(String orderSn, PropBuyReq propBuyReq, Integer status, PropsVO propsVO, WalletPayRequest walletPayReq) {
        PropsRecordVO propsRecordVO = new PropsRecordVO();
        propsRecordVO.setPropId(Long.parseLong(propBuyReq.getPropId()));
        propsRecordVO.setCount(propBuyReq.getNumbers());
        propsRecordVO.setUid(propBuyReq.getUid());
        propsRecordVO.setStatus(status);
        propsRecordVO.setOrderSn(orderSn);
        propsRecordVO.setUnitCost(propsVO.getPrice());
        propsRecordVO.setDiscounts(walletPayReq.getTotalPayAmount().abs().subtract(walletPayReq.getPayAmount().abs()));
        propsRecordVO.setOrderCost(walletPayReq.getPayAmount().abs());
        return propsRecordVO;
    }

    private Function<BigDecimal, BigDecimal> doRight() {
        return totalPayAmount -> {
            //todo 权益计算 totalPayAmount 计算前价格  payAmount 实付价格
            BigDecimal payAmount = totalPayAmount;
            // TODO: 2022/7/24  积分抵扣 优惠券抵扣等一系列消耗减扣动作 可同步 可mq异步

            return payAmount;
        };
    }

    private WalletPayRequest createWalletPayReq(PropBuyReq propBuyReq, PropsVO propsVO, Function<BigDecimal, BigDecimal> rightCount, String orderSn) {
        WalletPayRequest walletPayRequest = new WalletPayRequest();
        walletPayRequest.setUserId(propBuyReq.getUid());
        walletPayRequest.setStatus(StarConstants.Pay_Status.PAY_SUCCESS.name());
        BigDecimal totalPayAmount = propsVO.getPrice().multiply(new BigDecimal(propBuyReq.getNumbers()));
        walletPayRequest.setTotalPayAmount(totalPayAmount);
        walletPayRequest.setFee(BigDecimal.ZERO);

        //执行价格抵扣 使用后道具减扣操作
        BigDecimal payAmount = rightCount.apply(totalPayAmount);

        walletPayRequest.setPayAmount(payAmount.signum() == -1 ? payAmount : payAmount.negate());
        walletPayRequest.setFromUid(propBuyReq.getUid());
        walletPayRequest.setToUid(0L);//官方id
        walletPayRequest.setType(StarConstants.Transaction_Type.Buy.getCode());
        walletPayRequest.setChannel(StarConstants.PayChannel.Balance.name());
        walletPayRequest.setOrderSn(orderSn);
        return walletPayRequest;
    }
}
