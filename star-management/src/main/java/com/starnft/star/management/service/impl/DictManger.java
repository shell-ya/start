package com.starnft.star.management.service.impl;

import com.starnft.star.common.utils.secure.AESUtil;
import com.starnft.star.infrastructure.entity.dict.StarNftDict;
import com.starnft.star.infrastructure.mapper.dict.StarNftDictMapper;
import com.starnft.star.management.service.IDictManger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictManger implements IDictManger {

    @Resource
    private StarNftDictMapper starNftDictMapper;

    @Override
    @Transactional
    public Integer createDict(List<StarNftDict> starNftDicts) {
        for (StarNftDict dict : starNftDicts) {
            //加密存储
            dict.setDictCode(AESUtil.encrypt(dict.getDictCode()));
        }
        return starNftDictMapper.insertBatch(starNftDicts);
    }
}
