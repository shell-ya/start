package com.starnft.star.domain.raising.service;

import com.starnft.star.domain.number.model.vo.RaisingTheme;

import java.math.BigDecimal;

public interface IRaisingService {
    public int saveRaising(RaisingTheme raisingTheme);

    public RaisingTheme nowRaisingTheme(Long themeInfoId);

    boolean themeRaisingFlag(Long themeId);

    BigDecimal getThemeLimitPrice(Long themeId);

    boolean updateRaisingFlag(Long themeInfoId);

    RaisingTheme getNowRaisingByTheme(Long themeInfoId);
}
