package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftDict;
import com.starnft.star.business.mapper.StarNftDictMapper;
import com.starnft.star.business.service.IDictionaryRepository;
import com.starnft.star.common.utils.AESUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;

/**
 * @Date 2022/6/22 11:08 AM
 * @Author ： shellya
 */
@Service
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
    public List<StarNftDict> obtainDictionary(String categoryCode) {
        return starNftDictMapper.queryByCategoryCode(categoryCode);

    }

    public Supplier<String> getApiKey(boolean isAES, String dictCode) {
        if (isAES) {
            return () -> AESUtil.decrypt(dictCode);
        }
        return () -> dictCode;
    }
}
