package com.starnft.star.infrastructure.repository;

import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.common.utils.secure.AESUtil;
import com.starnft.star.domain.support.key.model.DictionaryVO;
import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import com.starnft.star.infrastructure.entity.dict.StarNftDict;
import com.starnft.star.infrastructure.mapper.dict.StarNftDictMapper;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Repository
public class DictionaryRepository implements IDictionaryRepository {

    @Resource
    private StarNftDictMapper starNftDictMapper;

    @Override
    public <T> T getDictCodes(String categoryCode, Class<T> clazz) {

        List<StarNftDict> dicts = starNftDictMapper.queryByCategoryCode(categoryCode);
        T obj = null;
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
            for (StarNftDict dict : dicts) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getName().equals(dict.getDictDesc())) {
                        try {
                            field.setAccessible(true);
                            field.set(obj, getApiKey(dict.getDoSecret().equals(1), dict.getDictCode()).get());
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    @Override
    public List<DictionaryVO> obtainDictionary(String categoryCode) {
        List<StarNftDict> starNftDicts = starNftDictMapper.queryByCategoryCode(categoryCode);
        return BeanColverUtil.colverList(starNftDicts, DictionaryVO.class);
    }

    public Supplier<String> getApiKey(boolean isAES, String dictCode) {
        if (isAES) {
            return () -> AESUtil.decrypt(dictCode);
        }
        return () -> dictCode;
    }

}
