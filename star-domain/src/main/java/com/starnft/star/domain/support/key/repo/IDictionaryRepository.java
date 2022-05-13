package com.starnft.star.domain.support.key.repo;

public interface IDictionaryRepository {

    <T> T getDictCodes(String categoryCode, Class<T> clazz);

}
