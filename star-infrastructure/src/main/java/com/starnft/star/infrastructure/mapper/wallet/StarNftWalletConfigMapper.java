package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.infrastructure.entity.wallet.StarNftWalletConfig;
import com.starnft.star.infrastructure.entity.wallet.dto.WalletConfigDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftWalletConfigMapper {

    /**
     * 插入钱包配置【后台】
     * @param walletConfigDTO 钱包配置
     * @return 插入结果
     */
    Integer walletConfigRegister(WalletConfigDTO walletConfigDTO);

    List<StarNftWalletConfig> loadAllWalletConfig();
}
