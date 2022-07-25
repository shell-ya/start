package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.model.res.ComposeRes;
import com.starnft.star.domain.compose.repository.IComposeRepository;
import com.starnft.star.infrastructure.entity.compose.StarNftCompose;
import com.starnft.star.infrastructure.mapper.compose.StarNftComposeMapper;
import com.starnft.star.infrastructure.tools.PageHelperInterface;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ComposeRepository implements IComposeRepository, PageHelperInterface {
    @Resource
    StarNftComposeMapper starNftComposeMapper;

    @Override
    public ResponsePageResult<ComposeRes> queryComposePageByCondition(RequestConditionPage<ComposeReq> conditionPage) {
        StarNftCompose starNftCompose = new StarNftCompose();
        if (Objects.nonNull(conditionPage.getCondition())) {
            starNftCompose = BeanColverUtil.colver(conditionPage.getCondition(), StarNftCompose.class);
        }
        starNftCompose.setIsDeleted(Boolean.FALSE);
        StarNftCompose finalStarNftCompose = starNftCompose;
        PageInfo<StarNftCompose> result = PageHelper
                .startPage(conditionPage.getPage(), conditionPage.getSize())
                .doSelectPageInfo(() -> {
                            starNftComposeMapper
                                    .selectList(new QueryWrapper<StarNftCompose>()
                                            .setEntity(finalStarNftCompose));
                        }
                );
        List<ComposeRes> collect = result.getList().stream().map(item -> {
            ComposeRes composeRes = new ComposeRes();
            composeRes.setComposeRemark(item.getComposeRemark());
            composeRes.setComposeName(item.getComposeName());
            composeRes.setComposeStatus(item.getComposeStatus());
            composeRes.setComposeImages(item.getComposeImages());
            composeRes.setEndAt(item.getEndAt());
            composeRes.setStartedAt(item.getStartedAt());
            composeRes.setId(item.getId());
            return composeRes;
        }).collect(Collectors.toList());
        return listReplace(result,collect);
    }

    @Override
    public ComposeRes queryComposeById(Long id) {
        StarNftCompose starNftCompose = starNftComposeMapper.selectById(id);
        return  BeanColverUtil.colver(starNftCompose,ComposeRes.class);
    }
}
