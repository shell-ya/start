package com.starnft.star.domain.support.key.repo;

public interface IDictionaryRepository {

    <T> T getApiKeyInfo(String categoryCode, Class<T> clazz);

}
