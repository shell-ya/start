package com.starnft.star.domain.compose.service.impl;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.domain.compose.model.dto.ComposeRecordDTO;
import com.starnft.star.domain.compose.model.dto.ComposeUserArticleNumberDTO;
import com.starnft.star.domain.compose.repository.IComposeManageRepository;
import com.starnft.star.domain.compose.service.IComposeManageService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ComposeManageServiceImpl implements IComposeManageService {
    @Resource
    IComposeManageRepository composeManageRepository;
    @Resource
    Map<StarConstants.Ids, IIdGenerator> idsIIdGeneratorMap;
    @Override
    public List<ComposeUserArticleNumberDTO> queryUserArticleNumberInfoByNumberIds(Long userId, List<Long> sourceIds, UserNumberStatusEnum purchased) {
        return composeManageRepository.queryUserArticleNumberInfoByNumberIds(userId,sourceIds,purchased);
    }

    @Override
    public Boolean composeModifyUserNumberStatus(Long userId, List<Long> userNumberIds, UserNumberStatusEnum purchased, UserNumberStatusEnum destroy, NumberStatusEnum currentStatusEnum, NumberStatusEnum statusEnum, Boolean isDeleted) {
        return composeManageRepository.composeModifyUserNumberStatus(userId,userNumberIds,purchased,destroy,currentStatusEnum,statusEnum,isDeleted);
    }

    @Override
    public Boolean saveComposeRecord(ComposeRecordDTO composeRecordDTO) {
        composeRecordDTO.setId(idsIIdGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId());
        return composeManageRepository.insertComposeRecord(composeRecordDTO);
    }

    @Override
    public List<ComposeUserArticleNumberDTO> queryUserArticleNumberInfoBySeriesNumberIds(Long userId, List<Long> sourceIds, UserNumberStatusEnum purchased) {
        return composeManageRepository.queryUserArticleNumberInfoBySeriesNumberIds(userId,sourceIds,purchased);
    }
}
