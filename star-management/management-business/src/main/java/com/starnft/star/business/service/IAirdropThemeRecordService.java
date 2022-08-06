package com.starnft.star.business.service;

import com.starnft.star.business.domain.AirdropThemeRecord;
import com.starnft.star.business.domain.dto.AirdropRecordDto;
import com.starnft.star.business.domain.dto.WithRuleDto;

import java.util.List;

public interface IAirdropThemeRecordService {
    /**
     * 空投 单用户  单藏品
     * @param record
     * @return
     */
    boolean addUserAirdrop(AirdropThemeRecord record);

    /**
     * 单用户 多空投
     * @param recordList
     * @return
     */
    boolean addUserAirdropList(List<AirdropRecordDto> recordList);

    Boolean zhuyuliu(AirdropRecordDto dto);

    String airdropProcess(List<AirdropRecordDto> dtoList);

    Long addWithRule(WithRuleDto dto);

    String importWithRule(List<WithRuleDto> dtos);

}
