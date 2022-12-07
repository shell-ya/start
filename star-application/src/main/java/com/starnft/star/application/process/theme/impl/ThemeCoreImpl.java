package com.starnft.star.application.process.theme.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.starnft.star.application.process.theme.ThemeCore;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.activity.model.vo.ActivityVO;
import com.starnft.star.domain.activity.repository.IActivityRepository;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.number.model.vo.RaisingTheme;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import com.starnft.star.domain.publisher.service.PublisherService;
import com.starnft.star.domain.raising.RaisingConfig;
import com.starnft.star.domain.raising.service.IRaisingService;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.service.SeriesService;
import com.starnft.star.domain.theme.model.req.ThemeGoodsReq;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.res.ThemeDetailRes;
import com.starnft.star.domain.theme.model.res.ThemeRes;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeGoodsVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.domain.theme.service.ThemeService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ThemeCoreImpl implements ThemeCore {
    @Resource
    ThemeService themeService;
    @Resource
    PublisherService publisherService;

    @Resource
    private SeriesService seriesService;

    @Resource
    INumberService numberService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IRaisingService raisingService;

    @Autowired
    IActivityRepository activityRepository;

    @Override
    public List<ThemeRes> queryThemesBySeriesId(Long seriesId) {
        List<ThemeVO> themes = themeService.queryThemesBySeriesId(seriesId);
        return getPublisher(themes);
    }

    @Override
    public ResponsePageResult<ThemeRes> queryMainThemeInfo(ThemeReq req) {
        ResponsePageResult<ThemeVO> themeVOResponsePageResult = themeService.queryMainThemeInfo(req);
        return ResponsePageResult.listReplace(themeVOResponsePageResult, getPublisher(themeVOResponsePageResult.getList()));
    }

    @Override
    public ThemeDetailRes queryThemeDetail(Long id) {
        ThemeDetailVO themeDetailVO = themeService.queryThemeDetail(id);
        ThemeDetailRes themeDetailRes = new ThemeDetailRes();
        themeDetailRes.setId(themeDetailVO.getId());
        themeDetailRes.setSeriesId(themeDetailVO.getSeriesId());
        themeDetailRes.setContractAddress(themeDetailVO.getContractAddress());
        themeDetailRes.setTags(themeDetailVO.getTags());
        themeDetailRes.setDescrption(themeDetailVO.getDescrption());
        themeDetailRes.setThemePic(themeDetailVO.getThemePic());
        themeDetailRes.setThemeName(themeDetailVO.getThemeName());
        themeDetailRes.setThemeType(themeDetailVO.getThemeType());
        themeDetailRes.setThemeLevel(themeDetailVO.getThemeLevel());
        themeDetailRes.setLssuePrice(themeDetailVO.getLssuePrice());
        themeDetailRes.setPublishNumber(themeDetailVO.getPublishNumber());
        themeDetailRes.setStock(themeDetailVO.getStock());
//        themeDetailRes.setSellOut(Boolean.TRUE);
        Optional.ofNullable(themeDetailVO.getPublisherId()).ifPresent((item) -> {
            PublisherReq publisherReq = new PublisherReq();
            publisherReq.setPublisherId(item);
            PublisherVO publisherVO = publisherService.queryPublisher(publisherReq);
            themeDetailRes.setPublisherVO(publisherVO);
        });

        return themeDetailRes;
    }

    @Override
    public Set<SecKillGoods> querySecKillThemes() {
        Set<String> keys = redisUtil.keys(String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), "*"));
        if (keys.isEmpty()) {
            return null;
        }
        ArrayList<@Nullable SecKillGoods> goodsList = Lists.newArrayList();
        for (String key : keys) {
            Map<Object, Object> goods = redisUtil.hmget(key);
            for (Object value : goods.values()) {
                SecKillGoods secKillGoods = JSONUtil.toBean(value.toString(), SecKillGoods.class);
                SecKillGoods good = verifyStock(secKillGoods);
                goodsList.add(good);
            }
        }

        List<@Nullable SecKillGoods> noNoneStock = goodsList.stream()
                .filter(Objects::nonNull).filter(goods -> !goods.getSellOut())
                .sorted(Comparator.comparing(SecKillGoods::getStartTime)).collect(Collectors.toList());

        List<@Nullable SecKillGoods> nonStock = goodsList.stream()
                .filter(Objects::nonNull).filter(goods -> goods.getSellOut())
                .sorted(Comparator.comparing(SecKillGoods::getEndTime).reversed()).collect(Collectors.toList());

        Set<@Nullable SecKillGoods> results = new LinkedHashSet<>();
        results.addAll(noNoneStock);
        results.addAll(nonStock);

        return results;
    }

    @Override
    public List<SecKillGoods> querySecKillThemesNew() {
        List<ActivityVO> activityList = activityRepository.getAllActivity();
        log.info("[querySecKillThemesNew]activityList:{}", JSONUtil.toJsonStr(activityList));
        if (CollUtil.isEmpty(activityList)) {
            return Lists.newArrayList();
        }

        List<Long> themeIdList = activityList.stream().map(ActivityVO::getSpuId).collect(Collectors.toList());
        List<ThemeDetailVO> themeList = themeService.getThemeByIdList(themeIdList);
        List<Long> publisherIdList = themeList.stream().map(ThemeDetailVO::getPublisherId).distinct().collect(Collectors.toList());
        List<Long> seriesIdList = themeList.stream().map(ThemeDetailVO::getSeriesId).distinct().collect(Collectors.toList());

        List<PublisherVO> publisherList = publisherService.queryPublisherByIdList(publisherIdList);
        Map<Long, PublisherVO> publisherMap = publisherList.stream().collect(Collectors.toMap(PublisherVO::getAuthorId, Function.identity()));

        List<SeriesVO> seriesList = seriesService.querySeriesByIdList(seriesIdList);
        Map<Long, SeriesVO> seriesMap = seriesList.stream().collect(Collectors.toMap(SeriesVO::getId, Function.identity()));

        Map<Long, ThemeDetailVO> themeMap = themeList.stream().collect(Collectors.toMap(ThemeDetailVO::getId, Function.identity()));
        List<SecKillGoods> result = new ArrayList<>();
        activityList.forEach(activity -> {
            if (!themeMap.containsKey(activity.getSpuId())) {
                log.error("根据spuId未获取到theme，spuId:{}", activity.getSpuId());
                return;
            }

            ThemeDetailVO detailVO = themeMap.get(activity.getSpuId());
            PublisherVO publisherVO = publisherMap.get(detailVO.getPublisherId());
            SeriesVO seriesVO = seriesMap.get(detailVO.getSeriesId());
            SecKillGoods copy = copyNew(activity, detailVO, publisherVO, seriesVO.getSeriesName());
            result.add(copy);

        });
        result.sort(Comparator.comparing(SecKillGoods::getSellOut).reversed());
        return result;
    }

    private SecKillGoods copyNew(ActivityVO activity, ThemeDetailVO detailVO, PublisherVO publisherVO, String seriesName) {
        SecKillGoods secKillGoods = new SecKillGoods();
        secKillGoods.setGoodsNum(activity.getGoodsNum());
        secKillGoods.setDescrption(detailVO.getDescrption());
        secKillGoods.setSecCost(activity.getSecCost());
        secKillGoods.setEndTime(activity.getEndTime());
        secKillGoods.setStock(activity.getStock());
        secKillGoods.setStartTime(activity.getStartTime());
        secKillGoods.setThemeId(detailVO.getId());
        secKillGoods.setSeriesId(detailVO.getSeriesId());
        secKillGoods.setSeriesName(seriesName);
        secKillGoods.setThemeLevel(detailVO.getThemeLevel());
        secKillGoods.setThemeName(detailVO.getThemeName());
        secKillGoods.setThemePic(detailVO.getThemePic());
        secKillGoods.setThemeType(detailVO.getThemeType());
        secKillGoods.setPublisherId(publisherVO.getPublisherId());
        secKillGoods.setPublisherPic(publisherVO.getPublisherPic());
        secKillGoods.setPublisherName(publisherVO.getPublisherName());
        secKillGoods.setSellOut(activity.getGoodsNum() - activity.getFrozenStock() - activity.getSoldStock() > 0);
        return secKillGoods;
    }

    private SecKillGoods copy(ActivityVO activity, ThemeDetailVO detailVO) {

        PublisherVO publisherVO = publisherService.queryPublisher(new PublisherReq(detailVO.getPublisherId()));
        if (publisherVO == null) {
            log.error("themeInfo: [{}] publisherId : [{}]", detailVO, detailVO.getPublisherId());
            throw new RuntimeException("未找到对应发行商");
        }
        SeriesVO seriesVO = seriesService.querySeriesById(detailVO.getSeriesId());
        if (publisherVO == null) {
            log.error("themeInfo: [{}] seriesId : [{}]", detailVO, detailVO.getSeriesId());
            throw new RuntimeException("未找到对应系列");
        }
        SecKillGoods secKillGoods = new SecKillGoods();
        secKillGoods.setGoodsNum(activity.getGoodsNum());
        secKillGoods.setDescrption(detailVO.getDescrption());
        secKillGoods.setSecCost(activity.getSecCost());
        secKillGoods.setEndTime(activity.getEndTime());
        secKillGoods.setStock(activity.getStock());
        secKillGoods.setStartTime(activity.getStartTime());
        secKillGoods.setThemeId(detailVO.getId());
        secKillGoods.setSeriesId(detailVO.getSeriesId());
        secKillGoods.setSeriesName(seriesVO.getSeriesName());
        secKillGoods.setThemeLevel(detailVO.getThemeLevel());
        secKillGoods.setThemeName(detailVO.getThemeName());
        secKillGoods.setThemePic(detailVO.getThemePic());
        secKillGoods.setThemeType(detailVO.getThemeType());
        secKillGoods.setPublisherId(publisherVO.getPublisherId());
        secKillGoods.setPublisherPic(publisherVO.getPublisherPic());
        secKillGoods.setPublisherName(publisherVO.getPublisherName());
        secKillGoods.setSellOut(activity.getStock() > 0);
        return secKillGoods;
    }

    @Override
    public ResponsePageResult<ThemeGoodsVO> themeGoodsList(ThemeGoodsReq themeGoodsReq) {
        try {
            ResponsePageResult<ThemeGoodsVO> themeGoods = themeService.themeGoodsList(themeGoodsReq);
            List<ThemeGoodsVO> themeGoodsVOS = Lists.newArrayList();
            for (ThemeGoodsVO themeGoodsVO : themeGoods.getList()) {
                Long publisherId = themeGoodsVO.getPublisherId();
                PublisherVO publisherVO = publisherService.queryPublisher(new PublisherReq(publisherId));
                themeGoodsVO.setPublisherName(publisherVO.getPublisherName());
                //流通量
//                Integer destroy = numberService.destroyedPublishNumber(themeGoodsVO.getId());
                themeGoodsVO.setCirculate(themeGoodsVO.getPublishNumber());
                themeGoodsVO.setRaisingFlag(Boolean.FALSE);
//                if (1026607869655789568L == themeGoodsVO.getId()){
//                    themeGoodsVO.setCirculate(themeGoodsVO.getCirculate() + 2500);
//                }
                RaisingTheme raisingTheme = raisingService.nowRaisingTheme(themeGoodsVO.getId());
                if (Objects.nonNull(raisingTheme) && raisingTheme.getIsRaising()) {
                    themeGoodsVO.setRaisingFlag(Boolean.TRUE);
                    themeGoodsVO.setRaisingMsg(RaisingConfig.getRaisingMsg());
                }
                if (null == themeGoodsVO.getFloor() && Objects.nonNull(raisingTheme)) {
                    themeGoodsVO.setFloor(raisingTheme.getLimitPrice());
                }
                themeGoodsVOS.add(themeGoodsVO);
            }
            return ResponsePageResult.listReplace(themeGoods, themeGoodsVOS);
        } catch (Exception e) {
            log.error("市场列表发生错误", e);
            throw new StarException(StarError.SYSTEM_ERROR);
        }
    }

    //更新展示库存量
    private SecKillGoods verifyStock(SecKillGoods secKillGoods) {
        String stockKey = String.format(RedisKey.SECKILL_GOODS_STOCK_QUEUE.getKey(), secKillGoods.getThemeId(), secKillGoods.getTime());
        String poolKey = String.format(RedisKey.SECKILL_GOODS_STOCK_POOL.getKey(), secKillGoods.getThemeId(), secKillGoods.getTime());
        long stock = redisUtil.lGetListSize(stockKey);
        long poolStock = redisUtil.sGetSetSize(poolKey);
        if ((stock + poolStock) <= 0) {
            secKillGoods.setSellOut(true);
        }
        if (secKillGoods.getStock() != stock) {
            secKillGoods.setStock((int) stock);
        }
        secKillGoods.setStock(0);
        secKillGoods.setGoodsNum(0);
        return secKillGoods;
    }

    private List<ThemeRes> getPublisher(List<ThemeVO> theme) {
        List<ThemeRes> result = new ArrayList<>();
        Set<Long> collect = theme.stream().map(ThemeVO::getPublisherId).collect(Collectors.toSet());
        if (!collect.isEmpty()) {
            Map<Long, List<PublisherVO>> pubs = publisherService.queryPublisherByIds(collect).stream().collect(Collectors.groupingBy(PublisherVO::getAuthorId));
            for (ThemeVO themeVO : theme) {
                ThemeRes themeRes = new ThemeRes();
                themeRes.setId(themeVO.getId());
                themeRes.setThemePic(themeVO.getThemePic());
                themeRes.setThemeType(themeVO.getThemeType());
                themeRes.setThemeLevel(themeVO.getThemeLevel());
                themeRes.setThemeName(themeVO.getThemeName());
                themeRes.setLssuePrice(themeVO.getLssuePrice());
                themeRes.setSeriesId(themeVO.getSeriesId());
                themeRes.setPublishNumber(themeVO.getPublishNumber());
                themeRes.setPublisherVO(pubs.get(themeVO.getPublisherId()).get(0));
                themeRes.setTags(themeVO.getTags());
                result.add(themeRes);
            }
        }
        return result;
    }


}
