package com.starnft.star.infrastructure.mapper.wallet;

import com.starnft.star.infrastructure.entity.wallet.StarNftBankRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftBankRelationMapper {

    List<StarNftBankRelation> queryByCondition(StarNftBankRelation starNftBankRelation);

    Integer insert(StarNftBankRelation starNftBankRelation);

}
