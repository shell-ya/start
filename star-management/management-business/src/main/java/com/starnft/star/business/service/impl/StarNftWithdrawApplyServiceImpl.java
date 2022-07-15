package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.NftWallet;
import com.starnft.star.business.domain.StarNftWalletLog;
import com.starnft.star.business.domain.StarNftWithdrawApply;
import com.starnft.star.business.domain.vo.WithDrawDetail;
import com.starnft.star.business.mapper.StarNftWithdrawApplyMapper;
import com.starnft.star.business.service.INftWalletService;
import com.starnft.star.business.service.IStarNftWalletLogService;
import com.starnft.star.business.service.IStarNftWalletRecordService;
import com.starnft.star.business.service.IStarNftWithdrawApplyService;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.core.domain.model.LoginUser;
import com.starnft.star.common.exception.ServiceException;
import com.starnft.star.common.utils.DateUtils;
import com.starnft.star.common.utils.redis.RedisLockUtils;
import com.starnft.star.common.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 提现申请Service业务层处理
 *
 * @author shellya
 * @date 2022-05-28
 */
@Service
public class StarNftWithdrawApplyServiceImpl implements IStarNftWithdrawApplyService
{
    @Autowired
    private StarNftWithdrawApplyMapper starNftWithdrawApplyMapper;
    @Autowired
    private INftWalletService nftWalletService;
    @Autowired
    private IStarNftWalletLogService starNftWalletLogService;
    @Autowired
    private IStarNftWalletRecordService starNftWalletRecordService;
    @Autowired
    private RedisLockUtils redisLockUtils;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询提现申请
     *
     * @param id 提现申请主键
     * @return 提现申请
     */
    @Override
    public StarNftWithdrawApply selectStarNftWithdrawApplyById(Long id)
    {
        return starNftWithdrawApplyMapper.selectStarNftWithdrawApplyById(id);
    }

    /**
     * 查询提现申请列表
     *
     * @param starNftWithdrawApply 提现申请
     * @return 提现申请
     */
    @Override
    public List<StarNftWithdrawApply> selectStarNftWithdrawApplyList(StarNftWithdrawApply starNftWithdrawApply)
    {
        return starNftWithdrawApplyMapper.selectStarNftWithdrawApplyList(starNftWithdrawApply);
    }

    /**
     * 新增提现申请
     *
     * @param starNftWithdrawApply 提现申请
     * @return 结果
     */
    @Override
    public int insertStarNftWithdrawApply(StarNftWithdrawApply starNftWithdrawApply)
    {
        return starNftWithdrawApplyMapper.insertStarNftWithdrawApply(starNftWithdrawApply);
    }

