package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.NftWallet;

import java.util.List;

/**
 * 钱包Mapper接口
 *
 * @author shellya
 * @date 2022-06-08
 */
public interface NftWalletMapper
{
    /**
     * 查询钱包
     *
     * @param id 钱包主键
     * @return 钱包
     */
    public NftWallet selectNftWalletById(Long id);

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
     * 删除钱包
     *
     * @param id 钱包主键
     * @return 结果
     */
    public int deleteNftWalletById(Long id);

    /**
     * 批量删除钱包
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNftWalletByIds(Long[] ids);

    NftWallet selectNftWalletByUid(Long id);
}
