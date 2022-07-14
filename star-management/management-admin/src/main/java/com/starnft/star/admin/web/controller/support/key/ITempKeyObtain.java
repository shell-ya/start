package com.starnft.star.admin.web.controller.support.key;

import com.starnft.star.admin.web.controller.support.key.model.TempKey;

/**
 * @Date 2022/6/22 10:26 AM
 * @Author ： shellya
 */
public interface ITempKeyObtain {

    TempKey obtainTempKey(final String bucketPrefix, final long userId);
}
