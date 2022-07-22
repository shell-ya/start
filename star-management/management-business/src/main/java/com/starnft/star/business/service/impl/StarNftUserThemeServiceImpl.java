package com.starnft.star.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starnft.star.business.domain.StarNftSeries;
import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.StarNftUserTheme;
import com.starnft.star.business.domain.vo.UserNumberVO;
import com.starnft.star.business.domain.vo.UserSeriesVO;
import com.starnft.star.business.domain.vo.UserThemeVO;
import com.starnft.star.business.mapper.StarNftSeriesMapper;
import com.starnft.star.business.mapper.StarNftUserThemeMapper;
import com.starnft.star.business.service.IStarNftThemeInfoService;
import com.starnft.star.business.service.IStarNftThemeNumberService;
import com.starnft.star.business.service.IStarNftUserThemeService;
import com.starnft.star.common.constant.IsDeleteStatusEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户藏品Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-20
 */
@Service
public class StarNftUserThemeServiceImpl extends ServiceImpl<StarNftUserThemeMapper, StarNftUserTheme> implements IStarNftUserThemeService {
    @Autowired
    private StarNftUserThemeMapper starNftUserThemeMapper;
    @Autowired
    private StarNftSeriesMapper starNftSeriesMapper;
    @Autowired
    private IStarNftThemeInfoService starNftThemeInfoService;
    @Autowired
    private IStarNftThemeNumberService starNftThemeNumberService;

    /**
     * 查询用户藏品
     *
     * @param id 用户藏品主键
     * @return 用户藏品
     */
    @Override
    public StarNftUserTheme selectStarNftUserThemeById(Long id) {
        return starNftUserThemeMapper.selectStarNftUserThemeById(id);
    }

    /**
     * 查询用户藏品列表
     *
     * @param starNftUserTheme 用户藏品
     * @return 用户藏品
     */
    @Override
    public List<StarNftUserTheme> selectStarNftUserThemeList(StarNftUserTheme starNftUserTheme) {
        return starNftUserThemeMapper.selectStarNftUserThemeList(starNftUserTheme);
    }

