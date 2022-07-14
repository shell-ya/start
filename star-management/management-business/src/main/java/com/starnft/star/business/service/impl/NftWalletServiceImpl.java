package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.NftWallet;
import com.starnft.star.business.mapper.NftWalletMapper;
import com.starnft.star.business.service.INftWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
