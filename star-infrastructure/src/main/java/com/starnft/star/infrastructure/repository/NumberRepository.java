package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.numbers.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.numbers.model.dto.NumberCirculationDTO;
import com.starnft.star.domain.numbers.model.dto.NumberQueryDTO;
import com.starnft.star.domain.numbers.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.numbers.model.req.NumberReq;
import com.starnft.star.domain.numbers.model.vo.NumberDetailVO;
import com.starnft.star.domain.numbers.model.vo.NumberVO;
import com.starnft.star.domain.numbers.repository.INumberRepository;
import com.starnft.star.infrastructure.entity.number.StarNftNumberCirculationHist;
import com.starnft.star.infrastructure.entity.number.StarNftThemeNumber;
import com.starnft.star.infrastructure.mapper.number.StarNftNumberCirculationHistMapper;
import com.starnft.star.infrastructure.mapper.number.StarNftThemeNumberMapper;
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
        pageResult.setSize(result.getSize());
        return pageResult;
    }

    @Override
    public NumberDetailVO getNumberDetail(Long id) {
        return this.starNftThemeNumberMapper.selectNumberDetailId(id);
    }

    @Override
    public ResponsePageResult<NumberVO> listNumber(NumberQueryDTO param) {
        PageInfo<NumberVO> result = PageMethod.startPage(param.getPage(), param.getSize())
                .doSelectPageInfo(() -> this.starNftThemeNumberMapper.selectNumberList(param));
        return new ResponsePageResult<>(result.getList(), result.getPageNum(), result.getSize(), result.getTotal());
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
}
