package com.starnft.star.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Strings;
import com.starnft.star.business.domain.NftWallet;
import com.starnft.star.business.domain.StarNftWalletLog;
import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.UserStrategyExport;
import com.starnft.star.business.domain.dto.WalletInfoReq;
import com.starnft.star.business.domain.dto.WalletLogReq;
import com.starnft.star.business.domain.dto.WalletRecordReq;
import com.starnft.star.business.domain.vo.RechargeVO;
import com.starnft.star.business.domain.vo.WalletRecordVO;
import com.starnft.star.business.domain.vo.WalletVO;
import com.starnft.star.business.mapper.IUserStrategyExportDao;
import com.starnft.star.business.mapper.NftWalletMapper;
import com.starnft.star.business.mapper.StarNftWalletLogMapper;
import com.starnft.star.business.mapper.StarNftWalletRecordMapper;
import com.starnft.star.business.service.INftWalletService;
import com.starnft.star.common.Result;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.ServiceException;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.common.utils.redis.RedisLockUtils;
import com.starnft.star.common.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 钱包Service业务层处理
 *
 * @author shellya
 * @date 2022-06-08
 */
@Service
public class NftWalletServiceImpl implements INftWalletService
{
    @Autowired
    private NftWalletMapper nftWalletMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisLockUtils redisLockUtils;
    @Resource
    private TransactionTemplate template;
    @Resource
    private StarNftWalletRecordMapper walletRecordMapper;
    @Resource
    private StarNftWalletLogMapper walletLogMapper;
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;
    /**
     * 查询钱包
     *
     * @param id 钱包主键
     * @return 钱包
     */
    @Override
    public NftWallet selectNftWalletByUid(Long id)
    {
        return nftWalletMapper.selectNftWalletByUid(id);
    }

    /**
     * 查询钱包列表
     *
     * @param nftWallet 钱包
     * @return 钱包
     */
    @Override
    public List<NftWallet> selectNftWalletList(NftWallet nftWallet)
    {
        return nftWalletMapper.selectNftWalletList(nftWallet);
    }

    /**
     * 新增钱包
     *
     * @param nftWallet 钱包
     * @return 结果
     */
    @Override
    public int insertNftWallet(NftWallet nftWallet)
    {
        return nftWalletMapper.insertNftWallet(nftWallet);
    }

    /**
     * 修改钱包
     *
     * @param nftWallet 钱包
     * @return 结果
     */
    @Override
    public int updateNftWallet(NftWallet nftWallet)
    {
        return nftWalletMapper.updateNftWallet(nftWallet);
    }

    /**
     * 批量删除钱包
     *
     * @param ids 需要删除的钱包主键
     * @return 结果
     */
    @Override
    public int deleteNftWalletByIds(Long[] ids)
    {
        return nftWalletMapper.deleteNftWalletByIds(ids);
    }

    /**
     * 删除钱包信息
     *
     * @param id 钱包主键
     * @return 结果
     */
    @Override
    public int deleteNftWalletById(Long id)
    {
        return nftWalletMapper.deleteNftWalletById(id);
    }


