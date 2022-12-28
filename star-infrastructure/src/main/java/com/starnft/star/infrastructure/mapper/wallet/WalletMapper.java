package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.infrastructure.entity.wallet.Wallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WalletMapper {

    Wallet queryWalletByParams(Wallet wallet);

    Integer createWallet(Wallet wallet);

    Integer updateWallet(Wallet wallet);

    List<Wallet> selectAllWallet();

    void updateUserThWId(@Param("uid") Long uid, @Param("address") String address);
}
