package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.compose.model.res.ComposePrizeRes;
import com.starnft.star.domain.compose.repository.IComposePrizeRepository;
import com.starnft.star.infrastructure.entity.compose.StarNftComposePrize;
import com.starnft.star.infrastructure.mapper.compose.StarNftComposePrizeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ComposePrizeRepository implements IComposePrizeRepository
{
    @Resource
    StarNftComposePrizeMapper starNftComposePrizeMapper;

    @Override
    public List<ComposePrizeRes> queryComposePrizeByComposeId(Long composeId) {
        QueryWrapper<StarNftComposePrize> wrapper = new QueryWrapper<>();
        wrapper.eq(StarNftComposePrize.COL_COMPOSE_ID,composeId);
        wrapper.eq(StarNftComposePrize.COL_IS_DELETED,Boolean.FALSE);
        List<StarNftComposePrize> starNftComposePrizes = starNftComposePrizeMapper.selectList(wrapper);
        return BeanColverUtil.colverList(starNftComposePrizes,ComposePrizeRes.class);
    }
}
