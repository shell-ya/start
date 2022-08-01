package com.starnft.star.application.process.wallet.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
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
import com.starnft.star.domain.user.model.vo.UserRealInfo;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.domain.wallet.model.req.*;
import com.starnft.star.domain.wallet.model.res.CardBindResult;
import com.starnft.star.domain.wallet.model.res.TxResultRes;
import com.starnft.star.domain.wallet.model.res.WithdrawResult;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.model.vo.WithdrawRecordVO;
import com.starnft.star.domain.wallet.service.WalletConfig;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.domain.wallet.service.stateflow.IStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;


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
    private Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    private IPaymentService paymentService;

    @Resource
    private IStateHandler stateHandler;

    @Resource
    private IMessageSender messageSender;

    @Override
    public RechargeReqResult recharge(@Validated RechargeFacadeReq rechargeFacadeReq) {
        //参数验证
        walletService.verifyParam(rechargeFacadeReq.getChannel());

        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.valueOf(rechargeFacadeReq.getChannel()));

        BigDecimal rechargeLimit = config.getRechargeLimit();

        // todo 充值限额校验


        if (rechargeFacadeReq.getChannel().equals(StarConstants.PayChannel.CheckPay.name())) {
            //验证实名信息
            if (!userService.isCertification(rechargeFacadeReq.getUserId())) {
                throw new StarException(StarError.ERROR_AUTHENTICATION);
            }
        }
        UserRealInfo userRealInfo = userService.getUserRealInfo(rechargeFacadeReq.getUserId());

        String isTransaction = String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(), rechargeFacadeReq.getUserId());

        if (redisUtil.hasKey(RedisLockUtils.REDIS_LOCK_PREFIX + isTransaction)) {
            throw new StarException(StarError.IS_TRANSACTION);
        }
        //锁定当前钱包交易
        if (redisLockUtils.lock(isTransaction, RedisKey.REDIS_TRANSACTION_ING.getTimeUnit().toSeconds(RedisKey.REDIS_TRANSACTION_ING.getTime()))) {
            try {
                //生成充值单状态为支付中
                WalletRecordReq walletRecordReq = walletRecordInit(rechargeFacadeReq);
                boolean isSuccess = walletService.rechargeRecordGenerate(walletRecordReq);
                if (!isSuccess) {
                    throw new RuntimeException("生成预充值记录失败");
                }
                RechargeReqResult rechargeReqResult = new RechargeReqResult();
                //调用支付领域服务 获取拉起支付参数
                PaymentRes payResult = paymentService.pay(buildPaymentReq(walletRecordReq, rechargeFacadeReq, userRealInfo));
                if (payResult.getStatus().equals(ResultCode.SUCCESS.getCode())) {
                    rechargeReqResult.setOrderSn(payResult.getOrderSn());
                    //组装跳转url
                    assembly(rechargeReqResult, payResult);
                    rechargeReqResult.setChannel(rechargeFacadeReq.getChannel());
                    return rechargeReqResult;
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

    @Deprecated
    private void modifyStatus(WalletRecordReq walletRecordReq, PaymentRes payResult) {
        //状态机修改充值单状态
        Result modifyResult = stateHandler.paying(payResult.getOrderSn(), StarConstants.Pay_Status.valueOf(walletRecordReq.getPayStatus()));
        if (modifyResult.getCode().equals(ResultCode.SUCCESS.getCode())) {
            return;
        }
        log.error("当前状态[{}] 修改失败", StarConstants.Pay_Status.valueOf(walletRecordReq.getPayStatus()));
        throw new RuntimeException(modifyResult.getInfo());
    }


    private void assembly(RechargeReqResult rechargeReqResult, PaymentRes payResult) {
        rechargeReqResult.setNeededParam(payResult.getThirdPage());
        rechargeReqResult.setForward(payResult.getGatewayApi());
    }

    private PaymentRich buildPaymentReq(WalletRecordReq walletRecordReq, RechargeFacadeReq rechargeFacadeReq, UserRealInfo userRealInfo) throws UnsupportedEncodingException {

        //充值回调topic设置 用户拉起第三方支付支付后 结果回调后将状态及参数发送该topic下的消费者消费处理
        String rechargeCallbackProcessTopic = String.format(TopicConstants.WALLET_RECHARGE_DESTINATION.getFormat(),
                TopicConstants.WALLET_RECHARGE_DESTINATION.getTag());

        String payTime = DateUtil.formatLocalDateTime(LocalDateTime.now());
//        String forward = rechargeFacadeReq.getForward()
//                .concat("&transactionSn=" + walletRecordReq.getRecordSn() + "&payTime=" + payTime);

        HashMap<String, Object> extend = Maps.newHashMap();
        //sand快捷充值渠道参数
        if (rechargeFacadeReq.getChannel().equals(StarConstants.PayChannel.CheckPay.name())) {
            extend.put("userName", userRealInfo.getFullName());
            extend.put("idCard", userRealInfo.getIdNumber());
        }
        return PaymentRich.builder().payChannel(rechargeFacadeReq.getChannel())
                .totalMoney(rechargeFacadeReq.getMoney())
                .userId(rechargeFacadeReq.getUserId())
                .orderSn(walletRecordReq.getRecordSn())
                .bankNo(String.valueOf(rechargeFacadeReq.getCardNo()))
                .clientIp("1.1.1.1")
                .frontUrl(rechargeFacadeReq.getForward())
                .payExtend(extend)
                .orderType(StarConstants.OrderType.RECHARGE)
                .multicastTopic(rechargeCallbackProcessTopic)
                .build();
    }

    @Override
    @SneakyThrows
    public ResponsePageResult<TransactionRecord> walletRecordQuery(PayRecordReq recordReq) {
        ResponsePageResult<WalletRecordVO> walletRecordResult = walletService
                .queryTransactionRecord(TransactionRecordQueryReq.builder()
                        .page(recordReq.getPage()).size(recordReq.getSize())
                        .userId(recordReq.getUserId())
                        .payStatus(recordReq.getPayStatus())
                        .startDate(StringUtils.isNotBlank(recordReq.getStartTime()) ? DateUtil.parseDate(recordReq.getStartTime()).toSqlDate() : null)
                        .endDate(StringUtils.isNotBlank(recordReq.getEndTime()) ? DateUtil.parseDate(recordReq.getEndTime()).toSqlDate() : null)
                        .transactionType(recordReq.getPayType()).build());


        List<TransactionRecord> res = Lists.newArrayList();
        if (CollectionUtil.isEmpty(walletRecordResult.getList())) {
            return ResponsePageResult.listReplace(walletRecordResult, res);
        }
        for (WalletRecordVO walletRecordVO : walletRecordResult.getList()) {
            TransactionRecord transactionRecord = recordVOConvert(walletRecordVO, recordReq.getUserId());
            //todo 过滤提现订单查询提现记录填写带驳回原因字段填充响应结果  交易流水号 record_sn 与 提现流水号 withdraw_trade_no
            if (StarConstants.Transaction_Type.Withdraw.getFont().equals(transactionRecord.getPayType())) {
                WithdrawRecordVO withdrawRecordVO = walletService.queryWithDrawByTradeNo(walletRecordVO.getRecordSn());
                transactionRecord.setApplyMsg(withdrawRecordVO.getApplyMsg());
                transactionRecord.setCardNo(String.valueOf(withdrawRecordVO.getBankNo()));
            }
            res.add(transactionRecord);
        }
        return ResponsePageResult.listReplace(walletRecordResult, res);
    }

    @Override
    public WithdrawResult withdraw(WithDrawReq withDrawReq) {
        //验证支付凭证
        userService.assertPayPwdCheckSuccess(withDrawReq.getUid(), withDrawReq.getPwdToken());
        //验证参数合法性
        walletService.verifyParam(withDrawReq.getChannel());
        //验证余额
        walletService.balanceVerify(withDrawReq.getUid(), new BigDecimal(withDrawReq.getMoney()));
        //提现金额验证
        BigDecimal money = new BigDecimal(withDrawReq.getMoney());
        if (money.subtract(new BigDecimal(100)).doubleValue() < 0) {
            throw new StarException(StarError.WITHDRAW_ILLEGAL_MONEY);
        }
        try {
            return walletService.withdraw(withDrawReq);
        } finally {
            walletService.threadClear();
        }
    }

    @Override
    public boolean cardBinding(CardBindReq cardBindReq) {
        if (cardBindReq.getCardNo().length() < 13 || cardBindReq.getCardNo().length() > 19) {
            throw new StarException(StarError.CARD_LENGTH_ERROR);
        }
        UserInfoVO userInfoVO = userService.queryUserInfo(cardBindReq.getUid());
        cardBindReq.setNickname(userInfoVO.getNickName());
        List<CardBindResult> cardBindResults = obtainCardBinds(cardBindReq.getUid());
        if (cardBindResults.size() >= 5) {
            throw new StarException(StarError.CARD_BIND_NUMS_ERROR);
        }
        if (cardBindReq.getIsDefault() == null || cardBindReq.getIsDefault() == 0) {
            cardBindReq.setIsDefault(cardBindResults.size() >= 1 ? 0 : 1);
        }
        return walletService.cardBind(cardBindReq);
    }

    @Override
    public TxResultRes queryTxResult(TxResultReq txResultReq) {
        walletService.verifyParam(txResultReq.getPayChannel());
        TxResultRes txResultRes = walletService.txResultQuery(txResultReq);
        //根据状态做不同的处理
        if (StarConstants.Pay_Status.PAY_ING.name().equals(txResultRes.getStatus())) {
            //轮训多次无果 查单
            try {
                PayCheckRes payCheckRes = paymentService.orderCheck(PayCheckReq.builder()
                        .payChannel(txResultReq.getPayChannel()).orderSn(txResultReq.getOrderSn()).build());
                //将查询到的结果发送mq
                String rechargeCallbackProcessTopic = String.format(TopicConstants.WALLET_RECHARGE_DESTINATION.getFormat(),
                        TopicConstants.WALLET_RECHARGE_DESTINATION.getTag());
                messageSender.send(rechargeCallbackProcessTopic, Optional.of(payCheckRes));
            } catch (Exception e) {
                log.error("查单失败：单号：{}", txResultReq.getOrderSn(), e);

            }
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
    public Boolean queryTxBatch(CheckBatchReq batchReq) {

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR,
                    calendar.get(Calendar.HOUR) - batchReq.getHours());
            //查找最近时间区间订单
            TransactionRecordQueryReq req = new TransactionRecordQueryReq();
            req.setStartDate(calendar.getTime());
            req.setEndDate(new Date());
//            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            req.setStartDate(fmt.parse("2022-07-18 10:55:49"));
//            req.setEndDate(fmt.parse("2022-07-18 12:55:49"));
            ArrayList<Integer> integers = new ArrayList<>();
            integers.add(1);
            req.setTransactionType(integers);
            req.setPage(1);
            req.setSize(batchReq.getSize());
            req.setPayStatus(StarConstants.Pay_Status.PAY_ING.name());
            ResponsePageResult<WalletRecordVO> walletRecordVOResponsePageResult =
                    walletService.queryTransactionRecord(req);
            //遍历去查单
            List<WalletRecordVO> list = walletRecordVOResponsePageResult.getList();
            log.info("支付中充值订单数量:{}", list.size());
            for (WalletRecordVO walletRecordVO : list) {
                Long uid = walletRecordVO.getToUid();
                TxResultRes txResultRes = queryTxResult(new TxResultReq(uid, walletRecordVO.getPayChannel(), walletRecordVO.getRecordSn()));
            }
        } catch (Exception e) {
            log.error("查单异常：", e);
        }

        return Boolean.TRUE;
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
        String payType = "";
        for (StarConstants.Transaction_Type value : StarConstants.Transaction_Type.values()) {
            if (value.getCode().equals(walletRecordVO.getTsType())) {
                payType = value.getFont();
            }
        }
        NumberFormat number = NumberFormat.getNumberInstance();
        number.setMaximumFractionDigits(2);
        return TransactionRecord.builder()
                .userId(userId)
                .channel(walletRecordVO.getPayChannel())
                .money(walletRecordVO.getTsMoney())
                .outTradeNo(walletRecordVO.getOutTradeNo())
                .status(walletRecordVO.getPayStatus())
                .payTime(walletRecordVO.getPayTime())
                .payType(payType)
                .currMoney(walletRecordVO.getCurrMoney() == null ? null : number.format(walletRecordVO.getCurrMoney().setScale(2)))
                .transactionSn(walletRecordVO.getRecordSn()).build();
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
                .recordSn(StarConstants.OrderPrefix.RechargeSn.getPrefix().concat(String.valueOf(iIdGenerator.nextId())))
                .from_uid(0L) // 充值为0
                .to_uid(rechargeFacadeReq.getUserId())
                .payChannel(rechargeFacadeReq.getChannel())
                .tsType(StarConstants.Transaction_Type.Recharge.getCode())
                .tsMoney(rechargeFacadeReq.getMoney())
                .tsCost(rechargeFacadeReq.getMoney())
                .tsFee(BigDecimal.ZERO)
                .payTime(new Date())
                .payStatus(StarConstants.Pay_Status.PAY_ING.name())
                .build();
    }
}
