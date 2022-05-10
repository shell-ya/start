package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.domain.wallet.service.WalletConfig;
import com.starnft.star.infrastructure.entity.wallet.StarNftWalletConfig;
import com.starnft.star.infrastructure.entity.wallet.dto.WalletConfigDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftWalletConfigMapper {

    /**
     * 插入钱包配置【后台】TODO 插入时需调用刷新缓存配置
     *
     * @param walletConfigDTO 钱包配置
     * @return 插入结果
     * @see WalletConfig manualRefresh()
     */
    Integer walletConfigRegister(WalletConfigDTO walletConfigDTO);

    List<StarNftWalletConfig> loadAllWalletConfig();
}
