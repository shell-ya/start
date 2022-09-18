package com.starnft.star.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.business.domain.WhiteListDetail;
import com.starnft.star.business.mapper.WhiteListDetailMapper;
import com.starnft.star.business.service.IWhiteListDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhiteListDetailServiceImpl implements IWhiteListDetailService {

    @Autowired
    private WhiteListDetailMapper whiteListDetailMapper;

    @Override
    public WhiteListDetail queryWhite(Long whiteId, Long uid) {
        return whiteListDetailMapper.selectMappingWhite(whiteId,uid);
    }

    @Override
    public List<WhiteListDetail> queryCommon(Long whiteId) {
        QueryWrapper<WhiteListDetail> whiteListDetailQueryWrapper = new QueryWrapper<>();
        whiteListDetailQueryWrapper.eq("white_id",whiteId);
        return whiteListDetailMapper.selectList(whiteListDetailQueryWrapper);
    }
}
