package com.starnft.star.business.service.impl;

import com.google.common.collect.Lists;
import com.starnft.star.business.domain.*;
import com.starnft.star.business.domain.dto.AirdropRecordDto;
import com.starnft.star.business.domain.dto.RecordItem;
import com.starnft.star.business.domain.dto.WithRuleDto;
import com.starnft.star.business.mapper.*;
import com.starnft.star.business.service.IAirdropThemeRecordService;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.ServiceException;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.common.utils.StringUtils;
import com.starnft.star.common.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Executable;
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
    private AccountUserMapper accountUserMapper;
    @Resource
    RedisUtil redisUtil;
    @Resource
    private StarNftThemeInfoMapper themeInfoMapper;

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
                redisUtil.hincr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(),record.getUserId().toString(),Long.valueOf(orDefault));
            }
            return airdropSuccess & numberSuccess & userTheme;
        }catch (Exception e){
            log.error("空投异常",e);
            throw new StarException(e.getLocalizedMessage());
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
        starNftThemeNumber.setStatus(1);
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
    @Override
    public String airdropProcess(List<AirdropRecordDto> dtoList){

        if (StringUtils.isNull(dtoList) || dtoList.size() == 0)
        {
            throw new ServiceException("空投数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (AirdropRecordDto dto :
                dtoList) {
            try{
                //验证用户是否存在
                AccountUser accountUser = accountUserMapper.selectUserByAccount(dto.getUserId());
                if (Objects.isNull(accountUser)){
                    failureNum++;
                    failureMsg.append("<br/>用户").append(dto.getUserId()).append("不存在");
                    continue;
                }
                //验证要空投的主题是否存在
                List<RecordItem> recordItems = dto.getRecordItems();
                for (RecordItem item : recordItems) {
                    StarNftThemeInfo themeInfo = themeInfoMapper.selectStarNftThemeInfoById(item.getSeriesThemeInfoId());
                    if (Objects.isNull(themeInfo)){
                        failureNum++;
                        failureMsg.append("<br/>主题").append(item.getSeriesThemeId()).append("不存在");
                    }
                    Long numberId= null;
                    Long themeNumber = null;

                    ArrayList<Serializable> redisNumberList = new ArrayList<>();
                    //检查redis中有此编号 命中换下一个
                    //模糊匹配keys
//                            boolean pool = false;
//                            boolean queue= false;
                    Set<String> poolKeys = redisUtil.keys(String.format(RedisKey.SECKILL_GOODS_STOCK_POOL.getKey(),themeInfo.getId(), "*"));
                    //随机编号池
                    if (!poolKeys.isEmpty()) {
                        for (String key :
                                poolKeys) {
                            Set<Serializable> set = redisUtil.sGet(key);
                            redisNumberList.addAll(set);
//                                    pool = redisUtil.sHasKey(key, themeNumber);
//                                    if (pool) break;
                        }
                    }
                    Set<String> listKeys = redisUtil.keys(String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), themeInfo.getId(), "*"));
                    if (!listKeys.isEmpty()){
                        for (String key :
                                listKeys) {
//                                        queue = redisUtil.hHasKey(key,String.valueOf(themeNumber));
                            ArrayList<Serializable> list = (ArrayList<Serializable>) redisUtil.lGet(key, 0, -1);
                            redisNumberList.addAll(list);
//                                    queue = list.contains(themeNumber.intValue());
//                                    if (queue) break;
                        }
                    }
                    //发送空投数量为0 从数据库中选出一个所属人为空的藏品
                    if (0 == item.getSeriesThemeId().size() || Objects.isNull(item.getSeriesThemeId())){

                        themeNumber = (long) RandomUtil.randomInt(0, themeInfo.getPublishNumber().intValue());
                        while (true){
                            //秒杀编号队列
                            // TODO: 2022/8/3 秒杀库存
                            StarNftThemeNumber starNftThemeNumber = themeNumberMapper.selectOwnerIsNull(item.getSeriesThemeInfoId(), themeNumber,redisNumberList);
                            if (Objects.isNull(starNftThemeNumber)){
                                themeNumber = (long) RandomUtil.randomInt(0, themeInfo.getPublishNumber().intValue());
                            }else {
                                numberId= starNftThemeNumber.getId();
                                break;
                            }
                            //去数据库查询该编号所属人是否为空
                        }
                        item.setSeriesThemeId(Lists.newArrayList(themeNumber));
                    }else {
                        throw new StarException("空投随机编号不传藏品id seriesThemeId");
                    }
                    AirdropThemeRecord airdropThemeRecord = createAirdropThemeRecord(dto.getUserId(), dto.getAirdropType(), item.getSeriesId(), item.getSeriesThemeInfoId(), numberId);
                    try{
                        addUserAirdrop(airdropThemeRecord);
                        successNum++;
                        successMsg.append("<br/>用户").append(dto.getUserId()).append("空投编号").append(themeNumber).append("成功");
                    }catch (Exception e){
                        failureNum++;
                        failureMsg.append("<br/>用户").append(dto.getUserId()).append("空投失败");
                    }
                    // TODO: 2022/8/3
//                    else {
//                        //验证要空投的藏品所属人是否为空
//                        //验证空投藏品在缓存中
//
//                    }

                }

            }catch (Exception e){
                failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条");
                successMsg.insert(0, "导入成功！共 " + successNum + " 条");
                log.error("空投失败",e);
                successMsg.append(failureMsg);
                return successMsg.toString();
            }
        }

        if (failureNum > 0){
            successMsg.append(failureMsg);
        }

        return successMsg.toString();
    }

    @Override
    public Long addWithRule(WithRuleDto dto) {
        return  redisUtil.hincr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(),dto.getUserId().toString(),Long.valueOf(dto.getWithNum()));
    }

    @Override
    public String importWithRule(List<WithRuleDto> dtos) {
        if (StringUtils.isNull(dtos) || dtos.size() == 0)
        {
            throw new ServiceException("白名单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        try{
            for (WithRuleDto dto :
                    dtos) {
                //验证用户是否存在
                AccountUser accountUser = accountUserMapper.selectUserByAccount(dto.getUserId());
                if (Objects.isNull(accountUser)){
                    failureNum++;
                    failureMsg.append("<br/>用户").append(dto.getUserId()).append("不存在");
                    continue;
                }
                Long result = addWithRule(dto);
                if(Objects.isNull(result)) {
                    failureNum++;
                    failureMsg.append("<br/>用户:").append(dto.getUserId()).append("添加白名单失败");
                    continue;
                }
                successNum++;
                successMsg.append("<br/>用户:").append(dto.getUserId()).append("添加白名单成功");
            }
        }catch (Exception e){
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条");
            successMsg.insert(0, "导入成功！共 " + successNum + " 条");
            log.error("导入白名单失败",e);
            successMsg.append(failureMsg);
            return successMsg.toString();
        }
        if (failureNum > 0){
            successMsg.append(failureMsg);
        }
        return successMsg.toString();
    }

    @Override
    public Boolean zhuyuliu(AirdropRecordDto dto){
        List<AirdropThemeRecord> airdropThemeRecords = new ArrayList<>();
        //查询预留编号id
        List<RecordItem> recordItems = dto.getRecordItems();
        for (RecordItem item :
                recordItems) {
            List<Long> numbers = themeNumberMapper.selectOwberIsNullAndNumberInterval(item.getSeriesThemeInfoId());
            if (241!=numbers.size()) throw new StarException("查询回编号共+"+numbers.size() +"数量不足240条 请检查数据");
            item.setSeriesThemeId(numbers);
            List<AirdropThemeRecord> recordList = item.getSeriesThemeId().stream().map(number -> createAirdropThemeRecord(dto.getUserId(), dto.getAirdropType(), item.getSeriesId(), item.getSeriesThemeInfoId(), number)).collect(Collectors.toList());
            airdropThemeRecords.addAll(recordList);
        }
        long successNum = airdropThemeRecords.stream().map(this::addUserAirdrop).filter(Boolean.TRUE::equals).count();
//        long successNum = recordList.stream().map(this::addUserAirdrop).filter(Boolean.TRUE::equals).count();
//
        return successNum == airdropThemeRecords.size();
    }
}
