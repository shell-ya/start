package com.starnft.star.application.process.wallet.impl;

import com.google.common.collect.Lists;
import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.wallet.WalletProducer;
import com.starnft.star.application.process.wallet.IWalletCore;
import com.starnft.star.application.process.wallet.req.PayRecordReq;
import com.starnft.star.application.process.wallet.req.RechargeFacadeReq;
import com.starnft.star.application.process.wallet.res.RechargeReqResult;
import com.starnft.star.application.process.wallet.res.TransactionRecord;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.model.req.PayCheckReq;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.user.model.vo.UserInfoVO;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.*;
import com.starnft.star.domain.wallet.model.res.CardBindResult;
import com.starnft.star.domain.wallet.model.res.TxResultRes;
import com.starnft.star.domain.wallet.model.res.WithdrawResult;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.domain.wallet.service.stateflow.IStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class WalletCore implements IWalletCore {

    @Resource
    private WalletService walletService;

    @Resource
    private IUserService userService;

    @Resource
    private RedisLockUtils redisLockUtils;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private WalletProducer walletProducer;

    @Resource
    private Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    private IPaymentService paymentService;

    @Resource
    private IStateHandler stateHandler;

    @Resource
    private IMessageSender messageSender;

    @Override
    @Transactional
    public RechargeReqResult recharge(@Validated RechargeFacadeReq rechargeFacadeReq) {
        //参数验证
        verifyParam(rechargeFacadeReq);

        String isTransaction = String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(),
                new StringBuffer(String.valueOf(rechargeFacadeReq.getUserId())).append(rechargeFacadeReq.getWalletId()));

        if (redisUtil.hasKey(isTransaction)) {
            throw new StarException(StarError.IS_TRANSACTION);
        }
        //锁定当前钱包交易
        if (redisLockUtils.lock(isTransaction, RedisKey.REDIS_TRANSACTION_ING.getTime())) {
            try {
                //钱包领域 生成待支付充值订单
                WalletRecordReq walletRecordReq = walletRecordInit(rechargeFacadeReq);
                boolean isSuccess = walletService.rechargeRecordGenerate(walletRecordReq);

                RechargeReqResult rechargeReqResult = new RechargeReqResult();
                if (isSuccess) {
                    //调用支付领域服务 获取拉起支付参数
                    PaymentRes payResult = paymentService.pay(buildPaymentReq(walletRecordReq, rechargeFacadeReq));
                    if (payResult.getStatus().equals(ResultCode.SUCCESS.getCode())) {
                        rechargeReqResult.setOrderSn(payResult.getOrderSn());
                        //组装跳转url
                        assembly(rechargeReqResult);
                        //修改状态为支付中
                        modifyStatus(walletRecordReq, payResult);
                        return rechargeReqResult;
                    }
                }
            } catch (Exception e) {
                log.error("uid:[{}] 充值异常", rechargeFacadeReq.getUserId(), e);
                throw new StarException(StarError.PAY_PROCESS_ERROR);
            } finally {
                redisLockUtils.unlock(isTransaction);
            }
        }
        throw new StarException(StarError.PAY_PROCESS_ERROR);
    }

    private void modifyStatus(WalletRecordReq walletRecordReq, PaymentRes payResult) {
        //状态机修改充值单状态
        Result modifyResult = stateHandler.paying(payResult.getOrderSn(), StarConstants.Pay_Status.valueOf(walletRecordReq.getPayStatus()));
        if (modifyResult.getCode().equals(ResultCode.SUCCESS.getCode())) {
            return;
        }
        log.error("当前状态[{}] 修改失败", StarConstants.Pay_Status.valueOf(walletRecordReq.getPayStatus()));
        throw new RuntimeException(modifyResult.getInfo());
    }


    private void assembly(RechargeReqResult rechargeReqResult) {
    }

    private PaymentRich buildPaymentReq(WalletRecordReq walletRecordReq, RechargeFacadeReq rechargeFacadeReq) {

        //充值回调topic设置 用户拉起第三方支付支付后 结果回调后将状态及参数发送该topic下的消费者消费处理
        String rechargeCallbackProcessTopic = String.format(TopicConstants.WALLET_RECHARGE_DESTINATION.getFormat(),
                TopicConstants.WALLET_RECHARGE_DESTINATION.getTag());

        return PaymentRich.builder().payChannel(rechargeFacadeReq.getChannel())
                .totalMoney(rechargeFacadeReq.getMoney())
                .userId(rechargeFacadeReq.getUserId())
                .orderSn(walletRecordReq.getRecordSn())
                .frontUrl("")//todo 查询支付结果页连接
                .multicastTopic(rechargeCallbackProcessTopic)
                .build();
    }

    @Override
    public ResponsePageResult<TransactionRecord> walletRecordQuery(PayRecordReq recordReq) {
        ResponsePageResult<WalletRecordVO> walletRecordResult = walletService
                .queryTransactionRecord(TransactionRecordQueryReq.builder()
                        .page(recordReq.getPage()).size(recordReq.getSize())
                        .userId(recordReq.getUserId())
                        .startDate(recordReq.getStartTime())
                        .payStatus(recordReq.getPayStatus())
                        .endDate(recordReq.getEndTime())
                        .transactionType(recordReq.getPayType()).build());

        List<TransactionRecord> res = Lists.newArrayList();
        for (WalletRecordVO walletRecordVO : walletRecordResult.getList()) {
            TransactionRecord transactionRecord = recordVOConvert(walletRecordVO, recordReq.getUserId());
            res.add(transactionRecord);
        }
        return ResponsePageResult.listReplace(walletRecordResult, res);
    }

    @Override
    public WithdrawResult withdraw(WithDrawReq withDrawReq) {
        return walletService.withdraw(withDrawReq);
    }

    @Override
    public boolean cardBinding(CardBindReq cardBindReq) {
        if (cardBindReq.getCardNo().toString().length() < 13 || cardBindReq.getCardNo().toString().length() > 19) {
            throw new StarException("卡号长度错误");
        }
        UserInfoVO userInfoVO = userService.queryUserInfo(cardBindReq.getUid());
        cardBindReq.setNickname(userInfoVO.getNickName());
        List<CardBindResult> cardBindResults = obtainCardBinds(cardBindReq.getUid());
        if (cardBindResults.size() >= 5) {
            throw new StarException("银行卡超过绑定5张上限");
        }
        if (cardBindReq.getIsDefault() == null || cardBindReq.getIsDefault() == 0) {
            cardBindReq.setIsDefault(cardBindResults.size() >= 1 ? 0 : 1);
        }
        return walletService.cardBind(cardBindReq);
    }

    @Override
    public TxResultRes queryTxResult(TxResultReq txResultReq) {
        TxResultRes txResultRes = walletService.txResultQuery(txResultReq);
        //根据状态做不同的处理
        if (StarConstants.Pay_Status.PAY_ING.name().equals(txResultRes.getStatus())) {
            //轮训多次无果 查单
            PayCheckRes payCheckRes = paymentService.orderCheck(PayCheckReq.builder()
                    .payChannel(txResultReq.getPayChannel()).orderSn(txResultReq.getOrderSn()).build());
            //将查询到的结果发送mq
            String rechargeCallbackProcessTopic = String.format(TopicConstants.WALLET_RECHARGE_DESTINATION.getFormat(),
                    TopicConstants.WALLET_RECHARGE_DESTINATION.getTag());
            messageSender.send(rechargeCallbackProcessTopic, Optional.of(payCheckRes));
            //todo 前端轮训后 仍然是支付中 提示 用户稍后再试 前端判断还是直接异常？
            return txResultRes;
        }
        //成功或者失败直接返回
        if (StarConstants.Pay_Status.PAY_SUCCESS.name().equals(txResultRes.getStatus())
                || StarConstants.Pay_Status.PAY_FAILED.name().equals(txResultRes.getStatus())) {
            return txResultRes;
        }
        //支付异常返回 需要前端提示人工处理
        return txResultRes;
    }

    @Override
    public List<CardBindResult> obtainCardBinds(Long uid) {
        return walletService.obtainCards(uid);
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
                .orderSn(walletRecordVO.getOrderSn())
                .outTradeNo(walletRecordVO.getOutTradeNo())
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
                .build();
    }
}
