package com.starnft.star.management.service;

import com.qcloud.cos.COSClient;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ICosOperationService {

    <T> T doCos(Function<COSClient, T> operation, Class<T> resClazz);

    void doCos(Consumer<COSClient> operation);
}
