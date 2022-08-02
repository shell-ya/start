package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.AirdropThemeRecord;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.StarNftUserTheme;
import com.starnft.star.business.domain.dto.AirdropRecordDto;
import com.starnft.star.business.domain.dto.RecordItem;
import com.starnft.star.business.mapper.AirdropThemeRecordMapper;
import com.starnft.star.business.mapper.StarNftThemeNumberMapper;
import com.starnft.star.business.mapper.StarNftUserThemeMapper;
import com.starnft.star.business.service.IAirdropThemeRecordService;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.common.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AirdropThemeRecordServiceImpl implements IAirdropThemeRecordService {

    @Resource
    private AirdropThemeRecordMapper recordMapper;
    @Resource
    private StarNftThemeNumberMapper themeNumberMapper;
    @Resource
    private StarNftUserThemeMapper userThemeMapper;
    @Resource
    RedisUtil redisUtil;
    private final HashMap<Long,Integer> priorityTheme = new HashMap<>();

    @PostConstruct
    private void initPriority(){
        priorityTheme.put(1000489186785472512L,3);
        priorityTheme.put(1000490621916909568L,2);
        priorityTheme.put(1000491126767976448L,1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserAirdrop(AirdropThemeRecord record) {

        try{
            boolean airdropSuccess = createAirdropRecord(record);
            boolean numberSuccess = updateThemeNumber(record);
            boolean userTheme = createUserTheme(record);

            Integer orDefault = priorityTheme.get(record.getSeriesThemeInfoId());
            if (Objects.nonNull(orDefault)){
                redisUtil.hincr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(),record.getUserId().toString(),orDefault);
            }
            return airdropSuccess & numberSuccess & userTheme;
        }catch (Exception e){
            log.error("空投异常",e);
            throw new StarException();
        }
    }

    @Override
    public boolean addUserAirdropList(List<AirdropRecordDto> recordList) {

        List<AirdropThemeRecord> airdropThemeRecords = new ArrayList<>();
        for (AirdropRecordDto dto :
                recordList) {
            List<RecordItem> recordItems = dto.getRecordItems();
            for (RecordItem recordItem :
                    recordItems) {
                List<AirdropThemeRecord> collect = recordItem.getSeriesThemeId().stream().map(item -> createAirdropThemeRecord(dto.getUserId(), dto.getAirdropType(), recordItem.getSeriesId(), recordItem.getSeriesThemeInfoId(), item)).collect(Collectors.toList());
                airdropThemeRecords.addAll(collect);
            }
        }

        long successNum = airdropThemeRecords.stream().map(this::addUserAirdrop).filter(Boolean.TRUE::equals).count();
//        long successNum = recordList.stream().map(this::addUserAirdrop).filter(Boolean.TRUE::equals).count();
//
        return successNum == recordList.size();
    }

    private AirdropThemeRecord createAirdropThemeRecord(Long userId, Integer airdropType, Long seriesId, Long seriesThemeInfoId, Long item) {
        AirdropThemeRecord airdropThemeRecord = new AirdropThemeRecord();
        airdropThemeRecord.setUserId(userId);
        airdropThemeRecord.setAirdropType(airdropType);
        airdropThemeRecord.setSeriesId(seriesId);
        airdropThemeRecord.setSeriesThemeInfoId(seriesThemeInfoId);
        airdropThemeRecord.setSeriesThemeId(item);
        return airdropThemeRecord;
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

    /**
     * 单个用户 多个藏品
     * 多个用户 多个藏品
     */
    public void airdropProcess(AirdropRecordDto airdropRecordDto){

    }
}
