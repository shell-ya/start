package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.dto.NumberCirculationDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.model.vo.UserThemeMappingVO;
import com.starnft.star.domain.number.repository.INumberRepository;
import com.starnft.star.infrastructure.entity.number.StarNftNumberCirculationHist;
import com.starnft.star.infrastructure.entity.number.StarNftThemeNumber;
import com.starnft.star.infrastructure.entity.user.StarNftUserTheme;
import com.starnft.star.infrastructure.mapper.number.StarNftNumberCirculationHistMapper;
import com.starnft.star.infrastructure.mapper.number.StarNftThemeNumberMapper;
import com.starnft.star.infrastructure.mapper.user.StarNftUserThemeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class NumberRepository implements INumberRepository {
    @Resource
    StarNftThemeNumberMapper starNftThemeNumberMapper;
    @Resource
    StarNftNumberCirculationHistMapper starNftNumberCirculationHistMapper;
    @Resource
    StarNftUserThemeMapper starNftUserThemeMapper;

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
    public Boolean modifyNumberPriceAndStatus(NumberUpdateDTO param) {
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
    public Boolean modifyUserNumberStatus(Long uid, Long numberId, UserNumberStatusEnum statusEnum) {
        return this.starNftUserThemeMapper.update(StarNftUserTheme.builder()
                        .status(statusEnum.getCode())
                        .updateAt(new Date())
                        .updateBy(String.valueOf(uid))
                        .build(),
                Wrappers.lambdaUpdate(StarNftUserTheme.class)
                        .eq(StarNftUserTheme::getSeriesThemeId, numberId)
                        .eq(StarNftUserTheme::getUserId, uid)) == 1;
    }

    @Override
    public ThemeNumberVo getConsignNumber(Long id) {
        return this.starNftThemeNumberMapper.selectConsignThemeNumberDetail(id);
    }

    @Override
    public boolean modifyNumberStatus(Long numberId, Long uid, Integer status) {
        LambdaQueryWrapper<StarNftThemeNumber> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(numberId), StarNftThemeNumber::getId, numberId);

        StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
        starNftThemeNumber.setStatus(status);

        return starNftThemeNumberMapper.update(starNftThemeNumber, wrapper) == 1;
    }

    @Override
    public boolean createUserNumberMapping(UserThemeMappingVO userThemeMappingVO) {
        StarNftUserTheme userTheme = BeanColverUtil.colver(userThemeMappingVO, StarNftUserTheme.class);
        userTheme.setId(SnowflakeWorker.generateId());
        userTheme.setSeriesThemeInfoId(userThemeMappingVO.getSeriesThemeInfoId());
        userTheme.setCreateAt(new Date());
        userTheme.setSource(userThemeMappingVO.getSource());
        userTheme.setCreateBy(userThemeMappingVO.getUserId());
        userTheme.setIsDelete(Boolean.FALSE);
        return starNftUserThemeMapper.insert(userTheme) == 1;
    }

    @Override
    public ThemeNumberVo queryNumberExist(Integer themeNumber, Long themeId) {
        LambdaQueryWrapper<StarNftThemeNumber> wrapper = new LambdaQueryWrapper<>();
        StarNftThemeNumber starNftThemeNumber = starNftThemeNumberMapper.selectOne(wrapper.eq(Objects.nonNull(themeNumber), StarNftThemeNumber::getThemeNumber, themeNumber)
                .eq(Objects.nonNull(themeId), StarNftThemeNumber::getSeriesThemeInfoId, themeId));
        return copyToVO(starNftThemeNumber);
    }

    private ThemeNumberVo copyToVO(StarNftThemeNumber starNftThemeNumber) {
        ThemeNumberVo themeNumberVo = BeanColverUtil.colver(starNftThemeNumber, ThemeNumberVo.class);
        themeNumberVo.setNumberId(starNftThemeNumber.getId());
        themeNumberVo.setThemeInfoId(starNftThemeNumber.getSeriesThemeInfoId());
        return themeNumberVo;
    }
}
