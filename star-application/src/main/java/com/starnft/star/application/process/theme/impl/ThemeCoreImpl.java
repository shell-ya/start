package com.starnft.star.application.process.theme.impl;

import com.starnft.star.application.process.theme.ThemeCore;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.publisher.model.req.PublisherReq;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import com.starnft.star.domain.publisher.service.PublisherService;
import com.starnft.star.domain.theme.model.req.ThemeReq;
import com.starnft.star.domain.theme.model.res.ThemeDetailRes;
import com.starnft.star.domain.theme.model.res.ThemeRes;
import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import com.starnft.star.domain.theme.service.ThemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ThemeCoreImpl implements ThemeCore {
    @Resource
    ThemeService themeService;
    @Resource
    PublisherService publisherService;
    @Override
    public List<ThemeRes> queryThemesBySeriesId(Long seriesId) {
        List<ThemeVO> themes = themeService.queryThemesBySeriesId(seriesId);
       return getPublisher(themes);
    }

    @Override
    public ResponsePageResult<ThemeRes> queryMainThemeInfo(ThemeReq req) {
        ResponsePageResult<ThemeVO> themeVOResponsePageResult = themeService.queryMainThemeInfo(req);
        return ResponsePageResult.listReplace(themeVOResponsePageResult, getPublisher(themeVOResponsePageResult.getList()));
    }

    @Override
    public ThemeDetailRes queryThemeDetail(Long id) {
        ThemeDetailVO themeDetailVO = themeService.queryThemeDetail(id);
        ThemeDetailRes themeDetailRes = new ThemeDetailRes();
        themeDetailRes.setId(themeDetailVO.getId());
        themeDetailRes.setSeriesId(themeDetailVO.getSeriesId());
        themeDetailRes.setContractAddress(themeDetailVO.getContractAddress());
        themeDetailRes.setTags(themeDetailVO.getTags());
        themeDetailRes.setDescrption(themeDetailVO.getDescrption());
        themeDetailRes.setThemePic(themeDetailVO.getThemePic());
        themeDetailRes.setThemeName(themeDetailVO.getThemeName());
        themeDetailRes.setThemeType(themeDetailVO.getThemeType());
        themeDetailRes.setThemeLevel(themeDetailVO.getThemeLevel());
        themeDetailRes.setLssuePrice(themeDetailVO.getLssuePrice());
        themeDetailRes.setPublishNumber(themeDetailVO.getPublishNumber());
        themeDetailRes.setStock(themeDetailVO.getStock());
        Optional.ofNullable(themeDetailVO.getPublisherId()).ifPresent((item)->{
            PublisherReq publisherReq = new PublisherReq();
            publisherReq.setPublisherId(item);
            PublisherVO publisherVO = publisherService.queryPublisher(publisherReq);
            themeDetailRes.setPublisherVO(publisherVO);
        });

        return themeDetailRes;
    }

    private List<ThemeRes> getPublisher(List<ThemeVO> theme) {
        List<ThemeRes> result=new ArrayList<>();
        Set<Long> collect = theme.stream().map(ThemeVO::getPublisherId).collect(Collectors.toSet());
        if (!collect.isEmpty()){
            Map<Long, List<PublisherVO>> pubs = publisherService.queryPublisherByIds(collect).stream().collect(Collectors.groupingBy(PublisherVO::getAuthorId));
            for (ThemeVO themeVO : theme) {
                ThemeRes themeRes = new ThemeRes();
                themeRes.setId(themeVO.getId());
                themeRes.setThemePic(themeVO.getThemePic());
                themeRes.setThemeType(themeVO.getThemeType());
                themeRes.setThemeLevel(themeVO.getThemeLevel());
                themeRes.setThemeName(themeVO.getThemeName());
                themeRes.setLssuePrice(themeVO.getLssuePrice());
                themeRes.setSeriesId(themeVO.getSeriesId());
                themeRes.setPublishNumber(themeVO.getPublishNumber());
                themeRes.setPublisherVO(pubs.get(themeVO.getPublisherId()).get(0));
                themeRes.setTags(themeVO.getTags());
                result.add(themeRes);
            }
        }
        return result;
    }
}
