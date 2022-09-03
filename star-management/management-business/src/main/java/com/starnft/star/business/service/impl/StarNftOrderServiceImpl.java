package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.NftWallet;
import com.starnft.star.business.domain.StarNftOrder;
import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.dto.WalletRecordReq;
import com.starnft.star.business.mapper.NftWalletMapper;
import com.starnft.star.business.mapper.StarNftOrderMapper;
import com.starnft.star.business.mapper.StarNftWalletLogMapper;
import com.starnft.star.business.mapper.StarNftWalletRecordMapper;
import com.starnft.star.business.service.IStarNftOrderService;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.SnowflakeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author shellya
 * @date 2022-05-28
 */
@Service
public class StarNftOrderServiceImpl implements IStarNftOrderService
{
    @Autowired
    private StarNftOrderMapper starNftOrderMapper;
    @Resource
    private StarNftWalletRecordMapper recordMapper;
    @Resource
    private NftWalletMapper walletMapper;
    @Resource
    private StarNftWalletLogMapper logMapper;
    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public StarNftOrder selectStarNftOrderById(Long id)
    {
        return starNftOrderMapper.selectStarNftOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param starNftOrder 订单
     * @return 订单
     */
    @Override
    public List<StarNftOrder> selectStarNftOrderList(StarNftOrder starNftOrder)
    {
        return starNftOrderMapper.selectStarNftOrderList(starNftOrder);
    }

    /**
     * 新增订单
     *
     * @param starNftOrder 订单
     * @return 结果
     */
    @Override
    public int insertStarNftOrder(StarNftOrder starNftOrder)
    {
        return starNftOrderMapper.insertStarNftOrder(starNftOrder);
    }

    /**
     * 修改订单
     *
     * @param starNftOrder 订单
     * @return 结果
     */
    @Override
    public int updateStarNftOrder(StarNftOrder starNftOrder)
    {
        return starNftOrderMapper.updateStarNftOrder(starNftOrder);
    }

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteStarNftOrderByIds(Long[] ids)
    {
        return starNftOrderMapper.deleteStarNftOrderByIds(ids);
    }

    /**
     * 删除订单信息
     *
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteStarNftOrderById(Long id)
    {
        return starNftOrderMapper.deleteStarNftOrderById(id);
    }

    @Override
    public Boolean refundOrder(String orderSn) {
        //判断是市场订单还是首发订单
        //市场订单 付款账户退回余额 收款账户减去余额 藏品返回收款账户 新赠一条交易记录 两条变化记录
        //首发订单 付款账户退回余额 新增一条交易记录、变化记录 回收藏品
        //生成退款记录
        StarNftWalletRecord record = queryRecord(orderSn);
        //确定藏品id
        StarNftOrder order = queryOrder(orderSn);
        WalletRecordReq walletRecordReq = this.walletRecordInit(record);

        Long toUid = record.getToUid();
        Long fromUid = record.getFromUid();
        NftWallet nftWallet = walletMapper.selectNftWalletByUid(fromUid);

        return true;

    }

    private StarNftOrder queryOrder(String orderSn){
        List<StarNftOrder> starNftOrders = starNftOrderMapper.queryStarNftOrder(orderSn);

        if (starNftOrders.size() > 1) throw new StarException(StarError.SYSTEM_ERROR);

        return starNftOrders.get(0);
    }

    private StarNftWalletRecord queryRecord(String recordSn){
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
