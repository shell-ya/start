package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.compose.model.res.ComposeCategoryRes;
import com.starnft.star.domain.compose.repository.IComposeCategoryRepository;
import com.starnft.star.infrastructure.entity.compose.StarNftComposeCategory;
import com.starnft.star.infrastructure.mapper.compose.StarNftComposeCategoryMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
@Repository
public class ComposeCategoryRepository implements IComposeCategoryRepository {
    @Resource
    StarNftComposeCategoryMapper starNftComposeCategoryMapper;
    @Override
    public List<ComposeCategoryRes> queryComposeCategoryByComposeId(Long composeId) {
        Assert.notNull(composeId,()->new StarException("id不能为空"));
        QueryWrapper<StarNftComposeCategory> wrapper = new QueryWrapper<>();
        wrapper.eq(StarNftComposeCategory.COL_COMPOSE_ID,composeId);
        wrapper.eq(StarNftComposeCategory.COL_IS_DELETED,Boolean.FALSE);
        List<StarNftComposeCategory> starNftComposeCategories = starNftComposeCategoryMapper.selectList(wrapper);
        return  BeanColverUtil.colverList(starNftComposeCategories,ComposeCategoryRes.class);
    }

    @Override
    public ComposeCategoryRes composeCategoryByCategoryId(Long id) {
        StarNftComposeCategory starNftComposeCategory = starNftComposeCategoryMapper.selectById(id);
        return BeanColverUtil.colver(starNftComposeCategory,ComposeCategoryRes.class);
    }
}
