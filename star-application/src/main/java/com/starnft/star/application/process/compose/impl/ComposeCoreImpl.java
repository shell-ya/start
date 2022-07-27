package com.starnft.star.application.process.compose.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.article.IUserArticleCore;
import com.starnft.star.application.process.compose.IComposeCore;
import com.starnft.star.application.process.compose.model.req.UserMaterialReq;
import com.starnft.star.application.process.compose.model.res.ComposeCategoryMaterialRes;
import com.starnft.star.application.process.compose.model.res.ComposeDetailRes;
import com.starnft.star.application.process.compose.strategy.lottery.ComposeDrawConfiguration;
import com.starnft.star.application.process.compose.strategy.lottery.ComposeDrawLotteryStrategy;
import com.starnft.star.application.process.compose.strategy.prize.ComposePrizeStrategy;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.common.enums.ComposeDrawLotteryStrategyEnums;
import com.starnft.star.common.enums.ComposePrizeTypeEnums;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.repository.IUserThemeRepository;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.compose.model.dto.ComposeMaterialDTO;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;
import com.starnft.star.domain.compose.model.res.ComposeCategoryRes;
import com.starnft.star.domain.compose.model.res.ComposePrizeRes;
import com.starnft.star.domain.compose.model.res.ComposeRes;
import com.starnft.star.domain.compose.repository.IComposePrizeRepository;
import com.starnft.star.domain.compose.service.IComposeService;
import com.starnft.star.domain.scope.service.IUserScopeService;
import com.starnft.star.domain.theme.service.ThemeService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ComposeCoreImpl implements IComposeCore, ApplicationContextAware {

    @Resource
    private IComposeService composeService;
    @Resource
    private UserThemeService userThemeService;
    @Resource
    private IScopeCore iScopeCore;
    @Resource
    private  ComposeDrawConfiguration composeDrawConfiguration;

    ApplicationContext applicationContext;

    @Override
    public List<ComposeCategoryMaterialRes> composeMaterial(Long id) {
        //依据合成ID查询类目信息
        List<ComposeCategoryRes> categoryResArray = composeService.composeCategory(id);
        List<ComposeCategoryMaterialRes> collect = categoryResArray
                .stream()
                .map(item -> ComposeCategoryMaterialRes
                        .builder()
                        .composeScopeType(item.getComposeScopeType())
                        .composeScopeNumber(item.getComposeScopeNumber())
                        .id(item.getId())
                        .isScore(item.getIsScore())
                        .composeMaterials(JSONUtil.toList(item.getComposeMaterial(), ComposeMaterialDTO.class))
                        .build()
                ).collect(Collectors.toList());
        return collect;
    }

    @Override
    public ComposeDetailRes composeDetails(Long id) {
        ComposeRes composeRes = composeService.composeDetails(id);
        List<ComposeCategoryMaterialRes> composeCategoryMaterialRes = composeMaterial(id);
        ComposeDetailRes composeDetailRes = getComposeDetailRes(composeRes, composeCategoryMaterialRes);
        return composeDetailRes;
    }

    @Override
    public Map<Long, List<UserNumbersVO>> composeUserMaterial(UserMaterialReq userMaterialReq) {
        Long categoryId = userMaterialReq.getCategoryId();
        ComposeCategoryRes composeCategoryRes = composeService.composeCategoryByCategoryId(categoryId);
        List<ComposeMaterialDTO> composeMaterials = JSONUtil.toList(composeCategoryRes.getComposeMaterial(), ComposeMaterialDTO.class);
        List<Long> themeIds = composeMaterials.stream().map(ComposeMaterialDTO::getThemeId).collect(Collectors.toList());
        List<UserNumbersVO> userHaveNumbers = userThemeService.queryUserArticleNumberInfoByThemeIds(userMaterialReq.getUserId(), themeIds, UserNumberStatusEnum.PURCHASED);
        Map<Long, List<UserNumbersVO>> collect = userHaveNumbers.stream().collect(Collectors.groupingBy(UserNumbersVO::getThemeId));
        return collect;
    }

    @Override
    public Object composeManage(ComposeManageReq composeManageReq) {
        ComposeCategoryRes composeCategoryRes = composeService.composeCategoryByCategoryId(composeManageReq.getCategoryId());
        //积分判断操作
        if (composeCategoryRes.getIsScore()){
            ScoreDTO subScoreDTO = getScoreDTO(composeManageReq, composeCategoryRes);
            iScopeCore.userScopeManageSub(subScoreDTO);
        }
        List<ComposeMaterialDTO> composeMaterials = JSONUtil.toList(composeCategoryRes.getComposeMaterial(), ComposeMaterialDTO.class);
        List<UserNumbersVO> userNumbersList = userThemeService.queryUserArticleNumberInfoByNumberIds(composeManageReq.getUserId(), composeManageReq.getSourceIds(), UserNumberStatusEnum.PURCHASED);
        Assert.isTrue(composeManageReq.getSourceIds().size() == userNumbersList.size(), () -> new StarException("不能操作非本人物品"));
        Map<Long, List<UserNumbersVO>> collect = userNumbersList.stream().collect(Collectors.groupingBy(UserNumbersVO::getThemeId));
        Map<Long, ComposeMaterialDTO> composeMaterialDTOMap = composeMaterials.stream().collect(Collectors.toMap(ComposeMaterialDTO::getThemeId, Function.identity()));
        checkThemeCounts(collect, composeMaterialDTOMap);
        //执行合成业务
        ComposeDrawLotteryStrategy composeDrawLotteryStrategy = applicationContext
                .getBean(ComposeDrawLotteryStrategyEnums.getScaleLotteryStrategy(
                        composeDrawConfiguration.getComposeDraw()).getStrategy(),
                        ComposeDrawLotteryStrategy.class
                );
        //获取随机合成参数
        List<ComposePrizeRes> composePrizeRes = composeService.composePrizeByComposeId(composeManageReq.getComposeId());
        List<ComposePrizeDTO> composePrizeDTOS = BeanColverUtil.colverList(composePrizeRes, ComposePrizeDTO.class);
        int randNums = RandomUtil.randomInt();
        //获得随机的合成物品
        ComposePrizeDTO composePrizeDTO = composeDrawLotteryStrategy.drawPrize(composePrizeDTOS);
        ComposePrizeStrategy composePrizeStrategy = applicationContext.getBean(ComposePrizeTypeEnums.getComposePrizeType(composePrizeDTO.getPrizeType()).getStrategy(), ComposePrizeStrategy.class);
       //执行商品合成操作
        composePrizeStrategy.composePrize(composePrizeDTO);

        //todo 依据判断看是否需要销毁商品

        return null;
    }

    private ScoreDTO getScoreDTO(ComposeManageReq composeManageReq, ComposeCategoryRes composeCategoryRes) {
        ScoreDTO subScoreDTO = new ScoreDTO();
        subScoreDTO.setIsSub(true);
        subScoreDTO.setScope(new BigDecimal(composeCategoryRes.getComposeScopeNumber()));
        subScoreDTO.setScopeType(composeCategoryRes.getComposeScopeType());
        subScoreDTO.setTemplate("合成消耗%s积分");
        subScoreDTO.setUserId(composeManageReq.getUserId());
        return subScoreDTO;
    }

    private void checkThemeCounts(Map<Long, List<UserNumbersVO>> collect, Map<Long, ComposeMaterialDTO> composeMaterialDTOMap) {
        for (Long themeId : collect.keySet()) {
            int size = collect.get(themeId).size();
            ComposeMaterialDTO composeMaterialDTO = composeMaterialDTOMap.get(themeId);
            Integer number = composeMaterialDTO.getNumber();
            Assert.isTrue(size == number, () -> new StarException("合成数量不足"));
        }
    }

    private ComposeDetailRes getComposeDetailRes(ComposeRes composeRes, List<ComposeCategoryMaterialRes> composeCategoryMaterialRes) {
        ComposeDetailRes composeDetailRes = new ComposeDetailRes();
        composeDetailRes.setComposeImages(composeRes.getComposeImages());
        composeDetailRes.setComposeName(composeRes.getComposeName());
        composeDetailRes.setComposeStatus(composeRes.getComposeStatus());
        composeDetailRes.setComposeRemark(composeRes.getComposeRemark());
        composeDetailRes.setEndAt(composeRes.getEndAt());
        composeDetailRes.setComposeCategoryMaterialResList(composeCategoryMaterialRes);
        composeDetailRes.setStartedAt(composeRes.getStartedAt());
        composeDetailRes.setId(composeRes.getId());
        return composeDetailRes;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
