package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.domain.wallet.model.req.TransactionRecordQueryReq;
import com.starnft.star.infrastructure.entity.wallet.StarNftWalletRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftWalletRecordMapper {

    Integer createWalletRecord(StarNftWalletRecord starNftWalletRecord);

    List<StarNftWalletRecord> selectByLimit(StarNftWalletRecord starNftWalletRecord);

    Integer updateRecord(StarNftWalletRecord starNftWalletRecord);

    List<StarNftWalletRecord> queryRecordOnCondition(TransactionRecordQueryReq queryReq);
}
