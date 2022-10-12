package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.starnft.star.domain.number.model.vo.RaisingTheme;
import com.starnft.star.domain.raising.repository.IRaisingDetailRepository;
import com.starnft.star.infrastructure.entity.raising.StarRaisingDetail;
import com.starnft.star.infrastructure.mapper.raising.StarRaisingDetailMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class RaisingDetailRepository implements IRaisingDetailRepository {

    @Resource
    private StarRaisingDetailMapper raisingDetailMapper;


    @Override
    public int save(RaisingTheme raisingTheme) {

        StarRaisingDetail raisingDetail = new StarRaisingDetail();
        raisingDetail.setThemeInfoId(raisingTheme.getThemeInfoId());
        raisingDetail.setOpeningPrice(raisingTheme.getFloorPrice());
        raisingDetail.setLimitPrice(raisingTheme.getLimitPrice());
        raisingDetail.setIsDeleted(Boolean.FALSE);
        raisingDetail.setIsRaising(Boolean.FALSE);
        raisingDetail.setCreatedAt(new Date());
        raisingDetail.setModifiedAt(new Date());

        return raisingDetailMapper.insert(raisingDetail);
    }

    @Override
    public RaisingTheme nowRaisingTheme(Long themeId,Date first,Date late) {
        LambdaQueryWrapper<StarRaisingDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StarRaisingDetail::getThemeInfoId,themeId);
        wrapper.between(StarRaisingDetail::getCreatedAt,first,late);
        wrapper.orderByDesc(StarRaisingDetail::getCreatedAt);
        List<StarRaisingDetail> starRaisingDetails = raisingDetailMapper.selectList(wrapper);
        if (!starRaisingDetails.isEmpty()) return coverTheme(starRaisingDetails.get(0));
        return null;
    }

    @Override
    public boolean raisingFlag(Long themeId, Date first, Date late) {
        LambdaQueryWrapper<StarRaisingDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StarRaisingDetail::getThemeInfoId,themeId);
        wrapper.between(StarRaisingDetail::getCreatedAt,first,late);
        wrapper.orderByDesc(StarRaisingDetail::getCreatedAt);
        List<StarRaisingDetail> starRaisingDetails = raisingDetailMapper.selectList(wrapper);
        if (!starRaisingDetails.isEmpty()) return starRaisingDetails.get(0).getIsRaising();
        return Boolean.FALSE;
    }

    @Override
    public BigDecimal raisingPrice(Long themeId, Date first, Date late) {
        LambdaQueryWrapper<StarRaisingDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StarRaisingDetail::getThemeInfoId,themeId);
        wrapper.between(StarRaisingDetail::getCreatedAt,first,late);
        wrapper.orderByDesc(StarRaisingDetail::getCreatedAt);
        List<StarRaisingDetail> starRaisingDetails = raisingDetailMapper.selectList(wrapper);
        if (!starRaisingDetails.isEmpty()) return starRaisingDetails.get(0).getLimitPrice();
        return null;
    }

    @Override
    public boolean updateFlag(Long themeInfoId, Date first, Date late) {
        LambdaUpdateWrapper<StarRaisingDetail> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(StarRaisingDetail::getThemeInfoId,themeInfoId);
        wrapper.between(StarRaisingDetail::getCreatedAt,first,late);
        StarRaisingDetail raisingDetail = new StarRaisingDetail();
        raisingDetail.setIsRaising(Boolean.TRUE);
        return raisingDetailMapper.update(raisingDetail,wrapper) >= 1;
    }

    @Override
    public RaisingTheme getNowRaisingByTheme(Long themeInfoId, Date first, Date late) {
        return null;
    }

    public RaisingTheme coverTheme(StarRaisingDetail detail){
        RaisingTheme raisingTheme = new RaisingTheme();
        raisingTheme.setFloorPrice(detail.getOpeningPrice());
        raisingTheme.setThemeInfoId(detail.getThemeInfoId());
        raisingTheme.setLimitPrice(detail.getLimitPrice());
        raisingTheme.setIsRaising(detail.getIsRaising());
        return raisingTheme;
    }
}
