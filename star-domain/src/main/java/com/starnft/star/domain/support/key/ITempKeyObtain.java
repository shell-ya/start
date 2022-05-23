package com.starnft.star.domain.support.key;

import com.starnft.star.domain.support.key.model.TempKey;

public interface ITempKeyObtain {

    TempKey obtainTempKey(final String bucketPrefix,final long uid);


    TempKey obtainTempKeyDefaultBucket(final long uid);
}
