package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarNftWalletLog;

import java.util.List;

/**
 * 钱包变化记录Service接口
 *
 * @author shellya
 * @date 2022-06-09
 */
public interface IStarNftWalletLogService
{
    /**
     * 查询钱包变化记录
     *
     * @param id 钱包变化记录主键
     * @return 钱包变化记录
     */
    public StarNftWalletLog selectStarNftWalletLogById(Long id);

    /**
     * 查询钱包变化记录列表
     *
     * @param starNftWalletLog 钱包变化记录
     * @return 钱包变化记录集合
     */
    public List<StarNftWalletLog> selectStarNftWalletLogList(StarNftWalletLog starNftWalletLog);

    /**
     * 新增钱包变化记录
     *
     * @param starNftWalletLog 钱包变化记录
     * @return 结果
     */
    public int insertStarNftWalletLog(StarNftWalletLog starNftWalletLog);

    /**
     * 修改钱包变化记录
     *
     * @param starNftWalletLog 钱包变化记录
     * @return 结果
     */
    public int updateStarNftWalletLog(StarNftWalletLog starNftWalletLog);

    /**
     * 批量删除钱包变化记录
     *
     * @param ids 需要删除的钱包变化记录主键集合
     * @return 结果
     */
    public int deleteStarNftWalletLogByIds(Long[] ids);

    /**
     * 删除钱包变化记录信息
     *
     * @param id 钱包变化记录主键
     * @return 结果
     */
    public int deleteStarNftWalletLogById(Long id);
}