    /**
     * 修改提现申请
     *
     * @param starNftWithdrawApply 提现申请
     * @return 结果
     */
    @Override
    @Transactional
    public int updateStarNftWithdrawApply(StarNftWithdrawApply starNftWithdrawApply, LoginUser loginUser)
    {

        NftWallet nftWallet = nftWalletService.selectNftWalletByUid(starNftWithdrawApply.getWithdrawUid());

        //钱包交易状态中锁
        String isTransactionKey = RedisLockUtils.REDIS_LOCK_PREFIX + String.format(RedisKey.REDIS_TRANSACTION_ING.getKey(),
                new StringBuffer(String.valueOf(starNftWithdrawApply.getWithdrawUid())).append(nftWallet.getwId()));
        //判断当前是否有其他交易正在进行
        if (redisUtil.hasKey(isTransactionKey)) {
            throw new ServiceException("有其他交易");
        }
        try{
            if (redisLockUtils.lock(isTransactionKey, RedisKey.REDIS_TRANSACTION_ING.getTime())) {
                starNftWithdrawApply.setModifiedBy(loginUser.getUserId().toString());
                starNftWithdrawApply.setModifiedAt(new Date());
                //成功 删除钱包表冻结余额 在钱包交易记录表添加提交记录
                if (1 == starNftWithdrawApply.getApplyStatus()){

                    //提现通过时间
                    starNftWithdrawApply.setApplyPassTime(DateUtils.dateTimeNow());
                    nftWallet.setFrozen(0);
                    nftWallet.setFrozenFee(BigDecimal.ZERO);
                    nftWalletService.updateNftWallet(nftWallet);

                    StarNftWalletLog starNftWalletLog = new StarNftWalletLog();
                    starNftWalletLog.setUid(starNftWithdrawApply.getWithdrawUid());
                    starNftWalletLog.setwId(nftWallet.getwId());
                    starNftWalletLog.setChannel(starNftWithdrawApply.getWithdrawChannel());
                    starNftWalletLog.setCreatedAt(new Date());
                    starNftWalletLog.setCreatedBy(loginUser.getUserId().toString());
                    starNftWalletLog.setModifiedAt(new Date());
                    starNftWalletLog.setModifiedBy(loginUser.getUserId().toString());
                    starNftWalletLog.setIsDeleted(0L);
                    starNftWalletLog.setDisplay(0);
                    starNftWalletLog.setBalanceOffset(starNftWithdrawApply.getWithdrawMoney());
                    starNftWalletLog.setCurrentBalance(nftWallet.getBalance());
                    starNftWalletLog.setRecordSn(starNftWithdrawApply.getWithdrawTradeNo());
                    starNftWalletLogService.insertStarNftWalletLog(starNftWalletLog);

//                    StarNftWalletRecord starNftWalletRecord = new StarNftWalletRecord();
//                    starNftWalletRecord.setRecordSn(starNftWithdrawApply.getWithdrawTradeNo());
//                    starNftWalletRecord.setPayStatus(StarConstants.Pay_Status.PAY_SUCCESS.name());
//                    starNftWalletRecord.setModifiedAt(new Date());
//                    starNftWalletRecord.setModifiedBy(loginUser.getUserId().toString());
//                    starNftWalletRecordService.updateStarNftWalletRecordByRecordSn(starNftWalletRecord);
                    starNftWalletRecordService.updateRecordByRecordSn(starNftWithdrawApply.getWithdrawTradeNo(),StarConstants.Pay_Status.PAY_SUCCESS.name(),loginUser.getUserId().toString());
                }else {
                    //失败 钱包表冻结金额回退至余额
                    nftWallet.setFrozen(0);
                    nftWallet.setBalance(nftWallet.getFrozenFee().add(nftWallet.getBalance()));
                    nftWalletService.updateNftWallet(nftWallet);
                    //更新钱包交易记录表
//                    StarNftWalletRecord starNftWalletRecord = new StarNftWalletRecord();
//                    starNftWalletRecord.setRecordSn(starNftWithdrawApply.getWithdrawTradeNo());
//                    starNftWalletRecord.setPayStatus(StarConstants.Pay_Status.PAY_FAILED.name());
//                    starNftWalletRecord.setModifiedAt(new Date());
//                    starNftWalletRecord.setModifiedBy(loginUser.getUserId().toString());
//                    starNftWalletRecordService.updateStarNftWalletRecordByRecordSn(starNftWalletRecord);
                    starNftWalletRecordService.updateRecordByRecordSn(starNftWithdrawApply.getWithdrawTradeNo(),StarConstants.Pay_Status.PAY_FAILED.name(),loginUser.getUserId().toString());
                }


                return starNftWithdrawApplyMapper.updateStarNftWithdrawApply(starNftWithdrawApply);
            }
        }catch (Exception e){
            throw new RuntimeException("修改提现发生异常",e);
        }finally {
            redisLockUtils.unlock(isTransactionKey);
        }
        throw new ServiceException("更新提现失败");

    }

    /**
     * 批量删除提现申请
     *
     * @param ids 需要删除的提现申请主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWithdrawApplyByIds(Long[] ids)
    {
        return starNftWithdrawApplyMapper.deleteStarNftWithdrawApplyByIds(ids);
    }

    /**
     * 删除提现申请信息
     *
     * @param id 提现申请主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWithdrawApplyById(Long id)
    {
        return starNftWithdrawApplyMapper.deleteStarNftWithdrawApplyById(id);
    }

    @Override
    public List<WithDrawDetail> selectStarNftWithdrawDetail(StarNftWithdrawApply starNftWithdrawApply) {
        return starNftWithdrawApplyMapper.selectStarWithDrawDetail(starNftWithdrawApply);
    }
}
