package com.starnft.star.infrastructure.mapper.wallet;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.infrastructure.entity.wallet.StarNftWithdrawApply;
import org.apache.ibatis.annotations.Mapper;

/**
 * 提现申请表;(star_nft_withdraw_apply)表数据库访问层
 * @author : Ryan
 * @date : 2022-5-21
 */
@Mapper
public interface StarNftWithdrawApplyMapper extends BaseMapper<StarNftWithdrawApply> {

    boolean insertRecord(StarNftWithdrawApply apply);

}
