package com.starnft.star.application.process.compose.strategy.prize;

import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.domain.compose.model.res.PrizeRes;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.enums.PrizeEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;
import com.starnft.star.domain.number.serivce.INumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component("themeComposePrizeStrategy")
@Slf4j
public class ThemeComposePrizeStrategy implements ComposePrizeStrategy {
    @Resource
    INumberService iNumberService;
    @Resource
    RedisLockUtils redisLockUtils;

    @Override
    public PrizeRes composePrize(ComposeManageReq composeManageReq, ComposePrizeDTO composePrizeDTO) {
        log.info("合成进入主题合成策略");
        log.info("合成进入主题标记为:{}", composePrizeDTO.getPrizeStamp());
        NumberQueryDTO numberQueryDTO = new NumberQueryDTO();
        numberQueryDTO.setThemeId(Long.parseLong(composePrizeDTO.getPrizeStamp()));
        List<NumberVO> themeNumberArrays = iNumberService.getNumberListByThemeInfoId(numberQueryDTO);
        Assert.isTrue(!themeNumberArrays.isEmpty(), () -> new StarException("合成奖池已空～"));
        int randomInt = RandomUtil.randomInt(0, themeNumberArrays.size());
        NumberVO themeNumberVo = themeNumberArrays.get(randomInt);
        Boolean lock = redisLockUtils.lock(String.format(RedisKey.COMPOSE_NUMBER_LOCK.getKey(), themeNumberVo.getId()), RedisKey.COMPOSE_NUMBER_LOCK.getTimeUnit().toSeconds(RedisKey.COMPOSE_NUMBER_LOCK.getTime()));
        Assert.isTrue(lock, () -> new StarException("合成人数过多，请稍后再试～"));
        //物品派发
        iNumberService.modifyNumberOwnerByVersion(themeNumberVo.getId(),composeManageReq.getUserId(), NumberStatusEnum.SOLD.getCode(),themeNumberVo.getVersion());
        iNumberService.createUserNumberMapping(getUserThemeMappingVO(composeManageReq, themeNumberVo));
        redisLockUtils.unlock(String.format(RedisKey.COMPOSE_NUMBER_LOCK.getKey(), themeNumberVo.getId()));
        PrizeRes prizeRes = new PrizeRes();
        prizeRes.setPrizeType(PrizeEnum.THEME.getCode());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",themeNumberVo.getId());
        map.put("themeId",themeNumberVo.getThemeId());
        map.put("pic",themeNumberVo.getThemePic());
        map.put("name",themeNumberVo.getThemeName());
        map.put("number",themeNumberVo.getNumber());
        prizeRes.setParams(map);
         return  prizeRes;
    }

    private UserThemeMappingVO getUserThemeMappingVO(ComposeManageReq composeManageReq, NumberVO themeNumberVo) {
        UserThemeMappingVO userThemeMappingVO = new UserThemeMappingVO();
        userThemeMappingVO.setUserId(String.valueOf(composeManageReq.getUserId()));
        userThemeMappingVO.setSeriesThemeId(themeNumberVo.getId());
        userThemeMappingVO.setStatus(UserNumberStatusEnum.PURCHASED.getCode());
        userThemeMappingVO.setSource(themeNumberVo.getType());
        userThemeMappingVO.setSeriesId(themeNumberVo.getSeriesId());
        userThemeMappingVO.setSeriesThemeInfoId(themeNumberVo.getThemeId());
        return userThemeMappingVO;
    }

}
