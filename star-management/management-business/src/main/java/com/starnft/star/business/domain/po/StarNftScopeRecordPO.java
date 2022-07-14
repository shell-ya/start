package com.starnft.star.business.domain.po;

import com.starnft.star.business.domain.StarNftScopeRecord;

public class StarNftScopeRecordPO extends StarNftScopeRecord {
    private Boolean isAggregation;

    public Boolean getAggregation() {
        return isAggregation;
    }

    public void setAggregation(Boolean aggregation) {
        isAggregation = aggregation;
    }
}
