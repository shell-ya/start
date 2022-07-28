package com.starnft.star.application.process.compose.strategy.prize;

import cn.hutool.json.JSONUtil;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.theme.repository.IThemeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component("themeComposePrizeStrategy")
@Slf4j
public class ThemeComposePrizeStrategy implements ComposePrizeStrategy {
    @Resource
    INumberService iNumberService;
    @Resource
    RedisLockUtils redisLockUtils;
    @Resource
    INumberService numberService;



    @Override
    public void composePrize( ComposeManageReq composeManageReq, ComposePrizeDTO composePrizeDTO) {
        log.info("合成进入主题合成策略");
        log.info("合成进入主题标记为:{}", composePrizeDTO.getPrizeStamp());
        ThemeNumberVo themeNumberVo = iNumberService.queryRandomThemeNumber(Long.parseLong(composePrizeDTO.getPrizeStamp()));
        Boolean lock = redisLockUtils.lock(String.format(RedisKey.COMPOSE_NUMBER_LOCK.getKey(), themeNumberVo.getNumberId()), RedisKey.COMPOSE_NUMBER_LOCK.getTime());
        Assert.isTrue(lock, () -> new StarException("合成人数过多，请稍后再试～"));
        //物品派发
        numberService.createUserNumberMapping(getUserThemeMappingVO(composeManageReq, themeNumberVo));
    }

    private UserThemeMappingVO getUserThemeMappingVO(ComposeManageReq composeManageReq, ThemeNumberVo themeNumberVo) {
        UserThemeMappingVO userThemeMappingVO = new UserThemeMappingVO();
        userThemeMappingVO.setUserId(String.valueOf(composeManageReq.getUserId()));
        userThemeMappingVO.setSeriesThemeId(themeNumberVo.getNumberId());
        userThemeMappingVO.setStatus(UserNumberStatusEnum.PURCHASED.getCode());
        userThemeMappingVO.setSource(themeNumberVo.getThemeType());
        userThemeMappingVO.setSeriesId(themeNumberVo.getSeriesId());
        userThemeMappingVO.setSeriesThemeInfoId(themeNumberVo.getThemeInfoId());
        return userThemeMappingVO;
    }

}
