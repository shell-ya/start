package com.starnft.star.infrastructure.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.domain.order.repository.BuyNum;
import com.starnft.star.infrastructure.entity.order.StarNftOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StarNftOrderMapper extends BaseMapper<StarNftOrder> {

    Integer createOrder(StarNftOrder starNftOrder);

    List<BuyNum> queryBuyNum();

    Integer queryUserBuyBox(String uid);
}
