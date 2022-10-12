package com.starnft.star.domain.raising.repository;

import com.starnft.star.domain.number.model.vo.RaisingTheme;

import java.math.BigDecimal;
import java.util.Date;

public interface IRaisingDetailRepository {

    int save(RaisingTheme raisingTheme);

    RaisingTheme nowRaisingTheme(Long themeId, Date first,Date late);

    boolean raisingFlag(Long themeId, Date first, Date late);

    BigDecimal raisingPrice(Long themeId, Date first, Date late);

    boolean updateFlag(Long themeInfoId, Date first, Date late);

    RaisingTheme getNowRaisingByTheme(Long themeInfoId, Date first, Date late);
}