    @Override
    public Boolean walletRecharge(RechargeVO rechargeVO) {
        //查询用户ID
//        return nftWalletMapper.walletRecharge(nftWallet);

        UserStrategyExport userStrategyExport = null;
        //判断当前用户是否抽中奖项。
        if (1 == rechargeVO.getVerification()){
            userStrategyExport = userStrategyExportDao.queryUserHash(rechargeVO.getUid(), rechargeVO.getAwardName());
            Optional.ofNullable(userStrategyExport).orElseThrow(() -> new StarException(StarError.USER_NOT_HAS_DRAW));
        }

        AtomicReference<UserStrategyExport> hasDraw = new AtomicReference<>(userStrategyExport);

        NftWallet walletVO = nftWalletMapper.selectNftWalletByUid(Long.parseLong(rechargeVO.getUid()));

        //充值后当前金额
        BigDecimal curr = walletVO.getBalance().add(rechargeVO.getTotalAmount().abs());
        //增加总收入金额
        BigDecimal income = walletVO.getWalletIncome().add(rechargeVO.getTotalAmount().abs());

        rechargeVO.setPayChannel(StarConstants.PayChannel.Other.toString());
        rechargeVO.setOrderSn(StarConstants.OrderPrefix.RechargeSn.getPrefix().concat(SnowflakeWorker.generateId().toString()));

        //钱包交易状态中锁
        String isTransactionKey = RedisLockUtils.REDIS_LOCK_PREFIX + String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(),
                new StringBuffer(String.valueOf(rechargeVO.getUid())));
        //判断当前是否有其他交易正在进行
        if (redisUtil.hasKey(isTransactionKey)) {
            throw new ServiceException("该用户正在交易");
        }
        try{
            Boolean lock = redisLockUtils.lock(isTransactionKey, RedisKey.REDIS_TRANSACTION_ING.getTime());
            Assert.isTrue(lock, () ->  new StarException(StarError.IS_TRANSACTION));
//            if () {
//                //两种充值 1.用户抽奖得到现金奖励或可折现物品 ? 怎样判断是那个中奖记录 2.爪哥任性就行要给钱
//                //用户抽奖的充值记录要将用户的抽奖记录设置为已兑换，并为其人设置一条充值记录
//                //幸运用户直接设置一条充值记录
//                //事务内控制
                Boolean isSuccess = template.execute(status -> {

                    //核销中奖记录为已兑现
                    boolean drawResult = this.sendAwardState(hasDraw);
                    //记录钱包交易记录
                    boolean result = this.rechargeRecordGenerate(walletRecordInit(rechargeVO),curr);
                    //记录余额变动记录
                boolean logWrite = this.createWalletLog(WalletLogReq.builder().walletId(walletVO.getwId())
                        .userId(walletVO.getUid()).offset(rechargeVO.getTotalAmount()).currentMoney(curr).payChannel(rechargeVO.getPayChannel())
                        .orderNo(rechargeVO.getOrderSn()).build());

                //修改余额
                boolean balanceModify = this.modifyWalletBalance(WalletVO.builder().uid(Long.valueOf(rechargeVO.getUid()))
                        .balance(curr).wallet_income(income).build());
                return logWrite && balanceModify && result && drawResult;
            });
            return Boolean.TRUE.equals(isSuccess);
//            }
        }catch (Exception e){
            throw new RuntimeException("充值提现发生异常",e);
        }finally {
            redisLockUtils.unlock(isTransactionKey);
        }
//        return nftWalletMapper.updateNftWallet(nftWallet);
    }

    private boolean sendAwardState(AtomicReference<UserStrategyExport> hasDraw) {
        UserStrategyExport userStrategyExport = hasDraw.get();
        if (null == userStrategyExport) return true;
        userStrategyExport.setGrantState(StarConstants.GrantState.COMPLETE.getCode());
        try{
            this.userStrategyExportDao.updateUserAwardState(userStrategyExport);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Transactional
    public boolean modifyWalletBalance(WalletVO walletVO) {
        NftWallet wallet = new NftWallet();

        NftWallet curr = getWallet(new WalletInfoReq(walletVO.getWalletId(), walletVO.getUid()));

        if (curr == null) {
            throw new RuntimeException("修改的钱包为空：uid:" + walletVO.getUid());
        }
        wallet.setUid(walletVO.getUid());
        wallet.setwId(walletVO.getWalletId());
        wallet.setBalance(walletVO.getBalance());
        wallet.setWalletOutcome(walletVO.getWallet_outcome());
        wallet.setWalletIncome(walletVO.getWallet_income());
        wallet.setFrozen(walletVO.isFrozen() ? 1 : 0);
        wallet.setFrozenFee(walletVO.getFrozen_fee());
        wallet.setVersion(curr.getVersion());

        Integer isSuccess = nftWalletMapper.updateWallet(wallet);

        return isSuccess == 1;
    }

    @Transactional
    public boolean createWalletLog(WalletLogReq walletLogReq) {
        StarNftWalletLog starNftWalletLog = initWalletLog(walletLogReq);
        return walletLogMapper.createChargeLog(starNftWalletLog) == 1;
    }

    private NftWallet getWallet(WalletInfoReq walletInfoReq) {
        NftWallet wallet = new NftWallet();
        wallet.setUid(walletInfoReq.getUid());

        return nftWalletMapper.queryWalletByParams(wallet);
    }

    /**
     * 填充钱包变化记录实体
     *
     * @param walletLogReq 钱包变化记录信息
     * @return 钱包变化记录实体
     */
    private StarNftWalletLog initWalletLog(WalletLogReq walletLogReq) {
        StarNftWalletLog starNftWalletLog = new StarNftWalletLog();
        starNftWalletLog.setUid(walletLogReq.getUserId());
        starNftWalletLog.setwId(walletLogReq.getWalletId());
        starNftWalletLog.setBalanceOffset(walletLogReq.getOffset());
        starNftWalletLog.setCurrentBalance(walletLogReq.getCurrentMoney());
        starNftWalletLog.setRecordSn(walletLogReq.getOrderNo());
        starNftWalletLog.setDisplay(0);
        starNftWalletLog.setCreatedAt(new Date());
        starNftWalletLog.setCreatedBy(walletLogReq.getUserId().toString());
        starNftWalletLog.setChannel(walletLogReq.getPayChannel());

        return starNftWalletLog;
    }


    public boolean rechargeRecordGenerate(WalletRecordReq walletRecordReq,BigDecimal curr) {

        if (Strings.isNullOrEmpty(walletRecordReq.getRecordSn())) {
            throw new StarException(StarError.VALUE_COULD_NOT_BE_NULL, "交易流水号不能为空");
        }

        WalletRecordVO walletRecordVO = queryWalletRecordBySerialNo(walletRecordReq.getRecordSn());

        boolean isSuccess = false;
        if (walletRecordVO == null) {
            isSuccess = this.createWalletRecord(walletRecordReq,curr);
        }

        return isSuccess;
    }

    /**
     * 填充钱包交易记录实体
     *
     * @param walletRecordReq 交易记录信息
     * @return 交易记录实体
     */
    private StarNftWalletRecord initWalletRecord(WalletRecordReq walletRecordReq,BigDecimal currMoney) {
        StarNftWalletRecord starNftWalletRecord = new StarNftWalletRecord();
        starNftWalletRecord.setRecordSn(walletRecordReq.getRecordSn());
        starNftWalletRecord.setFromUid(walletRecordReq.getFrom_uid());
        starNftWalletRecord.setToUid(walletRecordReq.getTo_uid());
        starNftWalletRecord.setPayStatus(walletRecordReq.getPayStatus());
        starNftWalletRecord.setTsType(walletRecordReq.getTsType().longValue());
        starNftWalletRecord.setTsMoney(walletRecordReq.getTsMoney());
        starNftWalletRecord.setTsCost(walletRecordReq.getTsCost());
        starNftWalletRecord.setTsFee(walletRecordReq.getTsFee());
        starNftWalletRecord.setOutTradeNo("LYWC".concat(String.valueOf(SnowflakeWorker.generateId())));
        starNftWalletRecord.setPayChannel(walletRecordReq.getPayChannel());
        starNftWalletRecord.setPayTime(walletRecordReq.getPayTime());
        starNftWalletRecord.setCurrMoney(currMoney);
        starNftWalletRecord.setCreatedBy(walletRecordReq.getFrom_uid() == 0 ? walletRecordReq.getTo_uid().toString() : walletRecordReq.getFrom_uid().toString());
        starNftWalletRecord.setCreatedAt(new Date());
        starNftWalletRecord.setIsDeleted(0);
        return starNftWalletRecord;
    }

    private WalletRecordReq walletRecordInit(RechargeVO rechargeVO) {

        return WalletRecordReq.builder()
                .recordSn(StarConstants.OrderPrefix.RechargeSn.getPrefix().concat(String.valueOf(SnowflakeWorker.generateId())))
                .from_uid(0L) // 充值为0
                .to_uid(Long.parseLong(rechargeVO.getUid()))
                .payChannel(String.valueOf(StarConstants.PayChannel.Other))
                .tsType(StarConstants.Transaction_Type.Recharge.getCode())
                .tsMoney(rechargeVO.getTotalAmount())
                .tsCost(rechargeVO.getTotalAmount())
                .tsFee(BigDecimal.ZERO)
                .payTime(new Date())
                .payStatus(StarConstants.Pay_Status.PAY_SUCCESS.name())
                .build();
    }

    @Transactional
    public boolean createWalletRecord(WalletRecordReq walletRecordReq,BigDecimal currMoney) {
        StarNftWalletRecord starNftWalletRecord = initWalletRecord(walletRecordReq,currMoney);
        return walletRecordMapper.createWalletRecord(starNftWalletRecord) == 1;
    }

    public WalletRecordVO queryWalletRecordBySerialNo(String serialNo) {

        StarNftWalletRecord record = queryWalletRecordPO(serialNo);

        if (null == record) {
            return null;
        }
        return walletRecordToVO(record);
    }

    private WalletRecordVO walletRecordToVO(StarNftWalletRecord record) {
        return WalletRecordVO.builder().recordSn(record.getRecordSn())
                .outTradeNo(record.getOutTradeNo())
                .fromUid(record.getFromUid())
                .payChannel(record.getPayChannel())
                .payTime(record.getPayTime())
                .payStatus(record.getPayStatus())
                .toUid(record.getToUid())
                .remark(record.getRemark())
                .tsMoney(record.getTsMoney())
                .tsType(record.getTsType().intValue()).build();
    }

    private StarNftWalletRecord queryWalletRecordPO(String serialNo) {
        StarNftWalletRecord request = new StarNftWalletRecord();
        request.setRecordSn(serialNo);


        List<StarNftWalletRecord> starNftWalletRecords = walletRecordMapper.selectByLimit(request);
        if (starNftWalletRecords.size() > 1) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR);
        }
        if (CollectionUtil.isEmpty(starNftWalletRecords)) {
            return null;
        }

        return starNftWalletRecords.get(0);
    }


//    private Boolean recharge(NftWallet nftWallet){
//
//    }
}
