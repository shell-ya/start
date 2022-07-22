package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.AirdropThemeRecord;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.StarNftUserTheme;
import com.starnft.star.business.mapper.AirdropThemeRecordMapper;
import com.starnft.star.business.mapper.StarNftThemeNumberMapper;
import com.starnft.star.business.mapper.StarNftUserThemeMapper;
import com.starnft.star.business.service.IAirdropThemeRecordService;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.SnowflakeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class AirdropThemeRecordServiceImpl implements IAirdropThemeRecordService {

    @Resource
    private AirdropThemeRecordMapper recordMapper;
    @Resource
    private StarNftThemeNumberMapper themeNumberMapper;
    @Resource
    private StarNftUserThemeMapper userThemeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserAirdrop(AirdropThemeRecord record) {

        try{
            boolean airdropSuccess = createAirdropRecord(record);
            boolean numberSuccess = updateThemeNumber(record);
            boolean userTheme = createUserTheme(record);
            return airdropSuccess & numberSuccess & userTheme;
        }catch (Exception e){
            log.error("空投异常",e);
            throw new StarException();
        }
    }

    @Override
    public boolean addUserAirdropList(List<AirdropThemeRecord> recordList) {
        long successNum = recordList.stream().map(this::addUserAirdrop).filter(Boolean.TRUE::equals).count();

        return successNum == recordList.size();
    }

    private boolean createUserTheme(AirdropThemeRecord record){
        StarNftUserTheme userTheme = new StarNftUserTheme();

        userTheme.setUserId(record.getUserId().toString());
        userTheme.setCreateAt(new Date());
        userTheme.setCreateBy(record.getUserId().toString());
        userTheme.setSeriesThemeId(record.getSeriesThemeId());
        userTheme.setSeriesThemeInfoId(record.getSeriesThemeInfoId());
        userTheme.setSeriesId(record.getSeriesId());

        userTheme.setIsDelete(0);
        userTheme.setSource(1);
        userTheme.setStatus(0);

        return userThemeMapper.insert(userTheme) == 1;

    }

    private boolean updateThemeNumber(AirdropThemeRecord record){
        StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
        starNftThemeNumber.setSeriesThemeInfoId(record.getSeriesThemeInfoId());
        starNftThemeNumber.setId(record.getSeriesThemeId());
        starNftThemeNumber.setOwnerBy(record.getUserId().toString());
        starNftThemeNumber.setStatus(0);
        return themeNumberMapper.updateStarNftThemeNumber(starNftThemeNumber) == 1;
    }

    private boolean createAirdropRecord(AirdropThemeRecord record){

        String airdropSn = "AD"
                .concat(String.valueOf(SnowflakeWorker.generateId()));
//        long id = idsIIdGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId();
        record.setAirdropSn(airdropSn);
        record.setCreatedAt(new Date());
        record.setCreatedBy(record.getUserId().toString());
        record.setIsDeleted(0);
        return recordMapper.insert(record) == 1;
    }
}
