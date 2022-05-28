package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.infrastructure.entity.wallet.StarNftBankRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarNftBankRelationMapper {

    List<StarNftBankRelation> queryByCondition(StarNftBankRelation starNftBankRelation);

    Integer insert(StarNftBankRelation starNftBankRelation);

    Integer delete(@Param("uid") Long uid, @Param("cardNo") String cardNo);

    Integer update(StarNftBankRelation starNftBankRelation);
}
