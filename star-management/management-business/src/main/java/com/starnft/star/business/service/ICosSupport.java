package com.starnft.star.business.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Date 2022/6/22 10:47 AM
 * @Author ： shellya
 */
public interface ICosSupport {

    COSClient cosClientInit();

    PutObjectResult fileUploadViaCos(InputStream in, String bucketName, String key, COSClient client);

    void shutdown(COSClient client);

    void fileDelete(String bucketName, String key, COSClient client);

    ObjectMetadata fileDownloadViaCos(String bucketName, String key, COSClient client) throws IOException;

    Bucket createBucket(String bucketPrefix, String path, COSClient client);

    List<Bucket> getBucketList(COSClient client);

    String getBucketName(String bucketPrefix);
}
