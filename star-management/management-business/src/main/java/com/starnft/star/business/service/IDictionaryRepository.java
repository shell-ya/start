package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarNftDict;

import java.util.List;

/**
 * @Date 2022/6/22 11:07 AM
 * @Author ： shellya
 */
public interface IDictionaryRepository {

    <T> T getDictCodes(String categoryCode, Class<T> clazz);

    List<StarNftDict> obtainDictionary(String categoryCode);
}
