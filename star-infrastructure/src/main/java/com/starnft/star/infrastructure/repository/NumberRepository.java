package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.number.model.dto.*;
import com.starnft.star.domain.number.model.req.MarketNumberListReq;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.*;
import com.starnft.star.domain.number.repository.INumberRepository;
import com.starnft.star.infrastructure.entity.number.StarNftNumberCirculationHist;
import com.starnft.star.infrastructure.entity.number.StarNftShelvesRecord;
import com.starnft.star.infrastructure.entity.number.StarNftThemeNumber;
import com.starnft.star.infrastructure.entity.user.StarNftUserTheme;
import com.starnft.star.infrastructure.mapper.number.StarNFtShelvesRecordMapper;
import com.starnft.star.infrastructure.mapper.number.StarNftNumberCirculationHistMapper;
import com.starnft.star.infrastructure.mapper.number.StarNftThemeNumberMapper;
import com.starnft.star.infrastructure.mapper.user.StarNftUserThemeMapper;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class NumberRepository implements INumberRepository {
    @Resource
    StarNftThemeNumberMapper starNftThemeNumberMapper;
    @Resource
    StarNftNumberCirculationHistMapper starNftNumberCirculationHistMapper;
    @Resource
    StarNftUserThemeMapper starNftUserThemeMapper;
    @Resource
    StarNFtShelvesRecordMapper shelvesRecordMapper;

    @Resource
    TransactionTemplate template;

    @Override
    public ResponsePageResult<NumberVO> queryNumber(NumberReq numberReq) {
        QueryWrapper<StarNftThemeNumber> wrapper = new QueryWrapper<StarNftThemeNumber>()
                .eq(StarNftThemeNumber.COL_IS_DELETE, Boolean.FALSE)
                .eq(StarNftThemeNumber.COL_SERIES_THEME_INFO_ID, numberReq.getId());
        if (Boolean.TRUE.equals(numberReq.getIsSell())) {
            wrapper.in(StarNftThemeNumber.COL_STATUS, Arrays.asList(0, 3));
        }
        wrapper.orderBy(Objects.nonNull(numberReq.getOrderBy()),
                numberReq.getUpOrDown(), numberReq.getOrderBy().getValues());
        PageInfo<StarNftThemeNumber> result = PageMethod.startPage(numberReq.getPage(), numberReq.getSize())
                .doSelectPageInfo(() -> this.starNftThemeNumberMapper.selectList(wrapper));
        List<NumberVO> list = result.getList().stream()
                .map(item ->
                        NumberVO
                                .builder()
                                .id(item.getId())
                                .identification(item.getChainIdentification())
                                .number(item.getThemeNumber())
                                .price(item.getPrice())
                                .status(item.getStatus())
                                .build()
                )
                .collect(Collectors.toList());
        ResponsePageResult<NumberVO> pageResult = new ResponsePageResult<>();
        pageResult.setPage(result.getPageNum());
        pageResult.setList(list);
        pageResult.setTotal(result.getTotal());
        pageResult.setSize(result.getPageSize());
        return pageResult;
    }

    @Override
    public NumberDetailVO getNumberDetail(Long id) {
        return this.starNftThemeNumberMapper.selectNumberDetailById(id);
    }

    @Override
    public ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param) {
        PageInfo<NumberVO> result = PageMethod.startPage(param.getPage(), param.getSize())
                .doSelectPageInfo(() -> this.starNftThemeNumberMapper.selectNumberList(param));
        return new ResponsePageResult<>(result.getList(), result.getPageNum(), result.getPageSize(), result.getTotal());
    }

    @Override
    public Boolean modifyNumberInfo(NumberUpdateDTO param) {
        return this.starNftThemeNumberMapper.updateById(
                StarNftThemeNumber.builder()
                        .id(param.getNumberId())
                        .price(param.getPrice())
                        .status(param.getStatus().getCode())
                        .updateAt(new Date())
                        .updateBy(String.valueOf(param.getUid()))
                        .build()) == 1;
    }

    @Override
    public Boolean modifyBatchNumberInfo(NumberBatchUpdateDTO param) {
        LambdaQueryWrapper<StarNftThemeNumber> wrapper = new LambdaQueryWrapper<StarNftThemeNumber>()
                .in(StarNftThemeNumber::getId, param.getNumberIds());
        StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
        starNftThemeNumber.setStatus(param.getStatus().getCode());
        starNftThemeNumber.setIsDelete(param.getIsDeleted());
        starNftThemeNumber.setPrice(param.getPrice());
        Optional.ofNullable(param.getUid()).ifPresent((item) -> {
            starNftThemeNumber.setOwnerBy(item.toString());
        });

        return this.starNftThemeNumberMapper.update(starNftThemeNumber, wrapper) == param.getNumberIds().size();
    }

    @Override
    public Boolean comsumeNumber(Long userId, Long numberId) {
        return template.execute(status -> {
            LambdaQueryWrapper<StarNftThemeNumber> queryWrapper = new LambdaQueryWrapper<>();
            StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
            starNftThemeNumber.setOwnerBy("-1");
            int isSuccess = starNftThemeNumberMapper.update(starNftThemeNumber, queryWrapper.eq(StarNftThemeNumber::getId, numberId));
            LambdaQueryWrapper<StarNftUserTheme> utqueryWrapper = new LambdaQueryWrapper<>();
            StarNftUserTheme starNftUserTheme = new StarNftUserTheme();
            starNftUserTheme.setIsDelete(Boolean.TRUE);
            int update1 = starNftUserThemeMapper.update(starNftUserTheme,
                    utqueryWrapper.eq(StarNftUserTheme::getSeriesThemeId, numberId).eq(StarNftUserTheme::getUserId, userId));
            return isSuccess + update1 == 2;
        });
    }

    @Override
    public Boolean isOwner(Long userId, Long themeId, Long numberId) {
        StarNftThemeNumber starNftThemeNumber = getStarNftThemeNumber(userId, themeId, numberId);

        return starNftThemeNumber != null;
    }

    private StarNftThemeNumber getStarNftThemeNumber(Long userId, Long themeId, Long numberId) {
        LambdaQueryWrapper<StarNftThemeNumber> queryWrapper = new LambdaQueryWrapper<>();

        StarNftThemeNumber starNftThemeNumber = starNftThemeNumberMapper.selectOne(queryWrapper.eq(StarNftThemeNumber::getId, numberId)
                .eq(StarNftThemeNumber::getSeriesThemeInfoId, themeId)
                .eq(StarNftThemeNumber::getOwnerBy, userId));
        return starNftThemeNumber;
    }

    @Override
    public Integer isOnSell(Long userId, Long themeId, Long numberId) {
        StarNftThemeNumber starNftThemeNumber = getStarNftThemeNumber(userId, themeId, numberId);
        if (starNftThemeNumber != null) {
            return starNftThemeNumber.getStatus();
        }
        return -1;
    }

    @Override
    public Boolean saveNumberCirculationRecord(NumberCirculationAddDTO numberCirculation) {
        return this.starNftNumberCirculationHistMapper.insert(
                StarNftNumberCirculationHist.builder()
                        .numberId(numberCirculation.getNumberId())
                        .beforePrice(numberCirculation.getBeforePrice())
                        .afterPrice(numberCirculation.getAfterPrice())
                        .type(numberCirculation.getType().getCode())
                        .createAt(new Date())
                        .createBy(String.valueOf(numberCirculation.getUid()))
                        .updateAt(new Date())
                        .updateBy(String.valueOf(numberCirculation.getUid()))
                        .isDelete(Boolean.FALSE)
                        .build()) == 1;
    }

    @Override
    public Boolean saveBatchNumberCirculationRecord(List<NumberCirculationAddDTO> numberCirculations) {
        List<StarNftNumberCirculationHist> collect = numberCirculations.stream().map(numberCirculation -> StarNftNumberCirculationHist.builder()
                .numberId(numberCirculation.getNumberId())
                .beforePrice(numberCirculation.getBeforePrice())
                .afterPrice(numberCirculation.getAfterPrice())
                .type(numberCirculation.getType().getCode())
                .createAt(new Date())
                .createBy(String.valueOf(numberCirculation.getUid()))
                .updateAt(new Date())
                .updateBy(String.valueOf(numberCirculation.getUid()))
                .isDelete(Boolean.FALSE)
                .build()

        ).collect(Collectors.toList());
        this.starNftNumberCirculationHistMapper.saveBatchNumberCirculationRecord(collect);
        return Boolean.TRUE;
    }

    @Override
    public NumberCirculationDTO getLastConsignedCirculation(Long numberId) {
        StarNftNumberCirculationHist hist = this.starNftNumberCirculationHistMapper.selectOne(
                Wrappers.lambdaQuery(StarNftNumberCirculationHist.class)
                        .eq(StarNftNumberCirculationHist::getNumberId, numberId)
                        .eq(StarNftNumberCirculationHist::getType, NumberCirculationTypeEnum.CONSIGNMENT.getCode())
                        .orderByDesc(StarNftNumberCirculationHist::getCreateAt)
                        .last("limit 1"));
        return NumberCirculationDTO.builder()
                .numberId(hist.getNumberId())
                .beforePrice(hist.getBeforePrice())
                .afterPrice(hist.getAfterPrice())
                .build();
    }

    @Override
    public ThemeNumberVo getConsignNumber(NumberDTO dto) {
        return this.starNftThemeNumberMapper.selectConsignThemeNumberDetail(dto);
    }

    @Override
    public ThemeNumberVo selectRandomThemeNumber(Long id) {
        return this.starNftThemeNumberMapper.selectRandomThemeNumber(id);
    }

    @Override
    public boolean modifyNumberStatus(Long numberId, Long uid, Integer status) {
        LambdaQueryWrapper<StarNftThemeNumber> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(numberId), StarNftThemeNumber::getId, numberId);

        StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
        starNftThemeNumber.setStatus(status);
        starNftThemeNumber.setOwnerBy(String.valueOf(uid));

        return this.starNftThemeNumberMapper.update(starNftThemeNumber, wrapper) == 1;
    }

    @Override
    public boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO) {
        StarNftUserTheme userTheme = BeanColverUtil.colver(userThemeMappingVO, StarNftUserTheme.class);
        userTheme.setId(SnowflakeWorker.generateId());
        userTheme.setSeriesThemeInfoId(userThemeMappingVO.getSeriesThemeInfoId());
        userTheme.setCreateAt(new Date());
        userTheme.setSource(userThemeMappingVO.getSource());
        userTheme.setCreateBy(userThemeMappingVO.getUserId());
        userTheme.setBuyPrice(userThemeMappingVO.getBuyPrice());
        userTheme.setIsDelete(Boolean.FALSE);
        return this.starNftUserThemeMapper.insert(userTheme) == 1;
    }

    @Override
    public ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId) {
        LambdaQueryWrapper<StarNftThemeNumber> wrapper = new LambdaQueryWrapper<>();
        StarNftThemeNumber starNftThemeNumber = this.starNftThemeNumberMapper.selectOne(wrapper.eq(Objects.nonNull(themeNumber), StarNftThemeNumber::getThemeNumber, themeNumber)
                .eq(Objects.nonNull(themeId), StarNftThemeNumber::getSeriesThemeInfoId, themeId));
        return this.copyToVO(starNftThemeNumber);
    }

    @Override
    public boolean updateUserNumberMapping(UserThemeMappingVO updateThemeMappingVo) {
        return this.starNftUserThemeMapper.updateUserThemeMapping(updateThemeMappingVo);
    }

    @Override
    public List<Integer> loadNotSellNumberNumCollection(Long themeId) {
        List<StarNftThemeNumber> starNftThemeNumbers = this.starNftThemeNumberMapper.selectList(
                new LambdaQueryWrapper<StarNftThemeNumber>()
                        .eq(Objects.nonNull(themeId), StarNftThemeNumber::getSeriesThemeInfoId, themeId)
                        .isNull(StarNftThemeNumber::getOwnerBy));
        return starNftThemeNumbers.stream().map(po -> po.getThemeNumber().intValue()).collect(Collectors.toList());
    }

    @Override
    public List<NumberVO> loadNotSellNumberCollection(Long themeId) {
        List<StarNftThemeNumber> starNftThemeNumbers = this.starNftThemeNumberMapper.selectList(
                new LambdaQueryWrapper<StarNftThemeNumber>()
                        .eq(Objects.nonNull(themeId), StarNftThemeNumber::getSeriesThemeInfoId, themeId)
                        .isNull(StarNftThemeNumber::getOwnerBy));

        return BeanColverUtil.colverList(starNftThemeNumbers, NumberVO.class);
    }

    @Override
    public List<NumberVO> getNumberListByThemeInfoId(NumberQueryDTO numberQueryDTO) {

        List<NumberVO> numberListByThemeInfo = this.starNftThemeNumberMapper.getNumberListByThemeInfo(numberQueryDTO);
        return numberListByThemeInfo;

    }

    @Override
    public Boolean thirdPlatSelling(Long userId, Long seriesThemeId) {
        LambdaQueryWrapper<StarNftShelvesRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StarNftShelvesRecord::getUserId, userId);
        wrapper.eq(StarNftShelvesRecord::getSeriesThemeId, seriesThemeId);
        wrapper.eq(StarNftShelvesRecord::getStatus, 0);
        wrapper.eq(StarNftShelvesRecord::getIsDeleted, Boolean.FALSE);
        StarNftShelvesRecord starNftShelvesRecord = this.shelvesRecordMapper.selectOne(wrapper);
        return Objects.nonNull(starNftShelvesRecord);
    }

    @Override
    public List<NumberDingVO> getNumberDingList() {
        return this.starNftThemeNumberMapper.getNumbers2Ding();
    }

    @Override
    public Long firstNumber(Long uid, Long seriesThemeInfoId) {
        return this.starNftUserThemeMapper.firstNumber(uid, seriesThemeInfoId);
    }

    @Override
    public Boolean deleteNumber(Long uid, Long seriesThemeId) {

        return template.execute(status -> {
            LambdaQueryWrapper<StarNftThemeNumber> queryWrapper = new LambdaQueryWrapper<>();
            StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
            starNftThemeNumber.setOwnerBy("");
            starNftThemeNumber.setStatus(0);
            int isSuccess = starNftThemeNumberMapper.update(starNftThemeNumber, queryWrapper.eq(StarNftThemeNumber::getId, seriesThemeId));
            LambdaQueryWrapper<StarNftUserTheme> utqueryWrapper = new LambdaQueryWrapper<>();
            StarNftUserTheme starNftUserTheme = new StarNftUserTheme();
            starNftUserTheme.setIsDelete(Boolean.TRUE);
            starNftThemeNumber.setStatus(3);
            int update1 = starNftUserThemeMapper.update(starNftUserTheme,
                    utqueryWrapper.eq(StarNftUserTheme::getSeriesThemeId, seriesThemeId).eq(StarNftUserTheme::getUserId, uid));
            return isSuccess + update1 == 2;
        });
    }

    @Override
    public List<ReNumberVo> queryReNumberList(Long themeId) {

        return starNftUserThemeMapper.queryReNumberList(themeId);
    }

    @Override
    public List<Long> queryHasReNumberUser(Long seriesThemeId) {
        return starNftUserThemeMapper.queryHasReNumberUser(seriesThemeId);
    }

    @Override
    public boolean deleteNumber2ReDraw(ReNumberVo numberVo, List<Long> ids) {
        return template.execute(status -> {
            //删除numebr
            LambdaQueryWrapper<StarNftThemeNumber> queryWrapper = new LambdaQueryWrapper<>();
            StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
            starNftThemeNumber.setOwnerBy("");
            starNftThemeNumber.setStatus(0);
            int isSuccess = starNftThemeNumberMapper.update(starNftThemeNumber, queryWrapper.eq(StarNftThemeNumber::getId, numberVo.getSeriesThemeId()));
            //删除user_theme
            LambdaQueryWrapper<StarNftUserTheme> utqueryWrapper = new LambdaQueryWrapper<>();
            StarNftUserTheme starNftUserTheme = new StarNftUserTheme();
            starNftUserTheme.setIsDelete(Boolean.TRUE);
            int update1 = starNftUserThemeMapper.update(starNftUserTheme,
                    utqueryWrapper.eq(StarNftUserTheme::getSeriesThemeId, numberVo.getSeriesThemeId()).in(StarNftUserTheme::getUserId, ids));

            return isSuccess + update1 == ids.size() + 1;
        });
    }


    @Override
    public List<NumberDetailVO> queryNumberNotOnSell(Long themeId) {
        LambdaQueryWrapper<StarNftThemeNumber> queryWrapper = new LambdaQueryWrapper<>();
        List<StarNftThemeNumber> starNftThemeNumbers = starNftThemeNumberMapper.selectList(queryWrapper.eq(StarNftThemeNumber::getSeriesThemeInfoId, themeId)
                .eq(StarNftThemeNumber::getStatus, 1));

        return mappingNumberValues(starNftThemeNumbers);
    }

    @Override
    public Boolean modifyNumberStatusVersion(Long id, Long userId, Integer code, Integer version) {
        return starNftThemeNumberMapper.updateNumberStatus(id, userId, code, version);

    }

    @Override
    public Integer queryThemeNumberOnSellCount(Long themeId) {
        return starNftThemeNumberMapper.countPublishNumber(themeId);
    }

    @Override
    public BigDecimal minPrice(Long themeId) {
        return starNftThemeNumberMapper.minPrice(themeId);
    }

    @Override
    public ResponsePageResult<MarketNumberInfoVO> marketNumberList(MarketNumberListReq marketNumberListReq) {
        PageInfo<MarketNumberInfoVO> marketNumList = PageHelper.startPage(marketNumberListReq.getPage(), marketNumberListReq.getSize())
                .doSelectPageInfo(() -> this.starNftThemeNumberMapper.marketNumberList(marketNumberListReq));

        return new ResponsePageResult<>(marketNumList.getList(), marketNumList.getPageNum(), marketNumList.getPageSize(), marketNumList.getTotal());
    }

    private List<NumberDetailVO> mappingNumberValues(List<StarNftThemeNumber> starNftThemeNumbers) {
        ArrayList<@Nullable NumberDetailVO> objects = Lists.newArrayList();
        for (StarNftThemeNumber starNftThemeNumber : starNftThemeNumbers) {
            NumberDetailVO numberDetailVO = new NumberDetailVO();
            BeanUtils.copyProperties(starNftThemeNumber, numberDetailVO);
            numberDetailVO.setThemeId(starNftThemeNumber.getSeriesThemeInfoId());
            objects.add(numberDetailVO);
        }

        return objects;
    }

    private ThemeNumberVo copyToVO(StarNftThemeNumber starNftThemeNumber) {
        ThemeNumberVo themeNumberVo = BeanColverUtil.colver(starNftThemeNumber, ThemeNumberVo.class);
        themeNumberVo.setNumberId(starNftThemeNumber.getId());
        themeNumberVo.setThemeInfoId(starNftThemeNumber.getSeriesThemeInfoId());

        return themeNumberVo;
    }
}
