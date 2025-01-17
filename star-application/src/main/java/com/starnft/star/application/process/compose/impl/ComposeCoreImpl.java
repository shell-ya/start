package com.starnft.star.application.process.compose.impl;

import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.compose.IComposeCore;
import com.starnft.star.application.process.compose.model.req.UserMaterialReq;
import com.starnft.star.application.process.compose.model.res.ComposeCategoryMaterialRes;
import com.starnft.star.application.process.compose.model.res.ComposeDetailRes;
import com.starnft.star.application.process.compose.strategy.lottery.ComposeDrawConfiguration;
import com.starnft.star.application.process.compose.strategy.lottery.ComposeDrawLotteryStrategy;
import com.starnft.star.application.process.compose.strategy.prize.ComposePrizeStrategy;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
import com.starnft.star.common.enums.*;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.compose.model.dto.ComposeMaterialDTO;
import com.starnft.star.domain.compose.model.dto.ComposePrizeDTO;
import com.starnft.star.domain.compose.model.dto.ComposeRecordDTO;
import com.starnft.star.domain.compose.model.dto.ComposeUserArticleNumberDTO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;
import com.starnft.star.domain.compose.model.res.*;
import com.starnft.star.domain.compose.service.IComposeManageService;
import com.starnft.star.domain.compose.service.IComposeService;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.serivce.INumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComposeCoreImpl implements IComposeCore, ApplicationContextAware {

    @Resource
    private IComposeService composeService;
    @Resource
    private IComposeManageService composeManageService;
    @Resource
    private UserThemeService userThemeService;
    @Resource
    private IScopeCore iScopeCore;
    @Resource
    private ComposeDrawConfiguration composeDrawConfiguration;
    @Resource
    INumberService numberService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    RedisLockUtils redisLockUtils;

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
    public ResponsePageResult<UserNumbersVO> composeUserMaterial(Long userId, RequestConditionPage<UserMaterialReq> userMaterialReq) {
        Assert.notNull(userId, () -> new StarException("userId 为空"));
        Long categoryId = userMaterialReq.getCondition().getCategoryId();
        ComposeCategoryRes composeCategoryRes = composeService.composeCategoryByCategoryId(categoryId);
        List<ComposeMaterialDTO> composeMaterials = JSONUtil.toList(composeCategoryRes.getComposeMaterial(), ComposeMaterialDTO.class);
        //配置项中需要的合成主题id
        List<Long> themeIds = composeMaterials.stream().map(ComposeMaterialDTO::getThemeId).collect(Collectors.toList());
        //前端传入的合成主题id
        List<Long> filterArray = themeIds.stream().filter(item -> item.equals(userMaterialReq.getCondition().getThemeId())).collect(Collectors.toList());
        Assert.isTrue(!filterArray.isEmpty(),()->new StarException("所选素材不在合成类目中"));
        ResponsePageResult<UserNumbersVO> userNumbersVOResponsePageResult = filterArray.size() == 1 && filterArray.get(0) < 128 ?
                userThemeService.queryUserArticleNumberInfoBySeriesIds(userId, filterArray, UserNumberStatusEnum.PURCHASED, userMaterialReq.getPage(), userMaterialReq.getSize()) :
                userThemeService.queryUserArticleNumberInfoByThemeIds(userId, filterArray, UserNumberStatusEnum.PURCHASED, userMaterialReq.getPage(), userMaterialReq.getSize());

//        ResponsePageResult<UserNumbersVO> userNumbersVOResponsePageResult = userThemeService.queryUserArticleNumberInfoByThemeIds(userId, filterArray,
//                UserNumberStatusEnum.PURCHASED, userMaterialReq.getPage(), userMaterialReq.getSize());
        //  Map<Long, List<UserNumbersVO>> collect = userHaveNumbers.stream().collect(Collectors.groupingBy(UserNumbersVO::getThemeId));
        return userNumbersVOResponsePageResult;
    }

    @Override
    @Transactional
    public ComposeManageRes composeManage(ComposeManageReq composeManageReq) {
        ComposeRes composeRes = composeService.composeDetails(composeManageReq.getComposeId());
         Assert.isTrue(composeRes.getComposeStatus().equals(ComposeStatusEnum.Running.getCode()),()->new StarException("合成不在开启状态"));
        //校验合成时间


        Assert.isTrue(new Date().after(composeRes.getStartedAt()),() -> new StarException(StarError.COMPOST_TIME_NOT_START));
        Assert.isTrue(new Date().before(composeRes.getEndAt()),() -> new StarException(StarError.COMPOST_TIME_IS_END));

         //true可执行
//        boolean openCompose = redisUtil.hasKey(String.format(RedisKey.OPEN_COMPOSE.getKey(), composeManageReq.getComposeId()));
//        if (!openCompose){
//            throw new StarException(StarError.COMPOSE_PRIZE_EXIST);
//        }
        //        boolean canCompose = redisUtil.sHasKey(String.format(RedisKey.GOLD_COMPOSE.getKey(),composeManageReq.getComposeId()), composeManageReq.getUserId());
//        //true 不可执行
//        boolean composeSuccess = redisUtil.sHasKey(String.format(RedisKey.GOLD_COMPOSE_SUCCESS.getKey(),composeManageReq.getComposeId()), composeManageReq.getUserId());
//
//        if (canCompose == false|| composeSuccess){
//            throw new StarException(StarError.COMPOSE_PRIZE_EXIST);
//        }

        ComposeCategoryRes composeCategoryRes = composeService.composeCategoryByCategoryId(composeManageReq.getCategoryId());
        //积分判断操作
        Assert.isTrue(composeCategoryRes.getComposeId().equals(composeManageReq.getComposeId()), () -> new StarException("请选择正确的合成类目"));
        if (composeCategoryRes.getIsScore()) {
            ScoreDTO subScoreDTO = getScoreDTO(composeManageReq, composeCategoryRes);
            iScopeCore.userScopeManageSub(subScoreDTO);
        }
        List<ComposeMaterialDTO> composeMaterials = JSONUtil.toList(composeCategoryRes.getComposeMaterial(), ComposeMaterialDTO.class);

        List<ComposeUserArticleNumberDTO> composeUserNumberArrays = composeMaterials.size() == 1 && composeMaterials.get(0).getThemeId() < 128 ?
                composeManageService.queryUserArticleNumberInfoBySeriesNumberIds(composeManageReq.getUserId(),composeManageReq.getSourceIds(),UserNumberStatusEnum.PURCHASED)
                :composeManageService.queryUserArticleNumberInfoByNumberIds(composeManageReq.getUserId(), composeManageReq.getSourceIds(), UserNumberStatusEnum.PURCHASED);


        Assert.isTrue(composeManageReq.getSourceIds().size() == composeUserNumberArrays.size(), () -> new StarException("不能操作非本人物品"));
        // TODO: 2022/9/8 优化
        Map<Long, List<ComposeUserArticleNumberDTO>> collect = composeUserNumberArrays.stream().collect(Collectors.groupingBy(ComposeUserArticleNumberDTO::getThemeId));
        Map<Long, ComposeMaterialDTO> composeMaterialDTOMap = composeMaterials.stream().collect(Collectors.toMap(ComposeMaterialDTO::getThemeId, Function.identity()));
        checkThemeCounts(collect, composeMaterialDTOMap);

        //抽取  合成物品  抽奖
        ComposeDrawLotteryStrategy composeDrawLotteryStrategy = applicationContext
                .getBean(ComposeDrawLotteryStrategyEnums
                        .getScaleLotteryStrategy(composeDrawConfiguration.getComposeDraw())
                        .getStrategy(), ComposeDrawLotteryStrategy.class
                );
//        //获取随机合成参数
        List<ComposePrizeRes> composePrizeRes = composeService.composePrizeByComposeId(composeManageReq.getComposeId());
        List<ComposePrizeDTO> composePrizeDTOS = BeanColverUtil.colverList(composePrizeRes, ComposePrizeDTO.class);
//        //获得随机合成物品
        ComposePrizeDTO composePrizeDTO = composeDrawLotteryStrategy.drawPrize(composePrizeDTOS);
//        //随机合成的处理bean
        ComposePrizeStrategy composePrizeStrategy = applicationContext.getBean(ComposePrizeTypeEnums.getComposePrizeType(composePrizeDTO.getPrizeType()).getStrategy(), ComposePrizeStrategy.class);
//        //下方对素材的处理
        log.info("销毁的素材id：「{}」", JSONUtil.toJsonStr(composeManageReq.getSourceIds()));
        List<Long> userNumberIds = composeUserNumberArrays.stream().map(item -> item.getNumberId()).collect(Collectors.toList());

        List<NumberCirculationAddDTO> numberCirculations = getNumberCirculations(composeUserNumberArrays, composeManageReq);

//        String lockKey = String.format(RedisKey.USER_COMPOSE_ING.getKey(),composeManageReq.getUserId());
//        Boolean lock = redisLockUtils.lock(lockKey,RedisKey.USER_COMPOSE_ING.getTimeUnit().toSeconds(RedisKey.USER_COMPOSE_ING.getTime()));
//        Assert.isTrue(lock, () -> new StarException(StarError.SYSTEM_ERROR,"正在合成中，请稍等"));
//        try{
            //        //执行商品合成操作
            PrizeRes prizeRes = composePrizeStrategy.composePrize(composeManageReq, composePrizeDTO);
            //修改藏品的状态 销毁编号
            Boolean modifyStatus = composeManageService.composeModifyUserNumberStatus(composeManageReq.getUserId(), userNumberIds, UserNumberStatusEnum.PURCHASED, UserNumberStatusEnum.DESTROY, NumberStatusEnum.SOLD, NumberStatusEnum.DESTROY, Boolean.TRUE);
            //物品流转状态
            Boolean circulationStatus = numberService.saveBatchNumberCirculationRecord(numberCirculations);
            // 记录合成保存
            Boolean recordStatus = composeManageService.saveComposeRecord(getComposeRecordDTO(composeManageReq, composeCategoryRes, composeUserNumberArrays, prizeRes));
//        //封装返回数据
            log.info("prize:{}",prizeRes);
            log.info("modifyStatus:{}",modifyStatus);
            log.info("recordStatus:{}",recordStatus);
            log.info("circulationStatus:{}",circulationStatus);
            Assert.isTrue(modifyStatus && circulationStatus && recordStatus, () -> new StarException("合成失败"));
            ComposeManageRes composeManageRes = new ComposeManageRes();
            composeManageRes.setIsSuccess(Boolean.TRUE);
            composeManageRes.setPrizeRes(prizeRes);

//            redisUtil.sSet(String.format(RedisKey.GOLD_COMPOSE_SUCCESS.getKey(),composeManageReq.getComposeId()),composeManageReq.getUserId());

            //先默认销毁
            return composeManageRes;
//        }finally {
//            redisLockUtils.unlock(lockKey);
//        }

    }

    private ComposeRecordDTO getComposeRecordDTO(ComposeManageReq composeManageReq, ComposeCategoryRes composeCategoryRes, List<ComposeUserArticleNumberDTO> composeUserNumberArrays, PrizeRes prizeRes) {
        ComposeRecordDTO composeRecordDTO = new ComposeRecordDTO();
        composeRecordDTO.setComposeId(composeManageReq.getComposeId());
        composeRecordDTO.setCategoryId(composeManageReq.getCategoryId());
        composeRecordDTO.setPrizeMessage(prizeRes);
        composeRecordDTO.setUserId(composeManageReq.getUserId());
        composeRecordDTO.setIsScope(composeCategoryRes.getIsScore());
        composeRecordDTO.setScopeNumber(composeCategoryRes.getComposeScopeNumber());
        composeRecordDTO.setPrizeType(prizeRes.getPrizeType());
        composeRecordDTO.setSource(composeUserNumberArrays);
        return composeRecordDTO;
    }


    private List<NumberCirculationAddDTO> getNumberCirculations(List<ComposeUserArticleNumberDTO> composeUserArticleNumberDTOArrays, ComposeManageReq composeManageReq) {
        List<NumberCirculationAddDTO> numberCirculations = composeUserArticleNumberDTOArrays.stream().map(item -> NumberCirculationAddDTO.builder()
                .numberId(item.getNumberId())
                .type(NumberCirculationTypeEnum.CASTING)
                .uid(composeManageReq.getUserId())
                .beforePrice(item.getPrice())
                .afterPrice(BigDecimal.ZERO)
                .build()
        ).collect(Collectors.toList());
        return numberCirculations;
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

    private void checkThemeCounts(Map<Long, List<ComposeUserArticleNumberDTO>> collect, Map<Long, ComposeMaterialDTO> composeMaterialDTOMap) {
        for (Long themeId : composeMaterialDTOMap.keySet()) {
            ComposeMaterialDTO composeMaterialDTO = composeMaterialDTOMap.get(themeId);
            Integer number = composeMaterialDTO.getNumber();
            Optional.ofNullable(collect.get(themeId)).orElseThrow(() -> new StarException("合成数量不足"));
            int size = collect.get(themeId).size();
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
