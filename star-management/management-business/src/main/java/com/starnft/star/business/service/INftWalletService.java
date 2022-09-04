package com.starnft.star.business.service;

import com.starnft.star.business.domain.NftWallet;
import com.starnft.star.business.domain.vo.RechargeVO;

import java.util.List;

/**
 * 钱包Service接口
 *
 * @author shellya
 * @date 2022-06-08
 */
public interface INftWalletService
{
    /**
     * 查询钱包
     *
     * @param id 钱包主键
     * @return 钱包
     */
    public NftWallet selectNftWalletByUid(Long id);

    /**
     * 查询钱包列表
     *
     * @param nftWallet 钱包
     * @return 钱包集合
     */
    public List<NftWallet> selectNftWalletList(NftWallet nftWallet);

    /**
     * 新增钱包
     *
     * @param nftWallet 钱包
     * @return 结果
     */
    public int insertNftWallet(NftWallet nftWallet);

    /**
     * 修改钱包
     *
     * @param nftWallet 钱包
     * @return 结果
     */
    public int updateNftWallet(NftWallet nftWallet);

    /**
     * 批量删除钱包
     *
     * @param ids 需要删除的钱包主键集合
     * @return 结果
     */
    public int deleteNftWalletByIds(Long[] ids);

    /**
     * 删除钱包信息
     *
     * @param id 钱包主键
     * @return 结果
     */
    public int deleteNftWalletById(Long id);


    public Boolean walletRecharge(RechargeVO rechargeVO);

    public Boolean refundOrder(String orderSn);
}
