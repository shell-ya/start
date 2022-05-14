package com.starnft.star.management.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
