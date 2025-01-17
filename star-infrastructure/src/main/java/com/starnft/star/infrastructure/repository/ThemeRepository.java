package com.starnft.star.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.theme.model.req.ThemeGoodsReq;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeGoodsVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.domain.theme.repository.IThemeRepository;
import com.starnft.star.infrastructure.entity.theme.StarNftThemeInfo;
import com.starnft.star.infrastructure.mapper.theme.StarNftThemeInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ThemeRepository implements IThemeRepository {
    @Resource
    StarNftThemeInfoMapper starNftThemeInfoMapper;

    @Resource
    private RedisUtil redisUtil;

    private static final Map<String, SecKillGoods> goodsMap = new ConcurrentHashMap<>();

    @Override
    public ResponsePageResult<ThemeVO> queryTheme(ThemeReq requestPage) {
        QueryWrapper<StarNftThemeInfo> wrapper = new QueryWrapper<StarNftThemeInfo>().eq(StarNftThemeInfo.COL_IS_DELETE, Boolean.FALSE);
        if (Objects.nonNull(requestPage.getIsRecommend())) {
            wrapper.eq(StarNftThemeInfo.COL_IS_RECOMMEND, requestPage.getIsRecommend());
        }
        if (Objects.nonNull(requestPage.getSeriesId())) {
            wrapper.eq(StarNftThemeInfo.COL_SERIES_ID, requestPage.getSeriesId());
        }
        PageInfo<StarNftThemeInfo> result = PageHelper.startPage(requestPage.getPage(), requestPage.getSize())
                .doSelectPageInfo(() -> this.starNftThemeInfoMapper.selectList(wrapper));
        List<ThemeVO> list = result.getList().stream()
                .map(item ->
                        ThemeVO
                                .builder()
                                .id(item.getId())
                                .seriesId(item.getSeriesId())
                                .themeName(item.getThemeName())
                                .themeLevel(item.getThemeLevel())
                                .themePic(item.getThemePic())
                                .lssuePrice(item.getLssuePrice())
                                .publishNumber(item.getPublishNumber())
                                .themeType(item.getThemeType())
                                .publisherId(item.getPublisherId())
                                .tags(item.getTags())
                                .build()

                )
                .collect(Collectors.toList());
        ResponsePageResult<ThemeVO> pageResult = new ResponsePageResult<>();
        pageResult.setPage(result.getPageNum());
        pageResult.setList(list);
        pageResult.setTotal(result.getTotal());
        pageResult.setSize(result.getSize());
        return pageResult;
    }

    @Override
    public ResponsePageResult<ThemeGoodsVO> themeGoodsList(ThemeGoodsReq themeGoodsReq) {
        PageInfo<ThemeGoodsVO> result = PageHelper.startPage(themeGoodsReq.getPage(), themeGoodsReq.getSize())
                .doSelectPageInfo(() -> this.starNftThemeInfoMapper.themeGoodsList(themeGoodsReq));
        return new ResponsePageResult<>(result.getList(), result.getPageNum(), result.getPageSize(), result.getTotal());
    }

    @Override
    public List<ThemeVO> queryTheme(Long seriesId) {
        List<StarNftThemeInfo> starNftThemeInfos = this.starNftThemeInfoMapper.selectList(
                Wrappers.lambdaQuery(StarNftThemeInfo.class)
                        .eq(StarNftThemeInfo::getSeriesId, seriesId)
                        .eq(StarNftThemeInfo::getIsDelete, Boolean.FALSE));
        return starNftThemeInfos.stream()
                .map(item ->
                        ThemeVO
                                .builder()
                                .id(item.getId())
                                .seriesId(seriesId)
                                .publisherId(item.getPublisherId())
                                .themeName(item.getThemeName())
                                .build()

                )
                .collect(Collectors.toList());
    }

    @Override
    public ThemeDetailVO queryThemeDetail(Long id) {
        StarNftThemeInfo repository = this.starNftThemeInfoMapper.selectById(id);
        if (Objects.isNull(repository) || repository.getIsDelete().equals(Boolean.TRUE)) {
            return null;
        }
        return ThemeDetailVO
                .builder()
                .publisherId(repository.getPublisherId())
                .id(repository.getId())
                .themeName(repository.getThemeName())
                .tags(repository.getTags())
                .seriesId(repository.getSeriesId())
                .themePic(repository.getThemePic())
                .contractAddress(repository.getContractAddress())
                .descrption(repository.getDescrption())
                .stock(repository.getStock())
                .themeType(repository.getThemeType())
                .themeLevel(repository.getThemeLevel())
                .lssuePrice(repository.getLssuePrice())
                .publishNumber(repository.getPublishNumber())
                .marketOpenDate(repository.getMarketOpenTime())
                .isResale(repository.getIsResale())
                .build();
    }

    @Override
    public List<ThemeDetailVO> queryByThemeIdList(List<Long> themeIdList) {
        QueryWrapper<StarNftThemeInfo> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(StarNftThemeInfo::getId, themeIdList);
        wrapper.lambda().eq(StarNftThemeInfo::getIsDelete, 0);
        List<StarNftThemeInfo> starNftThemeInfos = this.starNftThemeInfoMapper.selectList(wrapper);
        if (CollUtil.isEmpty(starNftThemeInfos)) {
            return Lists.newArrayList();
        }
        return starNftThemeInfos.stream().map(repository ->
                ThemeDetailVO.builder()
                        .publisherId(repository.getPublisherId())
                        .id(repository.getId())
                        .themeName(repository.getThemeName())
                        .tags(repository.getTags())
                        .seriesId(repository.getSeriesId())
                        .themePic(repository.getThemePic())
                        .contractAddress(repository.getContractAddress())
                        .descrption(repository.getDescrption())
                        .stock(repository.getStock())
                        .themeType(repository.getThemeType())
                        .themeLevel(repository.getThemeLevel())
                        .lssuePrice(repository.getLssuePrice())
                        .publishNumber(repository.getPublishNumber())
                        .marketOpenDate(repository.getMarketOpenTime())
                        .isResale(repository.getIsResale())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public SecKillGoods obtainGoodsCache(Long themeId, String time) {
        String goodsKey = String.format(RedisKey.SECKILL_GOODS_INFO.getKey(), time);
        SecKillGoods secKillGoods = goodsMap.get(goodsKey + themeId);
        if (Objects.nonNull(secKillGoods)) {
            return secKillGoods;
        }

        Object goods = this.redisUtil.hget(goodsKey, String.valueOf(themeId));
        if (goods == null) {
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }
        goodsMap.put(goodsKey + themeId, JSONUtil.toBean(goods.toString(), SecKillGoods.class));
        return JSONUtil.toBean(goods.toString(), SecKillGoods.class);

    }

    @Override
    public ResponsePageResult<ThemeVO> queryRecommendTheme(ThemeReq param) {
        PageInfo<ThemeVO> result = PageMethod.startPage(param.getPage(), param.getSize())
                .doSelectPageInfo(() -> this.starNftThemeInfoMapper.selectRecommendThemes(param));
        return new ResponsePageResult<>(result.getList(), result.getPageNum(), result.getPageSize(), result.getTotal());
    }

    @Override
    public Integer obtainThemeIssuedQty(Long themeId) {
        return this.starNftThemeInfoMapper.selectThemeIssuedQty(themeId);
    }
}
