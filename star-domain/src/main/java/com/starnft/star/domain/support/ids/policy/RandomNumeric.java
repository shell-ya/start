package com.starnft.star.domain.support.ids.policy;

import com.starnft.star.domain.support.ids.IIdGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Ryan z
 * @description: 工具类生成 org.apache.commons.lang3.RandomStringUtils
 * @date: 2022/5/5
 */
@Component
public class RandomNumeric implements IIdGenerator {

    @Override
    public long nextId() {
        return Long.parseLong(RandomStringUtils.randomNumeric(11));
    }

}
