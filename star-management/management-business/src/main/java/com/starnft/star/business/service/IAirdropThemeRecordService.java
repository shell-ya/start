package com.starnft.star.business.service;

import com.starnft.star.business.domain.AirdropThemeRecord;

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
    boolean addUserAirdropList(List<AirdropThemeRecord> recordList);
}
