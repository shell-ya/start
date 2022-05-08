package com.starnft.star.domain.wallet.repository;

import com.starnft.star.domain.wallet.model.req.RechargeReq;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.req.WalletRecordReq;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.model.vo.WalletVO;

import java.util.List;

public interface IWalletRepository {

    /**
     *  根据uid获取钱包信息
     * @param walletInfoReq 获取钱包信息请求
     * @return WalletVO
     */
    WalletVO queryWallet(WalletInfoReq walletInfoReq);

    /**
     * 创建钱包
     * @param walletInfoReq 钱包信息
     * @return 钱包信息vo
     */
    WalletVO createWallet(WalletInfoReq walletInfoReq);

    /**
     *  记录充值变动
     * @param rechargeReq 充值变化请求
     * @return
     */
    Integer createWalletLog(RechargeReq rechargeReq);

    /**
     * 创建交易记录
     * @param walletRecordReq 交易记录
     * @return 执行结果
     */
    Integer createWalletRecord(WalletRecordReq walletRecordReq);

    /**
     * 获取钱包配置
     * @return 钱包配置列表
     */
    List<WalletConfigVO> loadAllWalletConfig();
}
