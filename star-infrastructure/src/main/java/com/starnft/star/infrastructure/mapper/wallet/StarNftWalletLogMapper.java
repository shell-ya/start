package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.infrastructure.entity.wallet.StarNftWalletLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftWalletLogMapper {

    Integer createChargeLog(StarNftWalletLog starNftWalletLog);

    List<StarNftWalletLog> readUsersLogs(Long uid);
}
