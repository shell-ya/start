package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.series.model.req.SeriesReq;
import com.starnft.star.domain.series.model.vo.SeriesVO;
import com.starnft.star.domain.series.repository.ISeriesRepository;
import com.starnft.star.infrastructure.entity.series.StarNftSeries;
import com.starnft.star.infrastructure.mapper.series.StarNftSeriesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class SeriesRepository implements ISeriesRepository {
    @Resource
    StarNftSeriesMapper starNftSeriesMapper;
    @Override
    public ResponsePageResult<SeriesVO> querySeries(SeriesReq requestPage) {
        PageInfo<SeriesVO> result = PageHelper.startPage(requestPage.getPage(), requestPage.getSize())
                .doSelectPageInfo(() -> {
                            starNftSeriesMapper.selectList(new QueryWrapper<StarNftSeries>()
                                            .eq(StarNftSeries.COL_IS_DELETE, Boolean.FALSE)).stream()
                                    .map(item ->
                                            SeriesVO
                                                    .builder()
                                                    .id(item.getId())
                                                    .seriesName(item.getSeriesName())
                                                    .seriesType(item.getSeriesType())
                                                    .seriesImages(item.getSeriesImages())
                                                    .seriesModels(item.getSeriesModels())
                                                    .build()
                                    ).collect(Collectors.toList());
                        }
                );
        ResponsePageResult<SeriesVO> pageResult = new ResponsePageResult<>();
        pageResult.setPage(result.getPageNum());
        pageResult.setList(result.getList());
        pageResult.setTotal(result.getTotal());
        pageResult.setSize(result.getSize());
        return pageResult;
    }

}
