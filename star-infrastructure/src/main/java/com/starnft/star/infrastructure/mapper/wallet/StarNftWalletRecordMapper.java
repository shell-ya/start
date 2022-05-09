package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.infrastructure.entity.wallet.StarNftWalletRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StarNftWalletRecordMapper {

    Integer createWalletRecord(StarNftWalletRecord starNftWalletRecord);

}
