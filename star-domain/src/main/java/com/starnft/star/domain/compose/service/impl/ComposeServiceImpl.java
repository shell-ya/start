package com.starnft.star.domain.compose.service.impl;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.model.res.ComposeCategoryRes;
import com.starnft.star.domain.compose.model.res.ComposePrizeRes;
import com.starnft.star.domain.compose.model.res.ComposeRes;
import com.starnft.star.domain.compose.repository.IComposeCategoryRepository;
import com.starnft.star.domain.compose.repository.IComposePrizeRepository;
import com.starnft.star.domain.compose.repository.IComposeRepository;
import com.starnft.star.domain.compose.service.IComposeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ComposeServiceImpl  implements IComposeService {
    @Resource
    IComposeRepository iComposeRepository;
    @Resource
    IComposeCategoryRepository composeCategoryRepository;
    @Resource
    IComposePrizeRepository composePrizeRepository;
    @Override
    public ResponsePageResult<ComposeRes> composeList(RequestConditionPage<ComposeReq> conditionPage) {
        return iComposeRepository.queryComposePageByCondition(conditionPage);
    }

    @Override
    public ComposeRes composeDetails(Long id) {
        return iComposeRepository.queryComposeById(id);
    }

    @Override
    public List<ComposeCategoryRes> composeCategory(Long id) {
        return composeCategoryRepository.queryComposeCategoryByComposeId(id);
    }

    @Override
    public ComposeCategoryRes composeCategoryByCategoryId(Long id) {
        return composeCategoryRepository.composeCategoryByCategoryId(id);
    }
    @Override
    public List<ComposePrizeRes> composePrizeByComposeId(Long id) {
        return composePrizeRepository.queryComposePrizeByComposeId(id);
    }
}
