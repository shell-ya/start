package com.starnft.star.domain.number.serivce.impl;

import com.starnft.star.common.enums.NumberCirculationTypeEnum;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.enums.SortTypeEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.number.model.OrderByEnum;
import com.starnft.star.domain.number.model.dto.NumberCirculationAddDTO;
import com.starnft.star.domain.number.model.dto.NumberCirculationDTO;
import com.starnft.star.domain.number.model.dto.NumberQueryDTO;
import com.starnft.star.domain.number.model.dto.NumberUpdateDTO;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.req.NumberReq;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.repository.INumberRepository;
import com.starnft.star.domain.number.serivce.INumberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class NumberServiceImpl implements INumberService {
    @Resource
    INumberRepository numberRepository;

    @Override
    public ResponsePageResult<NumberVO> queryThemeNumber(NumberReq numberReq) {
        return this.numberRepository.queryNumber(numberReq);
    }

    @Override
    public NumberDetailVO getNumberDetail(Long id) {
        Assert.notNull(id, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "商品ID不能为空"));
        NumberDetailVO number = this.numberRepository.getNumberDetail(id);
        Assert.notNull(number, () -> new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "商品不存在"));
        return number;
    }

    @Override
    public ResponsePageResult<NumberVO> listNumber(RequestConditionPage<NumberQueryRequest> request) {
        NumberQueryRequest condition = Optional.ofNullable(request.getCondition()).orElse(new NumberQueryRequest());
        return this.numberRepository.listNumber(
                NumberQueryDTO
                        .builder()
                        .seriesId(condition.getSeriesId())
                        .themeId(condition.getThemeId())
                        .themeType(condition.getThemeType())
                        .themeName(condition.getThemeName())
                        .orderBy(OrderByEnum.getOrderBy(condition.getOrderBy()).getValues())
                        .sortType(SortTypeEnum.getSortTypeEnum(condition.getSortType()).getValue())
                        .page(request.getPage())
                        .size(request.getSize())
                        .build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean consignment(Long uid, NumberConsignmentRequest request) {

        // 判断用户是否是该藏品的拥有者以及藏品是否存在
        NumberDetailVO numberDetail = this.checkNumberOwner(uid, request.getNumberId());

        // 判断商品是否已经在寄售中
        if (Objects.equals(NumberStatusEnum.ON_CONSIGNMENT.getCode(), numberDetail.getStatus())) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "该藏品已经是寄售状态");
        }
        // 修改商品价格和状态
        Boolean updBool = this.numberRepository.modifyNumberPriceAndStatus(
                NumberUpdateDTO.builder()
                        .uid(uid)
                        .numberId(request.getNumberId())
                        .price(request.getPrice())
                        .status(NumberStatusEnum.ON_CONSIGNMENT)
                        .build());

        // 保存寄售记录
        Boolean saveBool = this.numberRepository.saveNumberCirculationRecord(
                NumberCirculationAddDTO.builder()
                        .uid(uid)
                        .numberId(request.getNumberId())
                        .type(NumberCirculationTypeEnum.CONSIGNMENT)
                        .beforePrice(numberDetail.getPrice())
                        .afterPrice(request.getPrice())
                        .build());
        // 修改用户藏品状态
        Boolean updUserNumberBool = this.numberRepository.modifyUserNumberStatus(uid, request.getNumberId(), UserNumberStatusEnum.ON_CONSIGNMENT);

        if (updBool && saveBool && updUserNumberBool) {
            return Boolean.TRUE;
        }
        throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "寄售失败");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean consignmentCancel(Long uid, Long numberId) {
        Assert.notNull(numberId, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "商品ID不能为空"));

        // 判断用户是否是该藏品的拥有者以及藏品是否存在
        NumberDetailVO numberDetail = this.checkNumberOwner(uid, numberId);

        // 判断商品是否已经在寄售中
        if (!Objects.equals(NumberStatusEnum.ON_CONSIGNMENT.getCode(), numberDetail.getStatus())) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "该藏品不是是寄售状态 无法取消");
        }

        // 获取该商品最后一条寄售记录
        NumberCirculationDTO circulation = this.numberRepository.getLastConsignedCirculation(numberId);

        // 还原商品价格和状态
        Boolean updBool = this.numberRepository.modifyNumberPriceAndStatus(
                NumberUpdateDTO.builder()
                        .uid(uid)
                        .numberId(numberId)
                        .price(circulation.getBeforePrice())
                        .status(NumberStatusEnum.NOT_CONSIGNED)
                        .build());

        // 保存取消寄售记录
        Boolean saveBool = this.numberRepository.saveNumberCirculationRecord(
                NumberCirculationAddDTO.builder()
                        .uid(uid)
                        .numberId(numberId)
                        .type(NumberCirculationTypeEnum.CANCEL_CONSIGNMENT)
                        .beforePrice(numberDetail.getPrice())
                        .afterPrice(circulation.getBeforePrice())
                        .build());

        // 还原用户藏品状态
        Boolean updUserNumberBool = this.numberRepository.modifyUserNumberStatus(uid, numberId, UserNumberStatusEnum.PURCHASED);

        if (updBool && saveBool && updUserNumberBool) {
            return Boolean.TRUE;
        }
        throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "取消寄售失败");
    }

    private NumberDetailVO checkNumberOwner(Long uid, Long numberId) {
        NumberDetailVO numberDetail = this.numberRepository.getNumberDetail(numberId);
        if (Objects.isNull(numberDetail)) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "该藏品不存在 无法进行相关操作");
        }
        if (!Objects.equals(String.valueOf(uid), numberDetail.getOwnerBy())) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "你不是该藏品的拥有者 无法进行相关操作");
        }
        return numberDetail;
    }
}
