package com.starnft.star.domain.rank.core;

import com.starnft.star.common.constant.StarConstants;

public class ConsumptionRank implements  RankInterFace{
    @Override
    public StarConstants.RankTypes getRankTypes() {
        return StarConstants.RankTypes.Consumption;
    }

    @Override
    public void listening() {

    }
}
