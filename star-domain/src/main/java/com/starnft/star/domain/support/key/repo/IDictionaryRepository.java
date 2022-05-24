package com.starnft.star.domain.support.key.repo;

import com.starnft.star.domain.support.key.model.DictionaryVO;

import java.util.List;

public interface IDictionaryRepository {

    <T> T getDictCodes(String categoryCode, Class<T> clazz);

    List<DictionaryVO> obtainDictionary(String categoryCode);

}
