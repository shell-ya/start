package com.starnft.star.business.service;

import com.starnft.star.business.domain.WhiteListDetail;
import org.web3j.abi.datatypes.Int;

import java.util.List;

public interface IWhiteListDetailService {

    public WhiteListDetail queryWhite(Long whiteId, Long uid);

    List<WhiteListDetail> queryCommon(Long whiteId);
}
