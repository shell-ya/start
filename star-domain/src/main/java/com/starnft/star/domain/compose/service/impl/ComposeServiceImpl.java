package com.starnft.star.domain.compose.service.impl;

import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.domain.compose.model.req.ComposeReq;
import com.starnft.star.domain.compose.repository.IComposeRepository;
import com.starnft.star.domain.compose.service.IComposeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ComposeServiceImpl  implements IComposeService {
    @Resource
    IComposeRepository iComposeRepository;
    @Override
    public Object composeList(RequestConditionPage<ComposeReq> conditionPage) {
        return iComposeRepository.queryComposePageByCondition(conditionPage);
    }
}