    /**
     * 新增用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    @Override
    public int insertStarNftUserTheme(StarNftUserTheme starNftUserTheme) {
        return starNftUserThemeMapper.insertStarNftUserTheme(starNftUserTheme);
    }

    /**
     * 修改用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    @Override
    public int updateStarNftUserTheme(StarNftUserTheme starNftUserTheme) {
        return starNftUserThemeMapper.updateStarNftUserTheme(starNftUserTheme);
    }

    /**
     * 批量删除用户藏品
     *
     * @param ids 需要删除的用户藏品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserThemeByIds(Long[] ids) {
        return starNftUserThemeMapper.deleteStarNftUserThemeByIds(ids);
    }

    /**
     * 删除用户藏品信息
     *
     * @param id 用户藏品主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserThemeById(Long id) {
        return starNftUserThemeMapper.deleteStarNftUserThemeById(id);
    }

    @Override
    public Map<Integer, Map<Long, Optional<UserSeriesVO>>> listSeriesByUserId(String id) {
        StarNftUserTheme starNftUserTheme = new StarNftUserTheme();
        starNftUserTheme.setUserId(id);
        starNftUserTheme.setIsDelete(IsDeleteStatusEnum.NO.getCode());
        QueryWrapper<StarNftUserTheme> starNftUserThemeQueryWrapper = new QueryWrapper<StarNftUserTheme>().setEntity(starNftUserTheme);
        List<StarNftUserTheme> result = this.getBaseMapper().selectList(starNftUserThemeQueryWrapper);
        Set<Long> collect = result.stream().map(StarNftUserTheme::getSeriesId).collect(Collectors.toSet());
        if (collect.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        Map<Long, StarNftSeries> starNftSeriesArray = starNftSeriesMapper.selectList(new QueryWrapper<StarNftSeries>().select("id", "series_name", "series_type", "create_at", "series_images", "series_models", "series_status").lambda().in(StarNftSeries::getId, collect)).stream().collect(Collectors.toMap(StarNftSeries::getId, Function.identity()));
        Map<Integer, Map<Long, Optional<UserSeriesVO>>> res = result.stream().map(item -> {
            UserSeriesVO userSeriesVO = new UserSeriesVO();
            StarNftSeries starNftSeries = starNftSeriesArray.get(item.getSeriesId());
            userSeriesVO.setSeriesName(starNftSeries.getSeriesName());
            userSeriesVO.setSeriesId(starNftSeries.getId());
            userSeriesVO.setSeriesImages(starNftSeries.getSeriesImages());
            userSeriesVO.setUserId(item.getUserId());
            userSeriesVO.setTypes(starNftSeries.getSeriesType());
            userSeriesVO.setNums(1);
            userSeriesVO.setStatus(item.getStatus());
            return userSeriesVO;
        }).collect(Collectors.groupingBy(UserSeriesVO::getStatus, Collectors.groupingBy(UserSeriesVO::getSeriesId, Collectors.reducing(getUserSeriesVOBinaryOperator()))));
        return res;
    }

    @Override
    public  Map<Long, Optional<UserThemeVO>> listThemeBySeriesAndAccount(StarNftUserTheme starNftUserTheme) {
        starNftUserTheme.setIsDelete(IsDeleteStatusEnum.NO.getCode());
        QueryWrapper<StarNftUserTheme> starNftUserThemeQueryWrapper = new QueryWrapper<StarNftUserTheme>().setEntity(starNftUserTheme);
        List<StarNftUserTheme> result = this.getBaseMapper().selectList(starNftUserThemeQueryWrapper);
        Set<Long> collect = result.stream().map(StarNftUserTheme::getSeriesThemeInfoId).collect(Collectors.toSet());
        if (collect.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        Map<Long, StarNftThemeInfo> starNftThemeInfoArrays = starNftThemeInfoService.selectStarNftThemeInfoByIds(collect).stream().collect(Collectors.toMap(StarNftThemeInfo::getId, Function.identity()));
        Map<Long, Optional<UserThemeVO>> res = result.stream().map(item -> {
            StarNftThemeInfo starNftThemeInfo = starNftThemeInfoArrays.get(item.getSeriesThemeInfoId());
            UserThemeVO userThemeVO = new UserThemeVO();
            userThemeVO.setThemeId(item.getSeriesThemeInfoId());
            userThemeVO.setThemeName(starNftThemeInfo.getThemeName());
            userThemeVO.setThemeImages(starNftThemeInfo.getThemePic());
            userThemeVO.setUserId(item.getUserId());
            userThemeVO.setTypes(starNftThemeInfo.getThemeType());
            userThemeVO.setStatus(item.getStatus());
            userThemeVO.setNums(1);
            return userThemeVO;
        }).collect(Collectors.groupingBy(UserThemeVO::getThemeId, Collectors.reducing((a, b) -> {
            b.setNums(a.getNums() + b.getNums());
            return b;
        })));
        return  res;
    }

    @Override
    public List<UserNumberVO> listNumberByThemeAndAccount(StarNftUserTheme starNftUserTheme) {
        starNftUserTheme.setIsDelete(IsDeleteStatusEnum.NO.getCode());
        QueryWrapper<StarNftUserTheme> starNftUserThemeQueryWrapper = new QueryWrapper<StarNftUserTheme>().setEntity(starNftUserTheme);
        List<StarNftUserTheme> result = this.getBaseMapper().selectList(starNftUserThemeQueryWrapper);
        Set<Long> collect = result.stream().map(StarNftUserTheme::getSeriesThemeId).collect(Collectors.toSet());
        if (collect.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        Map<Long, StarNftThemeNumber> starNftThemeNumberMap = starNftThemeNumberService.selectStarNftThemeNumberByIds(collect).stream().collect(Collectors.toMap(StarNftThemeNumber::getId, Function.identity()));
        return  result.stream().map(item->{
            StarNftThemeNumber starNftThemeNumber = starNftThemeNumberMap.get(item.getSeriesThemeId());
            UserNumberVO userNumberVO = new UserNumberVO();
            userNumberVO.setUserId(item.getUserId());
            userNumberVO.setId(item.getId());
            userNumberVO.setAfterTaxPrice(item.getAfterTaxPrice());
            userNumberVO.setCopyrightTax(item.getCopyrightTax());
            userNumberVO.setPlatformTax(item.getPlatformTax());
            userNumberVO.setCopyrightTax(item.getCopyrightTax());
            userNumberVO.setStatus(item.getStatus());
            userNumberVO.setSource(item.getSource());
            userNumberVO.setThemeNumber(starNftThemeNumber.getThemeNumber());
            userNumberVO.setChainIdentification(starNftThemeNumber.getChainIdentification());
           return userNumberVO;
        }).collect(Collectors.toList());

    }

    @NotNull
    private BinaryOperator<UserSeriesVO> getUserSeriesVOBinaryOperator() {
        return (a, b) -> {
            b.setNums(a.getNums() + b.getNums());
            return b;
        };
    }


}
