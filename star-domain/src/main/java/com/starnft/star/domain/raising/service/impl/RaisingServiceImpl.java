package com.starnft.star.domain.raising.service.impl;

import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.domain.number.model.vo.RaisingTheme;
import com.starnft.star.domain.raising.RaisingConfig;
import com.starnft.star.domain.raising.repository.IRaisingDetailRepository;
import com.starnft.star.domain.raising.service.IRaisingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class RaisingServiceImpl implements IRaisingService {

    @Resource
    private IRaisingDetailRepository raisingDetailRepository;

    @Override
    public int saveRaising(RaisingTheme raisingTheme) {

        raisingTheme.setLimitPrice(
               BigDecimal.ZERO.compareTo( raisingTheme.getFloorPrice()) == 0 ? RaisingConfig.getDefaultPrice().multiply(RaisingConfig.getRaisingProportion()) : raisingTheme.getFloorPrice().multiply(RaisingConfig.getRaisingProportion())
        );
        return raisingDetailRepository.save(raisingTheme);
    }

    @Override
    public RaisingTheme nowRaisingTheme(Long themeInfoId) {
        Date first = DateUtil.toDayStartHour(DateUtil.getDaDate()); //凌晨
        Date late = DateUtil.addDateHour(first, 23);
        return raisingDetailRepository.nowRaisingTheme(themeInfoId, first, late);
    }

    @Override
    public boolean themeRaisingFlag(Long themeId) {
        Date first = DateUtil.toDayStartHour(DateUtil.getDaDate()); //凌晨
        Date late = DateUtil.addDateHour(first, 23);
        return raisingDetailRepository.raisingFlag(themeId,first,late);
    }

    @Override
    public BigDecimal getThemeLimitPrice(Long themeId) {
        Date first = DateUtil.toDayStartHour(DateUtil.getDaDate()); //凌晨
        Date late = DateUtil.addDateHour(first, 23);
        return raisingDetailRepository.raisingPrice(themeId,first,late);
    }

    @Override
    public boolean updateRaisingFlag(Long themeInfoId) {
        Date first = DateUtil.toDayStartHour(DateUtil.getDaDate()); //凌晨
        Date late = DateUtil.addDateHour(first, 23);
        return raisingDetailRepository.updateFlag(themeInfoId,first,late);
    }

    @Override
    public RaisingTheme getNowRaisingByTheme(Long themeInfoId) {
        Date first = DateUtil.toDayStartHour(DateUtil.getDaDate()); //凌晨
        Date late = DateUtil.addDateHour(first, 23);
        return raisingDetailRepository.getNowRaisingByTheme(themeInfoId,first,late);
    }


}
