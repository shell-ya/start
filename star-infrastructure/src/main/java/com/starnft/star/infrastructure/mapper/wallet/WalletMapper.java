package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.infrastructure.entity.wallet.Wallet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletMapper {

    Wallet queryWalletByParams(Wallet wallet);

    Integer createWallet(Wallet wallet);

    Integer updateWallet(Wallet wallet);
}
