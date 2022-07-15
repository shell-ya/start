package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarNftWalletRecord;
import com.starnft.star.business.domain.vo.StarNftReconVo;

import java.util.List;

/**
 * 钱包交易记录Service接口
 *
 * @author shellya
 * @date 2022-06-09
 */
public interface IStarNftWalletRecordService
{
    /**
     * 查询钱包交易记录
     *
     * @param id 钱包交易记录主键
     * @return 钱包交易记录
     */
    public StarNftWalletRecord selectStarNftWalletRecordById(Long id);

    /**
     * 查询钱包交易记录列表
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 钱包交易记录集合
     */
    public List<StarNftWalletRecord> selectStarNftWalletRecordList(StarNftWalletRecord starNftWalletRecord);

    /**
     * 新增钱包交易记录
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 结果
     */
    public int insertStarNftWalletRecord(StarNftWalletRecord starNftWalletRecord);

    /**
     * 修改钱包交易记录
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 结果
     */
    public int updateStarNftWalletRecord(StarNftWalletRecord starNftWalletRecord);

    /**
     * 修改钱包交易记录
     *
     * @param starNftWalletRecord 钱包交易记录
     * @return 结果
     */
    public int updateStarNftWalletRecordByRecordSn(StarNftWalletRecord starNftWalletRecord);

    /**
     * 批量删除钱包交易记录
     *
     * @param ids 需要删除的钱包交易记录主键集合
     * @return 结果
     */
    public int deleteStarNftWalletRecordByIds(Long[] ids);

    /**
     * 删除钱包交易记录信息
     *
     * @param id 钱包交易记录主键
     * @return 结果
     */
    public int deleteStarNftWalletRecordById(Long id);

    /**
     * 更新钱包交易记录状态
     * @param withdrawTradeNo
     * @param payStatus
     * @param sysUserId
     * @return
     */
    public int updateRecordByRecordSn(String withdrawTradeNo, String payStatus, String sysUserId);

    List<StarNftReconVo> selectUserRecord(Long userId);
}
