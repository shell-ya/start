package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.enums.CommodityTypeEnum;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.repository.ISeriesRepository;
import com.starnft.star.infrastructure.entity.series.StarNftSeries;
import com.starnft.star.infrastructure.mapper.series.StarNftSeriesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class SeriesRepository implements ISeriesRepository {
    @Resource
    StarNftSeriesMapper starNftSeriesMapper;

    @Override
    public ResponsePageResult<SeriesVO> querySeries(SeriesReq requestPage) {
        PageInfo<StarNftSeries> result = PageHelper.startPage(requestPage.getPage(), requestPage.getSize())
                .doSelectPageInfo(() -> {
                            this.starNftSeriesMapper.selectList(new QueryWrapper<StarNftSeries>()
                                    .eq(StarNftSeries.COL_IS_DELETE, Boolean.FALSE));
                        }
                );
        List<SeriesVO> list = result.getList().stream()
                .map(item ->
                        SeriesVO
                                .builder()
                                .id(item.getId())
                                .seriesName(item.getSeriesName())
                                .seriesType(item.getSeriesType())
                                .seriesImages(item.getSeriesImages())
                                .seriesStatus(item.getSeriesStatus())
                                .seriesModels(item.getSeriesModels())
                                .build()
                )
                .collect(Collectors.toList());
        ResponsePageResult<SeriesVO> pageResult = new ResponsePageResult<>();
        pageResult.setPage(result.getPageNum());
        pageResult.setList(list);
        pageResult.setTotal(result.getTotal());
        pageResult.setSize(result.getSize());
        return pageResult;
    }

    @Override
    public List<SeriesVO> querySeries(CommodityTypeEnum commodityType) {
        List<StarNftSeries> starNftSeries = this.starNftSeriesMapper.selectList(
                Wrappers.lambdaQuery(StarNftSeries.class)
                        .eq(StarNftSeries::getSeriesType, commodityType.getType())
                        .eq(StarNftSeries::getIsDelete, Boolean.FALSE));
        return starNftSeries.stream()
                .map(item ->
                        SeriesVO
                                .builder()
                                .id(item.getId())
                                .seriesName(item.getSeriesName())
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public SeriesVO querySeriesById(Long id) {

        StarNftSeries starNftSeries = starNftSeriesMapper.selectById(id);
        return BeanColverUtil.colver(starNftSeries, SeriesVO.class);

    }
}
