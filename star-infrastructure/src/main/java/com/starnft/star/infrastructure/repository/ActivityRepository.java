package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawOrderVO;
import com.starnft.star.domain.activity.model.vo.GoodsHavingTimesVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.draw.model.req.DrawAwardExportsReq;
import com.starnft.star.domain.draw.model.req.PartakeReq;
import com.starnft.star.domain.draw.model.vo.DrawAwardExportVO;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.infrastructure.entity.activity.GoodsHavingTimeRecord;
import com.starnft.star.infrastructure.entity.activity.StarScheduleSeckill;
import com.starnft.star.infrastructure.entity.draw.Activity;
import com.starnft.star.infrastructure.entity.draw.UserStrategyExport;
import com.starnft.star.infrastructure.mapper.activity.GoodsHavingTimeRecordMapper;
import com.starnft.star.infrastructure.mapper.activity.StarScheduleSeckillMapper;
import com.starnft.star.infrastructure.mapper.draw.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ActivityRepository implements IActivityRepository {

    private Logger logger = LoggerFactory.getLogger(ActivityRepository.class);
    @Resource
    private StarScheduleSeckillMapper starScheduleSeckillMapper;

    @Resource
    private IActivityDao activityDao;
    @Resource
    private IAwardDao awardDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Resource
    private GoodsHavingTimeRecordMapper goodsHavingTimeRecordMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public int subtractionActivityStock(Long activityId) {
        return activityDao.subtractionActivityStock(activityId);
    }

    @Override
    public List<DrawActivityVO> scanToDoActivityList(Long id) {
        List<Activity> activityList = activityDao.scanToDoActivityList(id);
        List<DrawActivityVO> activityVOList = new ArrayList<>(activityList.size());
        for (Activity activity : activityList) {
            DrawActivityVO activityVO = new DrawActivityVO();
            activityVO.setId(activity.getId());
            activityVO.setActivityId(activity.getActivityId());
            activityVO.setActivityName(activity.getActivityName());
            activityVO.setBeginDateTime(activity.getBeginDateTime());
            activityVO.setEndDateTime(activity.getEndDateTime());
            activityVO.setState(activity.getState());
            activityVOList.add(activityVO);
        }
        return activityVOList;
    }

    @Override
    public void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        if (null == tokenKey) {
            return;
        }
        // 删除分布式锁 Key
        redisUtil.del(tokenKey);
    }

    @Override
    public void saveUserStrategyExport(DrawOrderVO drawOrder) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(drawOrder.getuId());
        userStrategyExport.setActivityId(drawOrder.getActivityId());
        userStrategyExport.setOrderId(drawOrder.getOrderId());
        userStrategyExport.setStrategyId(drawOrder.getStrategyId());
        userStrategyExport.setStrategyMode(drawOrder.getStrategyMode());
        userStrategyExport.setGrantType(drawOrder.getGrantType());
        userStrategyExport.setGrantDate(drawOrder.getGrantDate());
        userStrategyExport.setGrantState(drawOrder.getGrantState());
        userStrategyExport.setAwardId(drawOrder.getAwardId());
        userStrategyExport.setAwardType(drawOrder.getAwardType());
        userStrategyExport.setAwardName(drawOrder.getAwardName());
        userStrategyExport.setAwardContent(drawOrder.getAwardContent());
        userStrategyExport.setUuid(String.valueOf(drawOrder.getTakeId()));
        userStrategyExport.setMqState(StarConstants.MQState.INIT.getCode());

        userStrategyExportDao.insert(userStrategyExport);
    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setMqState(mqState);
        userStrategyExportDao.updateInvoiceMqState(userStrategyExport);
    }

    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);
    }

    @Override
    public ResponsePageResult<DrawAwardExportVO> queryUserStrategyExportByUId(DrawAwardExportsReq exportVO) {
        PageInfo<UserStrategyExport> drawAwardExportVOPageInfo = PageHelper.startPage(exportVO.getPage(), exportVO.getSize())
                .doSelectPageInfo(() -> userStrategyExportDao.queryUserStrategyExportByUId(exportVO.getUId()));

        return new ResponsePageResult<DrawAwardExportVO>(drawAwardExportVOPageInfo.getList()
                .stream()
                .map(po -> BeanColverUtil.colver(po, DrawAwardExportVO.class))
                .collect(Collectors.toList()),
                exportVO.getPage(), exportVO.getSize(), drawAwardExportVOPageInfo.getTotal());
    }

    @Override
    public List<GoodsHavingTimesVO> queryGoodsHavingTimesByGood(Long themeId) {
        List<GoodsHavingTimeRecord> goodsHavingTimeRecords = goodsHavingTimeRecordMapper.selectList(new LambdaQueryWrapper<GoodsHavingTimeRecord>()
                .eq(GoodsHavingTimeRecord::getThemeInfoId, themeId).eq(GoodsHavingTimeRecord::getIsDeleted, 0));
        return BeanColverUtil.colverList(goodsHavingTimeRecords, GoodsHavingTimesVO.class);
    }

    @Override
    public void initGoodsHavingTimes(NumberDetailVO numberDetailVO) {
        GoodsHavingTimeRecord goodsHavingTimeRecord = new GoodsHavingTimeRecord();
        goodsHavingTimeRecord.setCountTimes(1);
        goodsHavingTimeRecord.setUid(Long.parseLong(numberDetailVO.getOwnerBy()));
        goodsHavingTimeRecord.setThemeInfoId(numberDetailVO.getThemeId());
        goodsHavingTimeRecord.setVersion(0);
        goodsHavingTimeRecord.setCreatedAt(new Date());
        goodsHavingTimeRecord.setCreatedBy(Long.parseLong(numberDetailVO.getOwnerBy()));
        goodsHavingTimeRecord.setIsDeleted(Boolean.FALSE);
        int isSuccess = goodsHavingTimeRecordMapper.insert(goodsHavingTimeRecord);
        if (isSuccess != 1) {
            logger.error("藏品 ： [{}]用户 :[{}] 持有次数插入失败！", numberDetailVO.getThemeId(), numberDetailVO.getOwnerBy());
        }
    }

    @Override
    public Integer queryUserExportNum(Long uid) {
        return userStrategyExportDao.queryUserStrategyExportNum(uid.toString()).size();
    }

    @Override
    public List<DrawAwardExportVO> queryUserExportList(Long uid) {
        List<UserStrategyExport> userStrategyExports = userStrategyExportDao.queryUserStrategyExportList(uid.toString());
        List<DrawAwardExportVO> collect = userStrategyExports
                .stream()
                .map(po -> BeanColverUtil.colver(po, DrawAwardExportVO.class))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public boolean deleteExport(String orderId) {
        return userStrategyExportDao.deleteExport(orderId);
    }

    @Override
    public void delTimes(Long uid, Long themeId) {
        goodsHavingTimeRecordMapper.delete(new LambdaQueryWrapper<GoodsHavingTimeRecord>().eq(GoodsHavingTimeRecord::getUid, uid)
                .eq(GoodsHavingTimeRecord::getThemeInfoId, themeId));
    }

    @Override
    public Integer addTimes(Long uid, Long themeId, Integer version) {
        int isSuccess = goodsHavingTimeRecordMapper.addCountTimes(uid, themeId, version);
        return isSuccess;

    }

    @Override
    public List<ActivityVO> obtainActivities(String startTime, String endTime, List<String> keys) {
        List<StarScheduleSeckill> starScheduleSeckills = starScheduleSeckillMapper.obtainActivities(startTime, endTime, keys);
        return BeanColverUtil.colverList(starScheduleSeckills, ActivityVO.class);
    }

    @Override
    public boolean modifyStock(Integer spuId, Integer stock) {
        StarScheduleSeckill starScheduleSeckill = starScheduleSeckillMapper.queryByThemeId(spuId);
        return starScheduleSeckillMapper.modifyStock(spuId, stock, starScheduleSeckill.getVersion()) == 1;
    }

    @Override
    public DrawActivityVO getDrawActivity(PartakeReq partakeReq) {
        Activity activity = activityDao.queryActivityById(partakeReq.getActivityId());

        if (activity == null) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "未找到该活动");
        }
        return buildDrawActivityVO(activity);
    }

    private DrawActivityVO buildDrawActivityVO(Activity activity) {
        DrawActivityVO activityVO = new DrawActivityVO();
        activityVO.setId(activity.getId());
        activityVO.setActivityId(activity.getActivityId());
        activityVO.setActivityName(activity.getActivityName());
        activityVO.setActivityDesc(activity.getActivityDesc());
        activityVO.setBeginDateTime(activity.getBeginDateTime());
        activityVO.setEndDateTime(activity.getEndDateTime());
        activityVO.setStockCount(activity.getStockCount());
        activityVO.setStockSurplusCount(activity.getStockSurplusCount());
        activityVO.setTakeCount(activity.getTakeCount());
        activityVO.setStrategyId(activity.getStrategyId());
        activityVO.setState(activity.getState());
        activityVO.setCreator(activity.getCreator());
        activityVO.setCreateTime(activity.getCreateTime());
        activityVO.setUpdateTime(activity.getUpdateTime());
        activityVO.setUpdateTime(activity.getUpdateTime());
        activityVO.setConsumables(activity.getConsumables());

        return activityVO;

    }
}
