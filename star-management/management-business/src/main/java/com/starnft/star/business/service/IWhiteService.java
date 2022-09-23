package com.starnft.star.business.service;

import com.starnft.star.business.domain.WhiteListConfig;
import com.starnft.star.business.domain.WhiteListDetail;
import com.starnft.star.business.domain.vo.WhiteDetailVo;

import java.util.List;

public interface IWhiteService {

    public WhiteListDetail queryWhite(Long whiteId, Long uid);

    List<WhiteListDetail> queryCommon(Long whiteId);

    List<WhiteListDetail> queryWhiteList(Long whiteId);

    List<WhiteListConfig> queryWhiteConfigList(WhiteListConfig config);

    int insertWhiteConfig(WhiteListConfig whiteListConfig);

    String importWhiteDetail(List<WhiteDetailVo> whiteDetailVos, Long whiteId);

    WhiteListConfig getOneConfig(Long whiteId);
}
