package com.starnft.star.management.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ICosOperationService {

    <T> T doCos(Function<COSClient, T> operation, Class<T> resClazz);

    void doCos(Consumer<COSClient> operation);

    void fileUpload(InputStream in, String bucketPrefix, String key);

    void fileDelete(String bucketPrefix, String key);

    Bucket createBucket(String bucketPrefix, String path);

    void fileDownloadViaCos(String bucketPrefix, String key);

    List<Bucket> getBucketList();
}
