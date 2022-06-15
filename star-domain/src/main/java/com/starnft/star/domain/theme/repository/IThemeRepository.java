package com.starnft.star.domain.theme.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.vo.SecKillGoods;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;

import java.util.List;

public interface IThemeRepository {
    ResponsePageResult<ThemeVO> queryTheme(ThemeReq requestPage);

    List<ThemeVO> queryTheme(Long seriesId);

    ThemeDetailVO queryThemeDetail(Long id);

    //缓存中获取秒杀商品信息
    SecKillGoods obtainGoodsCache(Long themeId, String time);

}
