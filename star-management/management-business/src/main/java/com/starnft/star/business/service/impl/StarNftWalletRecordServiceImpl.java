package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.vo.StarNftReconVo;
import com.starnft.star.business.mapper.StarNftWalletRecordMapper;
import com.starnft.star.business.service.IStarNftWalletRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钱包交易记录Service业务层处理
 *
 * @author shellya
 * @date 2022-06-09
 */
@Service
public class StarNftWalletRecordServiceImpl implements IStarNftWalletRecordService
{
    @Autowired
    private StarNftWalletRecordMapper starNftWalletRecordMapper;

    /**
     * 查询钱包交易记录
     *
     * @param id 钱包交易记录主键
     * @return 钱包交易记录
     */
    @Override
    public StarNftWalletRecord selectStarNftWalletRecordById(Long id)
    {
        return starNftWalletRecordMapper.selectStarNftWalletRecordById(id);
    }

    /**
     * 查询钱包交易记录列表
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 钱包交易记录
     */
    @Override
    public List<StarNftWalletRecord> selectStarNftWalletRecordList(StarNftWalletRecord starNftWalletRecord)
    {
        return starNftWalletRecordMapper.selectStarNftWalletRecordList(starNftWalletRecord);
    }

    /**
     * 新增钱包交易记录
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 结果
     */
    @Override
    public int insertStarNftWalletRecord(StarNftWalletRecord starNftWalletRecord)
    {
        return starNftWalletRecordMapper.insertStarNftWalletRecord(starNftWalletRecord);
    }

    /**
     * 修改钱包交易记录
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 结果
     */
    @Override
    public int updateStarNftWalletRecord(StarNftWalletRecord starNftWalletRecord)
    {
        return starNftWalletRecordMapper.updateStarNftWalletRecord(starNftWalletRecord);
    }

    /**
     * 修改钱包交易记录
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 结果
     */
    @Override
    public int updateStarNftWalletRecordByRecordSn(StarNftWalletRecord starNftWalletRecord)
    {
        return starNftWalletRecordMapper.updateStarNftWalletRecordByRecordSn(starNftWalletRecord);
    }


    /**
     * 批量删除钱包交易记录
     *
     * @param ids 需要删除的钱包交易记录主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWalletRecordByIds(Long[] ids)
    {
        return starNftWalletRecordMapper.deleteStarNftWalletRecordByIds(ids);
    }

    /**
     * 删除钱包交易记录信息
     *
     * @param id 钱包交易记录主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWalletRecordById(Long id)
    {
        return starNftWalletRecordMapper.deleteStarNftWalletRecordById(id);
    }

    @Override
    public int updateRecordByRecordSn(String withdrawTradeNo, String payStatus, String sysUserId) {
        return starNftWalletRecordMapper.updateRecordStatusByRecordSn(withdrawTradeNo,payStatus,sysUserId);
    }

    @Override
    public List<StarNftReconVo> selectUserRecord(Long userId) {
        return starNftWalletRecordMapper.selectUserRecord(userId);
    }
}
