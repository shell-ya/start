package com.starnft.star.domain.compose.repository;

import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.domain.compose.model.dto.ComposeRecordDTO;
import com.starnft.star.domain.compose.model.dto.ComposeUserArticleNumberDTO;

import java.util.List;

public interface IComposeManageRepository {
    List<ComposeUserArticleNumberDTO> queryUserArticleNumberInfoByNumberIds(Long userId, List<Long> sourceIds, UserNumberStatusEnum purchased);

    Boolean composeModifyUserNumberStatus(Long userId, List<Long> userNumberIds, UserNumberStatusEnum purchased, UserNumberStatusEnum destroy, NumberStatusEnum currentStatusEnum, NumberStatusEnum statusEnum, Boolean isDeleted);
    public  Boolean insertComposeRecord(ComposeRecordDTO composeRecord);
}
